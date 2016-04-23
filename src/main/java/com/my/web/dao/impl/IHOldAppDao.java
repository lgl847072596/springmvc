package com.my.web.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.my.web.dao.IHBaseDao;
import com.my.web.po.TbOldApp;

@Repository
public class IHOldAppDao extends IHBaseDao {

	public TbOldApp findOldAppByPlatformAndUrl(String platform, String url) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbOldApp>list=session.createQuery("from TbOldApp where platform=? and url=?")
				.setParameter(0, platform)
				.setParameter(1, url)
				.list();
		
		transaction.commit();
		session.close();
		
		return list.isEmpty()?null:list.get(0);
	}

	public TbOldApp findOldAppByPackageNameAndVersion(String packageName, int versionCode, String platform) throws Exception{

		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbOldApp>list=session.createQuery("from TbOldApp where packageName=? and versionCode=? and platform=?")
				.setParameter(0, packageName)
				.setParameter(1, versionCode)
				.setParameter(2, platform)
				.list();
		
		transaction.commit();
		session.close();
		
		return list.isEmpty()?null:list.get(0);
	
	}

	public List<TbOldApp> queryAccountOldTbAppByPlatform(String platform, String belongAccount) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbOldApp>list=session.createQuery("from TbOldApp where platform=? and belongAccount=?")
				.setParameter(0, platform)
				.setParameter(1, belongAccount)
				.list();
		
		transaction.commit();
		session.close();
		
		return list;
	
	}

}
