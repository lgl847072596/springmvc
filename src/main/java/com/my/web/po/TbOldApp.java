package com.my.web.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TbOldApp")
public class TbOldApp implements Serializable {

	// 下载路径
	private String url;
	// 应用的名字
	private String appName;
	// 包名
	private String packageName;// com.ylzinfo.palmhospital,
	// 版本
	private String versionName;// =3.3.0,
	// 版本code
	private int versionCode;// =330
	// 版本说明
	private String versionIntroduce;
	// 版本更新时间
	private String updateTime;
	// 该版本下载次数
	private Long downLoadCount = 0L;
	// 平台
	private String platform;

	public TbOldApp() {
		// TODO Auto-generated constructor stub
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}
    
	@Column(name = "appName")
	public String getAppName() {
		return appName;
	}

	@Id
	@Column(name = "packageName")
	public String getPackageName() {
		return packageName;
	}


	@Column(name = "versionName")
	public String getVersionName() {
		return versionName;
	}
	@Id
	@Column(name = "versionCode")
	public int getVersionCode() {
		return versionCode;
	}

	@Column(name = "versionIntroduce")
	public String getVersionIntroduce() {
		return versionIntroduce;
	}

	@Column(name = "updateTime")
	public String getUpdateTime() {
		return updateTime;
	}

	@Column(name = "downLoadCount")
	public Long getDownLoadCount() {
		return downLoadCount;
	}	
	@Id
	@Column(name = "platform")
	public String getPlatform() {
		return platform;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public void setVersionIntroduce(String versionIntroduce) {
		this.versionIntroduce = versionIntroduce;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setDownLoadCount(Long downLoadCount) {
		this.downLoadCount = downLoadCount;
	}



	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "TbOldApp [url=" + url + ", appName=" + appName + ", packageName=" + packageName + ", versionName="
				+ versionName + ", versionCode=" + versionCode + ", versionIntroduce=" + versionIntroduce
				+ ", updateTime=" + updateTime + ", downLoadCount=" + downLoadCount + ", platform=" + platform + "]";
	}

	

}
