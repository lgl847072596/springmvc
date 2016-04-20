package com.my.web.tool;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSONObject;
/**
 * @description 内容输出
 * @author guilin
 *
 */
public class ResponseOutputJSONTool {

	private ResponseOutputJSONTool() {
	}
	
	public static void out(HttpServletResponse response,JSONObject content) throws IOException{
		 response.setStatus(HttpStatus.OK.value());
		 response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(content.toString());
		response.getWriter().flush();
	}
}
