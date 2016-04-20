package com.my.web.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

import com.alibaba.fastjson.JSONObject;
import com.my.web.model.JSONResultModel;
import com.my.web.tool.ResponseOutputJSONTool;
/**
 * @description 异常处理
 * @author lgl
 *
 */
public class CustomHandlerExceptionResolver extends HandlerExceptionResolverComposite {
	
	private String errorPage;
	
	/**
	 * Resolve the exception by iterating over the list of configured exception resolvers.
	 * The first one to return a ModelAndView instance wins. Otherwise {@code null} is returned.
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
										 HttpServletResponse response,
										 Object handler,
										 Exception ex) {
		//异常检测
		CustomException exception=null;
		if(ex instanceof CustomException){
			exception=(CustomException) ex;
		}else{
			exception=new CustomException(ex.getMessage()!=null?ex.getMessage():"未知异常", 0);
		}
		
		 
		Method method = ((HandlerMethod)handler).getMethod();
		 ModelAndView modelAndView=null;
		 ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);  
		 //Json 请求处理
		 if(responseBodyAnn!=null){
			 
			 modelAndView=new ModelAndView();
			 //错误内容
			 JSONResultModel jsonResultModel=new JSONResultModel<List>(false, exception.getErrorCode(), exception.getMessage());
			 try {
				ResponseOutputJSONTool.out(response,(JSONObject)JSONObject.toJSON(jsonResultModel));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 //其他请求处理
		 else{
			 modelAndView=new ModelAndView();
			 response.setCharacterEncoding("utf-8");
			 modelAndView.setViewName(getErrorPage());
			 modelAndView.addObject("message", exception.getMessage());
		 }
		return modelAndView;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

}
