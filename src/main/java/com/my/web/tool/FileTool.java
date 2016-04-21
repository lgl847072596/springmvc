package com.my.web.tool;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

public class FileTool {

	/**
	 * @description 写出文件到response 中
	 * @param path  文件的真实磁盘位置
	 * @param filename 下载的文件名字
	 * @param response
	 * @throws Exception
	 */
	public static void writeAppToResponse(String path,String filename,boolean isApk,HttpServletResponse response) throws Exception{
		 response.setStatus(HttpStatus.OK.value());
		 response.setCharacterEncoding("utf-8");
		 response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode(filename,"UTF-8"));
		if(isApk){
			 response.setContentType("application/vnd.android.package-archive;charset=UTF-8");
		}
		
		FileInputStream fis=new FileInputStream(path);
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte[]buff=new byte[1024];
		int len=0;
		while((len=fis.read(buff))!=-1){
			bos.write(buff, 0, len);
		}
		fis.close();
		response.getOutputStream().write(bos.toByteArray());
		bos.close();
		response.getWriter().flush();
	}
	
}
