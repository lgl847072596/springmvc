package com.my.web.dao.hibernate.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.web.dao.hibernate.IHAppDao;
import com.my.web.dao.hibernate.IHUserDao;
import com.my.web.po.TbApp;
import com.my.web.po.TbOldApp;
import com.my.web.po.TbUser;
@Repository
public class AppDaoImpl implements IHAppDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(TbApp tbApp) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.save(tbApp);		
		transaction.commit();
		session.close();
	}

	public void update(TbApp tbApp) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.update(tbApp);	
		transaction.commit();
		session.close();
	}

	public void delete(String packageName,String platform) throws Exception {
		
		TbApp app=queryByPackageNameAndPlatform(packageName,platform);
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.delete(app);
		transaction.commit();
		session.close();
	}



	public List<TbApp> queryAll(String platform) throws Exception {
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();		
		List<TbApp>list=session.createQuery("from TbApp where platform=?")
				.setParameter(0, platform).list();		
		transaction.commit();		
		session.close();
		return list;
	}

	public TbOldApp findOldApkByPackageNameAndVersion(String packageName, int versionCode,String platform) throws Exception {
		TbOldApp app=null;
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

	public void update(TbOldApp oldApp) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.update(oldApp);	
		transaction.commit();
		session.close();
		
	}

	public void save(TbOldApp oldApp) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.save(oldApp);		
		transaction.commit();	
		session.close();
	}



	public TbApp queryByPackageNameAndPlatform(String packageName, String platform) throws Exception {
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

	public List<TbOldApp> queryByPackageName(String packageName, String platform) throws Exception {

		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbOldApp>list=session.createQuery("from TbOldApp where packageName=? and platform=? order by versionCode desc")
				.setParameter(0, packageName)
				.setParameter(1, platform)
				.list();
		
		transaction.commit();
		session.close();
		return list;
	
	}

}

