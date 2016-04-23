package com.my.web.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Tb_Category")
public class TbAppCategory implements Serializable {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column
	private String categoryId;
	@Id
	@Column
	private String categoryName;
	@Id
	@Column
	private String belongAccount;
	@Id
	@Column
	private String platform;
	@Column
	private String createTime;
	
	public TbAppCategory() {
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBelongAccount() {
		return belongAccount;
	}

	public void setBelongAccount(String belongAccount) {
		this.belongAccount = belongAccount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "TbAppCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + ", belongAccount="
				+ belongAccount + ", createTime=" + createTime + ", platform=" + platform + "]";
	}

	
	

}
