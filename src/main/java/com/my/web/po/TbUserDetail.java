package com.my.web.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Tb_User_Detail")
public class TbUserDetail implements Serializable {
	@Id
	@Column
	private String account;
	@Column
	private String name;
	@Column
	private String company;
	@Column
	private String teamName;
	@Column
	private String sex;
	@Column
	private String pic;
	@Column
	private String birthDay;
	@Column
	private String tag;
	
	public TbUserDetail() {
		// TODO Auto-generated constructor stub
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "TbUserDetail [account=" + account + ", name=" + name + ", company=" + company + ", teamName=" + teamName
				+ ", sex=" + sex + ", pic=" + pic + ", birthDay=" + birthDay + ", tag=" + tag + "]";
	}
	
	

}
