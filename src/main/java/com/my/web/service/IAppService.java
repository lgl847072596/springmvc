package com.my.web.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.my.web.po.TbApp;
import com.my.web.po.TbAppCategory;
import com.my.web.po.TbOldApp;

@Repository
public interface IAppService {

	void uploadApp(TbApp dexApp, String realPathDir)throws Exception;

	List<TbApp> queryAccountTbAppByPlatform(String platform, String account)throws Exception;
	

	TbOldApp findOldAppByPlatformAndUrl(String platform, String url)throws Exception;

	void updateTbOldApp(TbOldApp oldApp)throws Exception;

	TbApp findTbAppByPlatformAndUrlOrMappingUrl(String platform, String url, String mappingUrl)throws Exception;

	void updateTbApp(TbApp app)throws Exception;

	TbApp queryTbAppByPackageNameAndPlatform(String packageName, String platform)throws Exception;

	List<TbAppCategory> getAppCategoryByAccountAndPlatform(String belongAccount, String platform)throws Exception;

	List<TbOldApp> queryAccountOldAppByPlatformAndPackageName(String packageName, String platform, String account)throws Exception;
}
