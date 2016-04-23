package com.my.web.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.web.dao.impl.IHUserDao;
import com.my.web.po.TbUser;
import com.my.web.po.TbUserDetail;
import com.my.web.service.IUserService;
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IHUserDao hUserDao;
	
	public TbUser findUserByAccount(String account) throws Exception {
		return hUserDao.findUserByAccount(account);
	}

	public void save(TbUser tbUser) throws Exception {
		hUserDao.save(tbUser);		
	}

	public TbUser findUserByAccount(String account, String password) throws Exception {

		return hUserDao.findUserByAccountAndPassword(account,password);
	}

	public List<TbUser> findNoCheckUser() throws Exception {
		return hUserDao.findNoCheckUser();
	}

	public void update(TbUser tbUser) throws Exception {
		 hUserDao.update(tbUser);
	}

	public TbUserDetail findUserDetailByAccount(String account) throws Exception {
		return hUserDao.findUserDetailByAccount(account);
	}



	public void saveOrupdateTbUserDetail(TbUserDetail person) throws Exception {
		hUserDao.saveOrUpdate(person);
	}
	
	

}
