package com.my.web.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.my.web.po.TbUser;
/**
 * @description 用户包装类
 * @author guilin
 *
 */
public class UserPo {
   //用户
	private TbUser user;
	//token过期时间
	private Date outTime;
	//令牌
	private String token;
	
	public UserPo() {
		
	}
	
	public UserPo(TbUser user) {
		super();
		this.user = user;
		reset();
	}



	public TbUser getUser() {
		return user;
	}

	public void setUser(TbUser user) {
		this.user = user;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	//判断token是否过期
	public boolean hasOutTime(){
		return Calendar.getInstance().getTime().before(outTime);
	}
	
	/**
	 * @description 重置token和失效时间
	 */
	public void reset(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 30);
		outTime=calendar.getTime();
		token=UUID.randomUUID().toString();
	}
	
	
}
