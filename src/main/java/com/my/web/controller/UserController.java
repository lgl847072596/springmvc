package com.my.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.web.exception.CustomException;
import com.my.web.interceptor.AuthorHandlerInterceptor;
import com.my.web.model.JSONResultModel;
import com.my.web.model.UserPo;
import com.my.web.po.TbUser;
import com.my.web.service.IUserService;
import com.my.web.tool.CharacterTool;

import antlr.collections.List;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * @description 检测用户是否存在
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/check.json")
	@ResponseBody
	public JSONResultModel checkUser(@RequestParam String account) throws Exception {

		JSONResultModel jsonResultMode = null;
		TbUser tbUser = userService.findUserByAccount(account);
		if (tbUser == null) {
			jsonResultMode = new JSONResultModel<List>(false, "用户不存在");
		} else {
			jsonResultMode = new JSONResultModel<TbUser>(true, tbUser);
		}
		return jsonResultMode;

	}

	@RequestMapping("/public/register.json")
	@ResponseBody
	public JSONResultModel registerJson(@RequestBody  Map<String,String>map) throws Exception {
		
		String password=map.get("password");
		String account=map.get("account");
		
		if(CharacterTool.isNullOrEmpty(account)||CharacterTool.isNullOrEmpty(password)){
			throw new CustomException("用户名或密码不能为空");
		}
		
		JSONResultModel result = checkUser(account);
		if (!result.isSuccess()) {
			TbUser tbUser = new TbUser(account, password, 0);
			userService.save(tbUser);
			result = login(map);
		} else {
			result = new JSONResultModel<List>(false, "用户已经被注册了");
		}
		return result;
	}
	
	@RequestMapping("/public/register.action")
	public String register(@RequestParam String account, @RequestParam String password,HttpServletRequest request) throws Exception {
		JSONResultModel result = checkUser(account);
		if (!result.isSuccess()) {
			TbUser tbUser = new TbUser(account, password, 0);
			userService.save(tbUser);
			result = new JSONResultModel<TbUser>(true, tbUser);
			request.setAttribute("message", "注册成功");
			request.setAttribute("account", account);
			return "login";
		} else {
			throw new CustomException("用户已经被注册了");
		}
	}
	

	@RequestMapping("/public/login.action")
	public String login(@RequestParam String account, @RequestParam String password,HttpServletRequest request) throws Exception {
		TbUser tbUser = userService.findUserByAccount(account,password);
		if (tbUser == null) {
			request.getSession().removeAttribute("user");
			request.setAttribute("message", "用户名或密码错误");
			return "login";
		} else {
			request.getSession().setAttribute("user", tbUser);
			return "success";
		}
	}
	
	
	@RequestMapping("/public/login.json")
	@ResponseBody
	public JSONResultModel login(@RequestBody  Map<String,String>map) throws Exception {
		
		String password=map.get("password");
		String account=map.get("account");
		
		if(CharacterTool.isNullOrEmpty(account)||CharacterTool.isNullOrEmpty(password)){
			throw new CustomException("用户名或密码不能为空");
		}
		
		JSONResultModel result = checkUser(account);
		
		
		if (!result.isSuccess()) {
			result=new JSONResultModel<Map>(false,"用户名或密码错误");
		}else{
			TbUser tbUser=(TbUser) result.getEntity();
			if(!tbUser.getPassword().equals(password)){
				result=new JSONResultModel<Map>(false,"用户名或密码错误");
			}else if(tbUser.getActive()==0){
				result=new JSONResultModel<Map>(false,"用户未激活，请联系管理员");
			}else{
				Map resultMap=new HashMap();
				UserPo po=AuthorHandlerInterceptor.addUserToken(tbUser);
				resultMap.put("token", po.getToken());
				resultMap.put("user", tbUser);
				result=new JSONResultModel<Map>(true,resultMap);
			}
		}
		return result;
	}
	
}
