package com.my.web.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="TbUser")
public class TbUser implements Serializable {
	
	private String account;
	private String password;
	private String name;
	private String pic;
	private int age;
	private String  sex;
	private String tag;
	private String description;
	private String createDate;
	private String lastLoginDate;
	private int active;//0 未激活 1激活
	
	public TbUser() {
	}
	

	public TbUser(String account, String password, int active) {
		super();
		this.account = account;
		this.password = password;
		this.active = active;
	}

   
   @Id
	@Column(name="account",length=100,nullable=false,unique=true)
	public String getAccount() {
		return account;
	}
	
	@Column(name="password",length=100,nullable=false)
	public String getPassword() {
		return password;
	}
	
	@Column(name="name",length=100)
	public String getName() {
		return name;
	}

	@Column(name="pic")
	public String getPic() {
		return pic;
	}
	@Column(name="age")
	public int getAge() {
		return age;
	}
	
	@Column(name="active")
	public int getActive() {
		return active;
	}
	
	@Column(name="sex")
	public String getSex() {
		return sex;
	}
	@Column(name="tag",length=400)
	public String getTag() {
		return tag;
	}
	@Column(name="description",length=800)
	public String getDescription() {
		return description;
	}
	@Column(name="createDate",length=20)
	public String getCreateDate() {
		return createDate;
	}
	@Column(name="lastLoginDate",length=20)
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public void setActive(int active) {
		this.active = active;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	

}
