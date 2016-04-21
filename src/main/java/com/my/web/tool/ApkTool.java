package com.my.web.tool;

import java.io.IOException;

import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;

import com.my.web.po.TbApp;

/**
 * @description 获取apk信息
 * @author guilin
 *
 */
public class ApkTool {
	
	public static TbApp dexApk(String path){
		try {
			ApkInfo apkInfo = GetApkInfo.getApkInfoByFilePath(path);		
			TbApp model=new TbApp();
			model.setAppName("");
			model.setVersionIntroduce("");
			model.setUpdateTime("");
			model.setUrl("");
			model.setDownLoadCount(0L);
			model.setPackageName(apkInfo.getPackageName());
			model.setVersionCode(Integer.parseInt(apkInfo.getVersionCode()));
			model.setVersionName(apkInfo.getVersionName());			
		    return model;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}  
	}
}
