package com.my.web.service;

import java.util.List;

import com.my.web.po.TbUser;
import com.my.web.po.TbUserDetail;

public interface IUserService {
	public TbUser findUserByAccount(String account)throws Exception;

	public void save(TbUser tbUser)throws Exception;

	public TbUser findUserByAccount(String account, String password)throws Exception;

	public List<TbUser> findNoCheckUser()throws Exception;

	public void update(TbUser tbUser)throws Exception;

	public TbUserDetail findUserDetailByAccount(String account)throws Exception;

	public void saveOrupdateTbUserDetail(TbUserDetail person)throws Exception;
}
