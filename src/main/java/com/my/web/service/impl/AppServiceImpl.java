package com.my.web.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.web.dao.impl.IHAppDao;
import com.my.web.dao.impl.IHOldAppDao;
import com.my.web.exception.CustomException;
import com.my.web.po.TbApp;
import com.my.web.po.TbAppCategory;
import com.my.web.po.TbOldApp;
import com.my.web.service.IAppService;
import com.my.web.tool.CharacterTool;
import com.my.web.tool.DateTool;
@Service
public class AppServiceImpl implements IAppService {

	@Autowired
	private IHAppDao appDao;
	
	@Autowired
	private IHOldAppDao oldAppDao;

	public void uploadApp(TbApp poApp,String realPathDir) throws Exception {
		
		//从应用表中查找
		TbApp app=appDao.queryTbAppByPackageNameAndPlatform(poApp.getPackageName(), poApp.getPlatform());
		if(app==null){
			poApp.setCreateTime(DateTool.formatData(new Date()));
			appDao.save(poApp);
		}else{
			if(app.getVersionCode()>poApp.getVersionCode()){
				throw new CustomException("提交失败，现有的应用版本比你上传的高");
			}else{
				//版本一样则删除旧的app，替换新的
				if(app.getVersionCode()==poApp.getVersionCode()){
					if(!CharacterTool.isNullOrEmpty(realPathDir))new File(realPathDir+app.getUrl()).delete();
					app.setUrl(poApp.getUrl());
					poApp.setDownloadCount(app.getDownloadCount());
					poApp.setAppId(app.getAppId());
					appDao.update(poApp);
				}else{
					//从历史记录中招
					TbOldApp oldApp=oldAppDao.findOldAppByPackageNameAndVersion(app.getPackageName(), app.getVersionCode(), app.getPlatform());
					if(oldApp!=null){
						if(!CharacterTool.isNullOrEmpty(realPathDir))new File(realPathDir+oldApp.getUrl()).delete();
						oldApp.setAppName(app.getAppName());
						oldApp.setDownloadCount(app.getDownloadCount());
						oldApp.setForceUpdate(app.getForceUpdate());
						oldApp.setLog(app.getLog());
						oldApp.setUpdateTime(app.getUpdateTime());
						oldApp.setUrl(app.getUrl());
						oldAppDao.update(oldApp);
					}else{
						oldApp=new TbOldApp();
						oldApp.setAppName(app.getAppName());
						oldApp.setDownloadCount(app.getDownloadCount());
						oldApp.setForceUpdate(app.getForceUpdate());
						oldApp.setLog(app.getLog());
						oldApp.setUpdateTime(app.getUpdateTime());
						oldApp.setCreateTime(app.getCreateTime());
						oldApp.setUrl(app.getUrl());
						oldApp.setPlatform(app.getPlatform());
						oldApp.setPackageName(app.getPackageName());
						oldApp.setVersionCode(app.getVersionCode());
						oldApp.setVersionName(app.getVersionName());
						oldApp.setBelongAccount(app.getBelongAccount());
						oldAppDao.save(oldApp);
					}
					appDao.delete(app);
					appDao.save(poApp);
				}
			}
		}
		
	}



	public List<TbApp> queryAccountTbAppByPlatform(String platform, String belongAccount) throws Exception {
		return appDao.queryAccountTbAppByPlatform(platform,belongAccount);
	}






	public TbOldApp findOldAppByPlatformAndUrl(String platform, String url) throws Exception {
		return oldAppDao.findOldAppByPlatformAndUrl(platform,url);
	}



	public void updateTbOldApp(TbOldApp oldApp) throws Exception {
		oldAppDao.update(oldApp);
	}



	public TbApp findTbAppByPlatformAndUrlOrMappingUrl(String platform, String url, String mappingUrl)
			throws Exception {
		return appDao.findTbAppByPlatformAndUrlOrMappingUrl(platform,url,mappingUrl);
	}



	public void updateTbApp(TbApp app) throws Exception {
		appDao.update(app);		
	}



	public TbApp queryTbAppByPackageNameAndPlatform(String packageName, String platform) {
		return appDao.queryTbAppByPackageNameAndPlatform(packageName,platform);
	}



	public List<TbAppCategory> getAppCategoryByAccountAndPlatform(String belongAccount, String platform) throws Exception {
		
		return appDao.getAppCategoryByAccountAndPlatform(belongAccount,platform);
	}



	public List<TbOldApp> queryAccountOldAppByPlatformAndPackageName(String packageName, String platform, String account)
			throws Exception {
		// TODO Auto-generated method stub
		return oldAppDao.queryAccountOldAppByPlatformAndPackageName(packageName,platform,account);
	}



	public TbApp findTbAppByPlatformAndMappingUrl(String platform, String mappingUrl) throws Exception {
		// TODO Auto-generated method stub
		return appDao.findTbAppByPlatformAndMappingUrl(platform,mappingUrl);
	}



	
	

}
