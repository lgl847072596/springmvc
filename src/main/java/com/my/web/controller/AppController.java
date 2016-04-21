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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.my.web.config.FileConfig;
import com.my.web.exception.CustomException;
import com.my.web.model.JSONResultModel;
import com.my.web.po.TbApp;
import com.my.web.po.TbOldApp;
import com.my.web.service.IAppService;
import com.my.web.tool.ApkTool;
import com.my.web.tool.CharacterTool;
import com.my.web.tool.DateTool;
import com.my.web.tool.FileTool;
import com.my.web.tool.ResponseOutputJSONTool;

@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private IAppService appService;

	/**
	 * @description 上传apk
	 * @param apk
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/public/uploadApk.json")
	@ResponseBody
	public JSONResultModel upLoadApk(MultipartFile apk, HttpServletRequest request) throws Exception {
		// 检测上传的是否是apk文件
		if (!apk.getOriginalFilename().endsWith(".apk"))
			throw new CustomException("上传的文件不是有效的移动应用文件");

		// 获取本机真实路径
		String path = request.getSession().getServletContext().getRealPath(FileConfig.UPLOAD_FILE_APK);
		// 给文件随机生成名字
		String fileName = UUID.randomUUID().toString() + ".apk";
		File targetFile = new File(path, fileName);
		new File(path).mkdirs();
		String url = "";
		// 文件写入本地文件中
		try {
			apk.transferTo(targetFile);
			url = FileConfig.UPLOAD_FILE_APK + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 文件写入成功
			if (targetFile.exists()) {
				// 解析apk文件获取必要的信息
				System.out.println("app保存路径：" + targetFile.getAbsolutePath());
                
				TbApp app = ApkTool.dexApk(targetFile.getAbsolutePath());
				if (app != null) {
					app.setUrl(url);
					app.setPlatform("android");					
					app.setUpdateTime(DateTool.formatData(new Date()));
					
					TbApp temp = appService.queryByPackageNameAndPlatform(app.getPackageName(), app.getPlatform());
					// 若数据库中已经存在这个apk旧版本则取出转移到历史记录表中
					if (temp != null) {
						// 上传的版本必须大于已有的版本
						if (temp.getVersionCode() <= app.getVersionCode()) {
							// 版本号相同则只需要替换,删除原来的apk
							if (temp.getVersionCode() == app.getVersionCode()) {
								// 删除apk
								File file = new File(targetFile.getParentFile(),
										temp.getUrl().substring(FileConfig.UPLOAD_FILE_APK.length()));
								file.delete();

							}
							// 将就得移动到旧版本表中,若旧版本已经有这个版本了则，替换并删除原来的apk
							else {
								TbOldApp oldApp = appService.findOldApkByPackageNameAndVersion(temp.getPackageName(),
										temp.getVersionCode(), temp.getPlatform());
								if (oldApp != null) {
									// 删除原有apk
									File file = new File(targetFile.getParentFile(),
											oldApp.getUrl().substring(FileConfig.UPLOAD_FILE_APK.length()));
									file.delete();

									// 替换
									oldApp.setUpdateTime(temp.getUpdateTime());
									oldApp.setVersionIntroduce(temp.getVersionIntroduce());
									oldApp.setUrl(temp.getUrl());
									appService.update(oldApp);

								} else {
									oldApp = new TbOldApp();
									oldApp.setAppName(temp.getAppName());
									oldApp.setVersionIntroduce(temp.getVersionIntroduce());
									oldApp.setUpdateTime(temp.getUpdateTime());
									oldApp.setUrl(temp.getUrl());
									oldApp.setDownLoadCount(temp.getDownLoadCount());
									oldApp.setPackageName(temp.getPackageName());
									oldApp.setVersionCode(temp.getVersionCode());
									oldApp.setVersionName(temp.getVersionName());
									oldApp.setPlatform(temp.getPlatform());
									appService.save(oldApp);
								}
							}

							// 必须删除才可添加							
							appService.delete(temp.getPackageName(), temp.getPlatform());
						    //添加
							app.setDownLoadCount(temp.getDownLoadCount());
							app.setVersionIntroduce(temp.getVersionIntroduce());
							app.setPlatform("android");
							app.setAppName(temp.getAppName());
							appService.save(app);

						} else {
							throw new CustomException("文件上传失败");
						}
					} else {
						app.setPlatform("android");
						appService.save(app);
					}
					return new JSONResultModel<Map>(true, new HashMap());
				} else {
					throw new CustomException("文件上传失败");
				}

			} else {
				throw new CustomException("文件上传失败");
			}
		}

	}

	@RequestMapping("/public/uploadApp.action")
	public String upLoadApp(String appName, String packageName, int versionCode, String versionIntroduce,
			MultipartFile apk, HttpServletRequest request) throws Exception {

		// 检测上传的是否是apk文件
		if (!apk.getOriginalFilename().endsWith(".ipa"))
			throw new CustomException("上传的文件不是有效的移动应用文件");

		// 获取本机真实路径
		String path = request.getSession().getServletContext().getRealPath(FileConfig.UPLOAD_FILE_APP);
		// 给文件随机生成名字
		String fileName = UUID.randomUUID().toString() + ".ipa";
		File targetFile = new File(path, fileName);
		new File(path).mkdirs();
		String url = "";
		// 文件写入本地文件中
		try {
			apk.transferTo(targetFile);
			url = FileConfig.UPLOAD_FILE_APP + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// 文件写入成功
			if (targetFile.exists()) {
				TbApp temp = appService.queryByPackageNameAndPlatform(packageName, "ios");
				// 若数据库中已经存在这个apk旧版本则取出转移到历史记录表中
				if (temp != null) {
					// 上传的版本必须大于已有的版本
					if (temp.getVersionCode() <= versionCode) {
						// 版本号相同则只需要替换,删除原来的apk
						if (temp.getVersionCode() == versionCode) {
							// 删除apk
							File file = new File(targetFile.getParentFile(),
									temp.getUrl().substring(FileConfig.UPLOAD_FILE_APP.length()));
							file.delete();

						}
						// 将就得移动到旧版本表中,若旧版本已经有这个版本了则，替换并删除原来的apk
						else {
							TbOldApp oldApp = appService.findOldApkByPackageNameAndVersion(temp.getPackageName(),
									temp.getVersionCode(), temp.getPlatform());
							if (oldApp != null) {
								// 删除原有apk
								File file = new File(targetFile.getParentFile(),
										oldApp.getUrl().substring(FileConfig.UPLOAD_FILE_APP.length()));
								file.delete();
								// 替换
								oldApp.setUpdateTime(temp.getUpdateTime());
								oldApp.setVersionIntroduce(temp.getVersionIntroduce());
								oldApp.setUrl(temp.getUrl());
								appService.update(oldApp);

							} else {
								oldApp = new TbOldApp();
								oldApp.setAppName(temp.getAppName());
								oldApp.setVersionIntroduce(temp.getVersionIntroduce());
								oldApp.setUpdateTime(temp.getUpdateTime());
								oldApp.setUrl(temp.getUrl());
								oldApp.setDownLoadCount(temp.getDownLoadCount());
								oldApp.setPackageName(temp.getPackageName());
								oldApp.setVersionCode(temp.getVersionCode());
								oldApp.setVersionName(temp.getVersionName());
								oldApp.setPlatform(temp.getPlatform());
								appService.save(oldApp);
							}
						}

						// 删除旧的
						temp.setUpdateTime(DateTool.formatData(new Date()));
						temp.setUrl(url);
						appService.delete(temp.getPackageName(), temp.getPlatform());
						//添加新的
						temp=new TbApp();
						temp.setPackageName(packageName);
						temp.setAppName(appName);
						temp.setVersionCode(versionCode);
						temp.setVersionName(versionCode+"");
						temp.setVersionIntroduce(versionIntroduce);
						temp.setDownLoadCount(0L);
						temp.setPlatform("ios");
						temp.setUrl(url);
						appService.save(temp);
					} else {
						throw new CustomException("文件上传失败");
					}
				} else {
					
					temp=new TbApp();
					temp.setPackageName(packageName);
					temp.setAppName(appName);
					temp.setVersionCode(versionCode);
					temp.setVersionName(versionCode+"");
					temp.setVersionIntroduce(versionIntroduce);
					temp.setDownLoadCount(0L);
					temp.setPlatform("ios");
					temp.setUrl(url);
					appService.save(temp);
				}
				request.setAttribute("platform", "ios");
				request.setAttribute("apk", temp);
				return "apk/app_upload";
				
			} else {
				throw new CustomException("文件上传失败");
			}
	
		
		}

	}

	/**
	 * @description 获取最新apk 列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/public/applist.action")
	public String appAllList(String platform, HttpServletRequest request) throws Exception {

		List<TbApp> appList = appService.queryAll(platform);
		request.setAttribute("appList", appList);
		request.setAttribute("platform", platform);
		return "apk/apkList";

	}

	/**
	 * @description 获取老版本的apk
	 * @param packageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/public/apkOldlist.action")
	public String apkOldList(@RequestParam String packageName, @RequestParam String platform,
			HttpServletRequest request) throws Exception {
		List<TbOldApp> appList = appService.queryByPackageName(packageName, platform);
		request.setAttribute("appList", appList);
		request.setAttribute("platform", platform);
		return "apk/apkOldList";
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
	@RequestMapping("/public/download/app.action")
	@ResponseBody
	public void downLoadApp(@RequestParam String tb, @RequestParam String packageName, @RequestParam int versionCode,
			@RequestParam String url, String platform, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isApk = "android".equals(platform.toLowerCase());
		String path = request.getSession().getServletContext()
				.getRealPath(isApk ? FileConfig.UPLOAD_FILE_APK : FileConfig.UPLOAD_FILE_APP);
		path += url.replace(isApk ? FileConfig.UPLOAD_FILE_APK : FileConfig.UPLOAD_FILE_APP, "");
		// 文件存在则需要统计下载次数
		if (new File(path).exists()) {

			// 老版本的
			if ("old".equals(tb)) {
				TbOldApp oldApp = appService.findOldApkByPackageNameAndVersion(packageName, versionCode, platform);
				oldApp.setDownLoadCount(oldApp.getDownLoadCount() + 1);
				appService.update(oldApp);
			}
			// 新版本的
			else {
				TbApp app = appService.queryByPackageNameAndPlatform(packageName, platform);
				app.setDownLoadCount(app.getDownLoadCount() + 1);
				appService.update(app);
			}
			// 指定客户的呈现的文件名字
			String downloadFileName = url.replace(isApk ? FileConfig.UPLOAD_FILE_APK : FileConfig.UPLOAD_FILE_APP, "");

			FileTool.writeAppToResponse(path, downloadFileName, isApk, response);

		} else {
			JSONObject content = new JSONObject();
			content.put("success", false);
			content.put("message", "文件不存在");
			ResponseOutputJSONTool.out(response, content);
		}

	}

	/**
	 * @description 获取需要更新的app的信息
	 * @param packageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/public/apk/goToUpdateApk.action")
	public String goToUpdateApk(@RequestParam String packageName, @RequestParam String platform,
			HttpServletRequest request) throws Exception {
		TbApp apk = appService.queryByPackageNameAndPlatform(packageName, platform);
		request.setAttribute("apk", apk);
		if ("android".equals(platform.toLowerCase())) {
			request.setAttribute("platform", "android");
			return "apk/updateApk";
		} else {
			request.setAttribute("platform", "ios");
			return "apk/app_upload";
		}

	}

	/**
	 * @description 更新apk的信息
	 * @param appName
	 * @param versionIntroduce
	 * @param packageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/public/apk/updateApkMsg.action")
	public String updateApkMsg(@RequestParam String appName, @RequestParam String versionIntroduce,
			@RequestParam String packageName, HttpServletRequest request) throws Exception {
		TbApp apk = appService.queryByPackageNameAndPlatform(packageName, "android");
		apk.setAppName(appName);
		apk.setVersionIntroduce(versionIntroduce);
		appService.update(apk);
		request.setAttribute("apk", apk);
		request.setAttribute("platform", "android");
		return "apk/updateApk";
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
	public JSONResultModel<Map> getLastVersion(@RequestParam String platform, String packageName) throws Exception {
		
		TbApp app = appService.queryByPackageNameAndPlatform(packageName, platform);
		if(app!=null){
			Map map = new HashMap();
			map.put("app", app);
			JSONResultModel<Map> response = new JSONResultModel<Map>(true, map);
			return response;
		}
		return new JSONResultModel<Map>(false, "未找到应用信息");
	}

}
