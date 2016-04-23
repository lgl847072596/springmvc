package com.my.web.tool;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
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
	public static void writeAppToResponse(String path,String filename,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 response.setStatus(HttpStatus.OK.value());
		 response.setCharacterEncoding("utf-8");
		 response.setHeader("content-disposition","attachment;filename="+getCodeString(request,filename));
	    response.setContentType("application.x-msdownload;charset=UTF-8");
		
		FileInputStream fis=new FileInputStream(path);
		
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte[]buff=new byte[1024];
		int len=0;
		while((len=fis.read(buff))!=-1){
			bos.write(buff, 0, len);
		}
		fis.close();
		response.setContentLength(bos.size());
		response.getOutputStream().write(bos.toByteArray());
		bos.close();
		response.getWriter().flush();
	}

	public static String getCodeString(HttpServletRequest request,String fileName)throws Exception{
		String browName=null;
		String clientInfo=request.getHeader("User-agent");
		if(clientInfo!=null&&clientInfo.indexOf("MSIE")>0){
			//IE采用URLEncoder方式处理
			if(clientInfo.indexOf("MSIE6")>0||clientInfo.indexOf("MSIE5")>0){
				browName=new String(fileName.getBytes("GBK"),"ISO-8859-1");
			}else{
				browName=URLEncoder.encode(fileName,"UTF-8");
			}
		}else{
			browName=new String(fileName.getBytes("GBK"),"ISO-8859-1");
		}
		return browName;
	}
}
