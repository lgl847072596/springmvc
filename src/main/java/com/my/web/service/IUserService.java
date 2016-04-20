package com.my.web.service;

import com.my.web.po.TbUser;

public interface IUserService {
	public TbUser findUserByAccount(String account)throws Exception;

	public void save(TbUser tbUser)throws Exception;

	public TbUser findUserByAccount(String account, String password)throws Exception;
}
