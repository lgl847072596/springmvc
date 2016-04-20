package com.my.web.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.my.web.po.TbUser;

@Repository
public interface IHUserDao {
	
	public void save(TbUser tbUser)throws Exception;

}
