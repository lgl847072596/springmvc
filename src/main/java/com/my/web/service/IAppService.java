package com.my.web.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.my.web.po.TbApp;

@Repository
public interface IAppService {
	
	public void save(TbApp tbApp)throws Exception;
	
	public void update(TbApp tbApp)throws Exception;
	
	public void delete(int id)throws Exception;
	
	public TbApp queryById(int id)throws Exception;
	
	public List<TbApp>queryAll()throws Exception;

}
