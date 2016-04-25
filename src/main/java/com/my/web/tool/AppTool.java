package com.my.web.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.my.web.config.Contants;
import com.my.web.config.FileConfig;
import com.my.web.po.TbApp;

/**
 * @description 获取apk信息
 * @author guilin
 *
 */
public class AppTool {
	
	
	public static TbApp dex(String path,String platform)throws Exception{
		if(Contants.ANDROID.equals(platform)){
			return dexApk(path);
		}else{
			return dexApp(path);
		}
	}
	
	/**
	 * @description 解包apk文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static TbApp dexApk(String path)throws Exception{
			ApkInfo apkInfo = GetApkInfo.getApkInfoByFilePath(path);		
			TbApp model=new TbApp();
			model.setAppName("");
			model.setLog("");
			model.setUpdateTime(DateTool.formatData(new Date()));
			model.setUrl("");
			model.setDownloadCount(0L);
			model.setVersionCode(Integer.parseInt(apkInfo.getVersionCode()));
			model.setPackageName(apkInfo.getPackageName());
			model.setVersionName(apkInfo.getVersionName());		
			model.setPlatform(Contants.ANDROID);
		    return model;
	}
	/**
	 * @description 解包ipa文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static TbApp dexApp(String path)throws Exception{
		Map map=analysiIpa(new FileInputStream(path));
        String packageName=map.get("CFBundleIdentifier")+"";
        int versionCode=Integer.parseInt(map.get("CFBundleVersion")+"");
        String appName=map.get("CFBundleDisplayName")+"";
        String shortVersionString=(map.get("CFBundleShortVersionString")+"").replace("\\.", "");
        
        TbApp model=new TbApp();
		model.setAppName(appName);
		model.setLog("");
		model.setUpdateTime(DateTool.formatData(new Date()));
		model.setUrl("");
		model.setDownloadCount(0L);
		model.setVersionCode(Integer.parseInt(shortVersionString));
		model.setPackageName(packageName);
		model.setVersionName(shortVersionString);		
		model.setPlatform(Contants.IOS);
	    return model;
	}
	
	/**

	* 解析IPA文件

	* @date 2015.2.10

	*/

	private static Map<String, Object> analysiIpa(InputStream is) {
		

	// result map

	Map<String, Object> resultMap = new HashMap<String, Object>();

	try {

	ZipInputStream zipIns = new ZipInputStream(is);

	ZipEntry ze;

	InputStream infoIs = null;

	while ((ze = zipIns.getNextEntry()) != null) {

	if (!ze.isDirectory()) {

	String name = ze.getName();

	if (name.endsWith(".app/Info.plist")) {

	ByteArrayOutputStream _copy = new ByteArrayOutputStream();

	// int read = 0;

	int chunk = 0;

	byte[] data = new byte[256];

	while (-1 != (chunk = zipIns.read(data))) {

	// read += data.length;

	_copy.write(data, 0, chunk);

	}

	infoIs = new ByteArrayInputStream(_copy.toByteArray());

	break;

	}

	}

	}

	NSDictionary rootDict = (NSDictionary) PropertyListParser

	.parse(infoIs);

	String[] keyArray = rootDict.allKeys();

	for (String key : keyArray) {

	NSObject value = rootDict.objectForKey(key);

	if (key.equals("CFBundleSignature")) {

	continue;

	}

	if (value.getClass().equals(NSString.class)

	|| value.getClass().equals(NSNumber.class)) {

	resultMap.put(key, value.toString());

	}

	}

	zipIns.close();

	} catch (FileNotFoundException e) {

	resultMap.put("error", e.getStackTrace());

	} catch (Exception e) {

	resultMap.put("error", e.getStackTrace());

	}

	return resultMap;

	}
}
