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
	}

	public void update(TbApp tbApp) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.update(tbApp);	
		transaction.commit();
		
	}

	public void delete(int id) throws Exception {
		
		TbApp app=queryById(id);
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.delete(app);
		transaction.commit();
		
	}

	public TbApp queryById(int id) throws Exception {
		TbApp app=null;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		List<TbApp>list=session.createQuery("from TbApp where id=?").setParameter(0, id).list();
		
		transaction.commit();
		
		return list.isEmpty()?null:list.get(0);
	}

	public List<TbApp> queryAll() throws Exception {
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();		
		List<TbApp>list=session.createQuery("from TbApp").list();		
		transaction.commit();		
		return list;
	}

}

