package com.my.web.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.my.web.dao.IHBaseDao;
import com.my.web.po.TbApp;
import com.my.web.po.TbAppCategory;
import com.my.web.po.TbOldApp;

@Repository
public  class IHAppDao extends IHBaseDao{

	public List<TbApp> queryAccountTbAppByPlatform(String platform, String belongAccount)throws Exception {

		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbApp>list=session.createQuery("from TbApp where platform=? and belongAccount=?")
				.setParameter(0, platform)
				.setParameter(1, belongAccount)
				.list();
		
		transaction.commit();
		session.close();
		
		return list;
	
	}

	public TbApp findTbAppByPlatformAndUrlOrMappingUrl(String platform, String url, String mappingUrl) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		List<TbApp>list=null;
		
		if(mappingUrl!=null&&mappingUrl.length()>0){
			list=session.createQuery("from TbApp where platform=? and mappingUrl=? order by versionCode desc")
					.setParameter(0, platform)
					.setParameter(1, mappingUrl)
					.list();
		}else{
			list=session.createQuery("from TbApp where platform=? and url=? order by versionCode desc")
					.setParameter(0, platform)
					.setParameter(1, url)
					.list();
		}
		transaction.commit();
		session.close();
		
		return list.isEmpty()?null:list.get(0);
	}

	public TbApp queryTbAppByPackageNameAndPlatform(String packageName, String platform) {
	
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbApp>list=session.createQuery("from TbApp where packageName=? and platform=?")
				.setParameter(0, packageName)
				.setParameter(1, platform)
				.list();
		
		transaction.commit();
		session.close();
		
		return list.isEmpty()?null:list.get(0);
	}

	public List<TbAppCategory> getAppCategoryByAccountAndPlatform(String belongAccount, String platform) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbAppCategory>list=session.createQuery("from TbAppCategory where belongAccount=? and platform=?")
				.setParameter(0, belongAccount)
				.setParameter(1, platform)
				.list();
		
		transaction.commit();
		session.close();
		
		return list;
	}
	
}
