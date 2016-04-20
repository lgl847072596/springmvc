package com.my.web.dao.hibernate.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.web.dao.hibernate.IHUserDao;
import com.my.web.po.TbUser;
@Repository
public class UserDaoImpl implements IHUserDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(TbUser tbUser) throws Exception {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.save(tbUser);		
		transaction.commit();
	}

}
