package com.my.web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class IHBaseDao {

	@Autowired
	protected SessionFactory sessionFactory;

	public void save(Object obj) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.save(obj);		
		transaction.commit();
		session.close();
	}

	public void update(Object obj) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.update(obj);		
		transaction.commit();
		session.close();
	}

	public void saveOrUpdate(Object obj) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.saveOrUpdate(obj);	
		transaction.commit();
		session.close();
	}
	
	public void delete(Object obj)throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.delete(obj);
		transaction.commit();
		session.close();
	}
}
