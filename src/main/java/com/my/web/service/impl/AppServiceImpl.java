package com.my.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.web.dao.hibernate.IHAppDao;
import com.my.web.po.TbApp;
import com.my.web.service.IAppService;
@Service
public class AppServiceImpl implements IAppService {

	@Autowired
	private IHAppDao hAppDao;
	
	public void save(TbApp tbApp) throws Exception {
		hAppDao.save(tbApp);
	}

	public void update(TbApp tbApp) throws Exception {
		hAppDao.update(tbApp);
	}

	public void delete(int id) throws Exception {
		hAppDao.delete(id);
	}

	public TbApp queryById(int id) throws Exception {
		return hAppDao.queryById(id);
	}

	public List<TbApp> queryAll() throws Exception {
		return hAppDao.queryAll();
	}

}
