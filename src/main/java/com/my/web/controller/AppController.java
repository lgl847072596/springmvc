package com.my.web.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.my.web.config.Contants;
import com.my.web.config.FileConfig;
import com.my.web.exception.CustomException;
import com.my.web.model.JSONResultModel;
import com.my.web.po.TbApp;
import com.my.web.po.TbAppCategory;
import com.my.web.po.TbOldApp;
import com.my.web.po.TbUser;
import com.my.web.service.IAppService;
import com.my.web.tool.AppTool;
import com.my.web.tool.CharacterTool;
import com.my.web.tool.DateTool;
import com.my.web.tool.FileTool;
import com.my.web.tool.ResponseOutputJSONTool;

@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private IAppService appService;
	@RequestMapping(value="/upload.action",method = RequestMethod.POST)
	public String upLoadApp(@RequestParam String appName,String versionCode,String packageName,@RequestParam  String log ,String mappingUrl,@RequestParam  MultipartFile apk, HttpServletRequest request)throws Exception{
		
		TbUser po=(TbUser) request.getSession().getAttribute(Contants.USER);
		if(po.getRoleLevel()<1){
			throw new CustomException("您不具备该权限"+po.getRoleLevel());
		}
		
		TbApp app=new TbApp();
		app.setAppName(appName);
		try {
			app.setVersionCode(Integer.parseInt(versionCode));
		} catch (Exception e) {
		}
		app.setPackageName(packageName);
		app.setMappingUrl(mappingUrl);
		app.setLog(log);
		
		System.out.println("app:"+app);
		
		if(apk.getOriginalFilename().endsWith(".apk")||apk.getOriginalFilename().endsWith(".ipa")){
			boolean isApk=apk.getOriginalFilename().endsWith(".apk");
			
			String relativeDir=isApk?FileConfig.UPLOAD_FILE_APK:FileConfig.UPLOAD_FILE_APP;
			relativeDir+=po.getAccount()+"/";
			
			String suffix=isApk?".apk":".ipa";
			
			// 获取本机真实路径
			String path = request.getSession().getServletContext().getRealPath(relativeDir);
			// 给文件随机生成名字
			String fileName = UUID.randomUUID().toString() + suffix;
			File targetFile = new File(path, fileName);
			new File(path).mkdirs();
			//保存的相对路径
			String url = "";
			// 文件写入本地文件中
			try {
				apk.transferTo(targetFile);
				url = relativeDir + fileName;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 文件写入成功
				if (targetFile.exists()) {
					System.out.println("app保存路径：" + targetFile.getAbsolutePath());
					//对apk或ipa文件进行解包获取必要的信息
					TbApp dexApp = AppTool.dex(targetFile.getAbsolutePath(),isApk?Contants.ANDROID:Contants.IOS);
					if(!CharacterTool.isNullOrEmpty(app.getAppName()))dexApp.setAppName(app.getAppName());
					dexApp.setForceUpdate(app.getForceUpdate());
					dexApp.setLog(app.getLog());
					dexApp.setMappingUrl(app.getMappingUrl());
					dexApp.setUrl(url);
					
					TbUser user=(TbUser) request.getSession().getAttribute(Contants.USER);
					dexApp.setBelongAccount(user.getAccount());
					appService.uploadApp(dexApp,path.substring(0, path.length()-relativeDir.length()));
					
					request.setAttribute("app", dexApp);	
					request.setAttribute("platform", dexApp.getPlatform());
					request.setAttribute("success",true);
					return "protected/app/upload";
					
				}else{
					throw new CustomException("数据添加失败");
				}
			}
		}else{
			throw new CustomException("文件格式不对，不是移动应用。");
		}
		
	}
	

	/**
	 * @description 获取最新app 列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/applist.action",method = RequestMethod.GET)
	public String queryAccountNewAppByPlatform(String platform, HttpServletRequest request) throws Exception {
		TbUser user=(TbUser) request.getSession().getAttribute(Contants.USER);
		List<TbApp> appList = appService.queryAccountTbAppByPlatform(platform,user.getAccount());
		request.setAttribute("appList", appList);
		request.setAttribute("platform", platform);
		return "protected/app/appList";

	}

	/**
	 * @description 获取老版本的apk
	 * @param packageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/appOldlist.action",method = RequestMethod.GET)
	public String queryAccountNewAppByPlatform(@RequestParam String packageName, @RequestParam String platform,
			HttpServletRequest request) throws Exception {
		List<TbOldApp> appList = appService.queryAccountOldAppByPlatform(packageName, platform);
		request.setAttribute("appList", appList);
		request.setAttribute("platform", platform);
		return "protected/app/appOldList";
	}

	/**
	 * @descrription 下载apk并统计下载次数
	 * @param tb
	 * @param packageName
	 * @param versionCode
	 * @param url
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/public/download/app.action",method = RequestMethod.GET)
	@ResponseBody
	public void downLoadApp(@RequestParam String categroy,String platform,String mappingUrl,String url, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		boolean isApk=Contants.ANDROID.equals(platform.toLowerCase());
		String suffix=isApk?".apk":".ipa";
		
		String relativeDir=isApk?FileConfig.UPLOAD_FILE_APK:FileConfig.UPLOAD_FILE_APP;
		// 获取本机真实路径
		String path = request.getSession().getServletContext().getRealPath(relativeDir);
		String outPutName=null;
		if("old".equals(categroy.toLowerCase())){
			TbOldApp oldApp=appService.findOldAppByPlatformAndUrl(platform,url);
			if(oldApp!=null){
				url=path+oldApp.getUrl().replace(relativeDir, "/");
				oldApp.setDownloadCount(oldApp.getDownloadCount() + 1);
				appService.updateTbOldApp(oldApp);
				outPutName=CharacterTool.isNullOrEmpty(oldApp.getAppName())?null:oldApp.getAppName()+suffix;
			}else{
				url=null;
			}
		}else{
			TbApp app=appService.findTbAppByPlatformAndUrlOrMappingUrl(platform,url,mappingUrl);
			if(app!=null){
				url=path+app.getUrl().replace(relativeDir, "/");
				app.setDownloadCount(app.getDownloadCount() + 1);
				appService.updateTbApp(app);
				outPutName=CharacterTool.isNullOrEmpty(app.getAppName())?null:app.getAppName()+suffix;
			}else{
				url=null;
			}
		}
		if(url!=null){
			// 文件存在则需要统计下载次数
			if (new File(url).exists()) {
				// 指定客户的呈现的文件名字
				String downloadFileName =outPutName==null? url.replace(relativeDir, ""):outPutName;
				FileTool.writeAppToResponse(url, downloadFileName, request, response);
			} else {
				
				throw new CustomException("文件不存在 ");
			}
		}else{
			throw new CustomException("文件不存在 ");
		}
	}
	@RequestMapping(value="/goToUpLoad.action",method = RequestMethod.GET)
	public String goToUpLoad(String platform,HttpServletRequest request)throws Exception {
		
		if(Contants.ANDROID.equals(platform)||Contants.IOS.equals(platform)){}
		else platform=Contants.ANDROID;
		
		TbUser po=(TbUser) request.getSession().getAttribute(Contants.USER);
		if(po.getRoleLevel()>0){
			request.setAttribute("platform", platform);
			return "protected/app/upload";
		}else{
			throw new CustomException("您不具备该权限");
		}		
	}

	/**
	 * @description 获取需要更新的app的信息
	 * @param packageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goToUpdateApk.action",method = RequestMethod.GET)
	public String goToUpdateApk(@RequestParam String packageName, @RequestParam String platform,
			HttpServletRequest request) throws Exception {
		
		TbUser po=(TbUser) request.getSession().getAttribute(Contants.USER);
		if(po.getRoleLevel()>0){
			if(Contants.ANDROID.equals(platform)||Contants.IOS.equals(platform)){}
			else platform=Contants.ANDROID;
			
			TbApp app = appService.queryTbAppByPackageNameAndPlatform(packageName, platform);
			request.setAttribute("platform", platform);
			request.setAttribute("app", app);
			return "protected/app/upload";
		}else{
			throw new CustomException("您不具备该权限");
		}
		
		
	}

	@RequestMapping("/appCategory.action")
	public String getAppCategory(@RequestParam String platform,
			HttpServletRequest request)throws Exception{
		TbUser po=(TbUser) request.getSession().getAttribute(Contants.USER);
		
		List<TbAppCategory>cateGoryList=appService.getAppCategoryByAccountAndPlatform(po.getAccount(),platform);
		request.setAttribute("cateGoryList", cateGoryList);
		request.setAttribute("platform", platform);
		return "protected/app/appCategory";
	}
	

	
	
	/**
	 * @description 获取最新版本信息
	 * @param platform
	 * @param packageName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/app/lastversion.json")
	@ResponseBody
	public JSONResultModel<Map> getLastVersion(@RequestParam String platform, String packageName) throws Exception {
		
		TbApp app = appService.queryTbAppByPackageNameAndPlatform(packageName, platform);
		if(app!=null){
			
			//不把ios的下载路径给出来
			if(Contants.IOS.equals(platform)){
				app.setUrl("");
			}
			
			Map map = new HashMap();
			map.put("app", app);
			JSONResultModel<Map> response = new JSONResultModel<Map>(true, map);
			return response;
		}
		return new JSONResultModel<Map>(false, "未找到应用信息");
	}

}
