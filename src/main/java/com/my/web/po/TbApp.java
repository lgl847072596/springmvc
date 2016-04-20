package com.my.web.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="TbApp")
public class TbApp implements Serializable {
	
	private String id;
	//路径
	private String url;
	//版本
	private String version;
	//大小
	private String size;
	//名字
	private String name;
	//更新说明
	private String updateDesc;
	
	//下载次数
	private Long downLoadCount=0L; 
	//更新日期
	private String updateDate;
	
	
	public TbApp() {
	}
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id",unique=true)
	public String getId() {
		return id;
	}

	

	@Column(name="url",nullable=false)
	public String getUrl() {
		return url;
	}

	
	@Column(name="version",nullable=false)
	public String getVersion() {
		return version;
	}

	
	@Column(name="size",nullable=false)
	public String getSize() {
		return size;
	}

	
	@Column(name="name",nullable=false)
	public String getName() {
		return name;
	}

	
	@Column(name="updateDesc")
	public String getUpdateDesc() {
		return updateDesc;
	}

	
	@Column(name="updateDate",nullable=false)
	public String getUpdateDate() {
		return updateDate;
	}


	@Column(name="downLoadCount")
	public Long getDownLoadCount() {
		return downLoadCount;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}
	public void setDownLoadCount(Long downLoadCount) {
		this.downLoadCount = downLoadCount;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	

	
	
	

}
