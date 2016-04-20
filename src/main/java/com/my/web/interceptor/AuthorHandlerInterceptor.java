package com.my.web.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.my.web.exception.CustomException;
import com.my.web.model.JSONResultModel;
import com.my.web.model.UserPo;
import com.my.web.po.TbUser;
import com.my.web.tool.CharacterTool;
import com.my.web.tool.ResponseOutputJSONTool;

/**
 * @description 权限拦截
 * @author guilin
 *
 */
public class AuthorHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private static HashMap<String,UserPo>userToken=new HashMap<String,UserPo>();
	

	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURL().toString();

		if (url.contains("public")) {
			return true;
		} else {

			if(isJsonRequest(url)){
				return checkJsonRequest(request,response,handler);
			}else{
				return checkRequest(request,response,handler);
			}
			
		}
	}
	
	public boolean isJsonRequest(String url){
		return url.endsWith(".json");
	}
	
    /**
     * @description 移动设备请求json权限拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
	public boolean checkJsonRequest(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

		String account=request.getHeader("account");
		String token=request.getHeader("token");
		String password=request.getHeader("password");
		
		if(CharacterTool.isNullOrEmpty(account)){
			throw new CustomException("头部缺少account",ErrorCode.error_100);
		}
		
		if(!CharacterTool.isNullOrEmpty(password)){
			if(!userToken.containsKey(account)){
				throw new CustomException("请先登录",ErrorCode.error_100);
			}else{
				//获取的是令牌
				if(userToken.get(account).getUser().getPassword().equals(password)){
					Map map=new HashMap();
					map.put("token", userToken.get(account).getToken());
					JSONResultModel jsonResultModel=new JSONResultModel<Map>(true, map);
					jsonResultModel.setErrorCode(ErrorCode.error_101);	
					ResponseOutputJSONTool.out(response,(JSONObject)JSONObject.toJSON(jsonResultModel));
					return false;
				}
			}
		}
		
		
		if(CharacterTool.isNullOrEmpty(token)){
			throw new CustomException("头部缺少令牌",100);
		}
		//token失效或token不一致
		if(!(userToken.containsKey(account)&&userToken.get(account).getToken().equals(token)&&!userToken.get(account).hasOutTime())){
			//用户名密码正确则重置token返回
			if(userToken.containsKey(account)&&!CharacterTool.isNullOrEmpty(password)&&userToken.get(account).getUser().getPassword().equals(password)){
				//重置token
				userToken.get(account).reset();
				
				Map map=new HashMap();
				map.put("token", userToken.get(account).getToken());
				JSONResultModel jsonResultModel=new JSONResultModel<Map>(true, map);
				jsonResultModel.setErrorCode(101);	
				
				ResponseOutputJSONTool.out(response,(JSONObject)JSONObject.toJSON(jsonResultModel));
				return false;
			}else{
				throw new CustomException("令牌失效",100);
			}
		}
		
		return true;
		
	}

	/**
	 * @description 网页请求拦截
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws IOException
	 */
	public boolean checkRequest(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		//未登录的全部前往登录页面
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect(request.getContextPath()+"/page/login.jsp");
			return false;
		}
        return true;
	}


	/**
	 * @description 生成token
	 * @param user
	 * @return
	 */
	public static UserPo addUserToken(TbUser user){
		if(userToken.containsKey(user.getAccount())){
			userToken.get(user.getAccount()).reset();
		}else{
			userToken.put(user.getAccount(), new UserPo(user));
		}
		return userToken.get(user.getAccount());
	}

}
