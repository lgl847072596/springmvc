package com.my.web.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.web.dao.hibernate.IHUserDao;
import com.my.web.dao.mybatis.IMUserDao;
import com.my.web.po.TbUser;
import com.my.web.service.IUserService;
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IMUserDao mUserDao;
	
	@Autowired
	private IHUserDao hUserDao;
	
	public TbUser findUserByAccount(String account) throws Exception {
		return mUserDao.findUserByAccount(account);
	}

	public void save(TbUser tbUser) throws Exception {
		hUserDao.save(tbUser);		
	}

	public TbUser findUserByAccount(String account, String password) throws Exception {
		HashMap map=new HashMap();
		map.put("account", account);
		map.put("password", password);
		return mUserDao.findUser(map);
	}

}
