package com.my.web.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.my.web.dao.IHBaseDao;
import com.my.web.po.TbUser;
import com.my.web.po.TbUserDetail;

@Repository
public class IHUserDao extends IHBaseDao {
	
	public List<TbUser> findNoCheckUser() throws Exception {

		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();		
		List<TbUser>list=session.createQuery("from TbUser where roleLevel<?")
				.setParameter(0, 1).list();		
		transaction.commit();		
		session.close();
		return list;
	}

	public TbUser findUserByAccount(String account) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();		
		List<TbUser>list=session.createQuery("from TbUser where account=?")
				.setParameter(0, account).list();		
		transaction.commit();		
		session.close();
		return list.isEmpty()?null:list.get(0);
	}

	public TbUser findUserByAccountAndPassword(String account, String password) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();		
		List<TbUser>list=session.createQuery("from TbUser where account=? and password=?")
				.setParameter(0, account)
				.setParameter(1, password).list();		
		transaction.commit();		
		session.close();
		return list.isEmpty()?null:list.get(0);
	}

	public TbUserDetail findUserDetailByAccount(String account) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();		
		List<TbUserDetail>list=session.createQuery("from TbUserDetail where account=?")
				.setParameter(0, account).list();		
		transaction.commit();		
		session.close();
		return list.isEmpty()?null:list.get(0);
	}

}
