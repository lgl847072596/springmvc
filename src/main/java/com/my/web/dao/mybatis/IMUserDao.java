package com.my.web.dao.mybatis;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.my.web.po.TbUser;

@Repository
public interface IMUserDao {
	
	public TbUser findUserByAccount(String account)throws Exception;


	public TbUser findUser(HashMap<String,Object>map)throws Exception;
	
	

}
