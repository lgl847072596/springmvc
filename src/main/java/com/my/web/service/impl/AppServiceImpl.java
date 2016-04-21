package com.my.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.web.dao.hibernate.IHAppDao;
import com.my.web.po.TbApp;
import com.my.web.po.TbOldApp;
import com.my.web.service.IAppService;
@Service
public class AppServiceImpl implements IAppService {

	@Autowired
	private IHAppDao hAppDao;
	
	public void save(TbApp tbApp) throws Exception {
		hAppDao.save(tbApp);
	}

	public void update(TbApp tbApp) throws Exception {
		hAppDao.update(tbApp);
	}

	public void delete(String packageName,String platform) throws Exception {
		hAppDao.delete(packageName, platform);
	}

	public List<TbApp> queryAll(String platform) throws Exception {
		return hAppDao.queryAll(platform);
	}

	public TbOldApp findOldApkByPackageNameAndVersion(String packageName, int versionCode,String platform) throws Exception {
		// TODO Auto-generated method stub
		return hAppDao.findOldApkByPackageNameAndVersion(packageName,versionCode,platform);
	}

	public void update(TbOldApp oldApp) throws Exception {
		hAppDao.update(oldApp);
	}

	public void save(TbOldApp oldApp) throws Exception {
		hAppDao.save(oldApp);
	}

	public List<TbOldApp> queryByPackageName(String packageName, String platform) throws Exception {
		return hAppDao.queryByPackageName(packageName,platform);
	}

	public TbApp queryByPackageNameAndPlatform(String packageName, String platform) throws Exception {
		// TODO Auto-generated method stub
		return hAppDao.queryByPackageNameAndPlatform(packageName,platform);
	}

}
