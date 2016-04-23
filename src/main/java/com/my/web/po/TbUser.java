package com.my.web.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Tb_User")
public class TbUser implements Serializable {
	@Id
	 @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column
	private String userId;
	@Column
	private String account;
	@Column
	private String password;
	@Column
	private String createTime;
	@Column
	private String checkTime;
	@Column
	private int roleLevel = 0;
	
	public TbUser() {
	}
	
	

	public TbUser(String account, String password, String createTime, int roleLevel) {
		super();
		this.account = account;
		this.password = password;
		this.createTime = createTime;
		this.roleLevel = roleLevel;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	@Override
	public String toString() {
		return "TbUser [userId=" + userId + ", account=" + account + ", password=" + password + ", createTime="
				+ createTime + ", checkTime=" + checkTime + ", roleLevel=" + roleLevel + "]";
	}
	
	
	
}
