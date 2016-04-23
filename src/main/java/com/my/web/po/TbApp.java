package com.my.web.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Tb_App")
public class TbApp implements Serializable {
	@Id
	 @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column
	private String appId;
	@Column
	private String appName;
	@Column(nullable=false)
	private String packageName;
	@Column(nullable=false)
	private String platform;
	@Column
	private int versionCode ;
	@Column
	private String versionName;
	@Column
	private String log;
	@Column
	private String forceUpdate;
	@Column
	private long downloadCount;
	@Column
	private String createTime;
	@Column
	private String updateTime;
	@Column
	private String mappingUrl;
	@Column
	private String url;
	@Column(nullable=false)
	private String belongAccount; 
	@Column
	private String categoryId;
	
	public TbApp() {
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public long getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(long downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getMappingUrl() {
		return mappingUrl;
	}

	public void setMappingUrl(String mappingUrl) {
		this.mappingUrl = mappingUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBelongAccount() {
		return belongAccount;
	}

	public void setBelongAccount(String belongAccount) {
		this.belongAccount = belongAccount;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "TbApp [appId=" + appId + ", appName=" + appName + ", packageName=" + packageName + ", platform="
				+ platform + ", versionCode=" + versionCode + ", versionName=" + versionName + ", log=" + log
				+ ", forceUpdate=" + forceUpdate + ", downloadCount=" + downloadCount + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", mappingUrl=" + mappingUrl + ", url=" + url + ", belongAccount="
				+ belongAccount + ", categoryId=" + categoryId + "]";
	}	
	
}
