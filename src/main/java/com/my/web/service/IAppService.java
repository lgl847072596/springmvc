package com.my.web.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.my.web.po.TbApp;
import com.my.web.po.TbOldApp;

@Repository
public interface IAppService {
	
	public void save(TbApp tbApp)throws Exception;
	
	public void update(TbApp tbApp)throws Exception;
	
	public void delete(String packageName,String platform)throws Exception;
	
	
	public List<TbApp>queryAll(String platform)throws Exception;

	public TbOldApp findOldApkByPackageNameAndVersion(String packageName, int versionCode,String platform)throws Exception;

	public void update(TbOldApp oldApp)throws Exception;

	public void save(TbOldApp oldApp)throws Exception;

	public List<TbOldApp> queryByPackageName(String packageName, String platform)throws Exception;

	public TbApp queryByPackageNameAndPlatform(String packageName, String platform)throws Exception;


}
