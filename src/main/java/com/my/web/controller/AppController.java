package com.my.web.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.my.web.config.Contants;
import com.my.web.config.FileConfig;
import com.my.web.exception.CustomException;
import com.my.web.model.AppVersionPo;
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
	public String upLoadApp(@RequestParam String platform,@RequestParam String appName,String versionCode,String packageName,@RequestParam  String log ,String mappingUrl,String forceUpdate, HttpServletRequest request)throws Exception{
		//1.校验上传权限
		TbUser user=(TbUser) request.getSession().getAttribute(Contants.USER);
		if(user.getRoleLevel()<1){
			throw new CustomException("您不具备该权限"+user.getRoleLevel());
		}
		//创建一个临时App
		TbApp app=new TbApp();
		app.setAppName(appName);
		try {
			app.setVersionCode(Integer.parseInt(versionCode));
		} catch (Exception e) {
		}
		app.setPackageName(packageName);
		app.setMappingUrl(mappingUrl);
		app.setLog(log);
		
		if("true".equals(forceUpdate)||"false".equals(forceUpdate)){
			app.setForceUpdate(forceUpdate);
		}else{
			app.setForceUpdate("true");
		}
		
		System.out.println("app:"+app);
		
		//检测必要的参数是否齐全
		if(CharacterTool.isNullOrEmpty(platform)||CharacterTool.isNullOrEmpty(packageName)||CharacterTool.isNullOrEmpty(versionCode)||CharacterTool.isNullOrEmpty(mappingUrl)){
			throw new CustomException("没有上传app,则必须保证其他字段完整");
		}
             
	    //查询，保存，更新应用
		TbApp tbApp=appService.queryTbAppByPackageNameAndPlatform(packageName, platform);
		if(tbApp==null){				
			tbApp=new TbApp();
			tbApp.setBelongAccount(user.getAccount());
			tbApp.setPlatform(platform);
			tbApp.setCreateTime(DateTool.formatData(new Date()));
		}	
		
		tbApp.setAppName(appName);
		tbApp.setVersionCode(Integer.parseInt(versionCode));
		tbApp.setPackageName(packageName);
		tbApp.setVersionName(versionCode);
		tbApp.setForceUpdate(forceUpdate);
		tbApp.setLog(log);
		tbApp.setMappingUrl(mappingUrl);
		tbApp.setUpdateTime(DateTool.formatData(new Date()));
		
		
		appService.uploadApp(tbApp,"");
		request.setAttribute("app", tbApp);	
		request.setAttribute("platform", tbApp.getPlatform());
		request.setAttribute("success",true);
		
		return "protected/app/upload";
		
		
	
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
		TbUser user=(TbUser) request.getSession().getAttribute(Contants.USER);
		List<TbOldApp> appList = appService.queryAccountOldAppByPlatformAndPackageName(packageName, platform,user.getAccount());
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
				outPutName=CharacterTool.isNullOrEmpty(oldApp.getAppName())?null:oldApp.getAppName()+"-"+oldApp.getVersionCode()+suffix;
			}else{
				url=null;
			}
		}else{
			TbApp app=appService.findTbAppByPlatformAndUrlOrMappingUrl(platform,url,mappingUrl);
			if(app!=null){
				url=path+app.getUrl().replace(relativeDir, "/");
				app.setDownloadCount(app.getDownloadCount() + 1);
				appService.updateTbApp(app);
				outPutName=CharacterTool.isNullOrEmpty(app.getAppName())?null:app.getAppName()+"-"+app.getVersionCode()+suffix;
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
	public String goToUpdateApk(@RequestParam String packageName,@RequestParam String platform,
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
	@RequestMapping("/public/lastversion.json")
	@ResponseBody
	public JSONResultModel<Map> getLastVersion(@RequestBody AppVersionPo app,HttpServletRequest request) throws Exception {
		
		if(CharacterTool.isNullOrEmpty(app.getPlatform())||CharacterTool.isNullOrEmpty(app.getPackageName())){
			if(CharacterTool.isNullOrEmpty(app.getPlatform()))
				throw new CustomException("应用平台不能为空");
			if(CharacterTool.isNullOrEmpty(app.getPackageName()))
				throw new CustomException("应用包名不能为空");
			throw new CustomException("需要的参数是platform和packageName");
		}
		
		TbApp tempAPP = appService.queryTbAppByPackageNameAndPlatform(app.getPackageName(), app.getPlatform());
		if(tempAPP!=null){
			//基本路径
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
			
			//对外都是使用mappingUrl
			if(CharacterTool.isNullOrEmpty(tempAPP.getMappingUrl())){
				return new JSONResultModel<Map>(false, "应用的映射路径未找到");
			}
			//重新包装内容
			Map map=new HashMap();
			map.put("packageName", tempAPP.getPackageName());
			map.put("versionCode", tempAPP.getVersionCode());
			map.put("log", tempAPP.getLog());
			map.put("updateTime", tempAPP.getUpdateTime());
			map.put("forceUpdate", tempAPP.getForceUpdate());			
			map.put("url", tempAPP.getMappingUrl());
			return new JSONResultModel<Map>(true, map);
		}else{
			return new JSONResultModel<Map>(false, "未找到应用信息");
		}
		
	}

}
