package com.my.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.web.config.Contants;
import com.my.web.config.FileConfig;
import com.my.web.exception.CustomException;
import com.my.web.interceptor.AuthorHandlerInterceptor;
import com.my.web.model.JSONResultModel;
import com.my.web.model.UserPo;
import com.my.web.po.TbRole;
import com.my.web.po.TbUser;
import com.my.web.po.TbUserDetail;
import com.my.web.service.IUserService;
import com.my.web.tool.CharacterTool;
import com.my.web.tool.DateTool;
import com.my.web.tool.MD5;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private AppController appController;

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

	@RequestMapping("/public/register.action")
	public String register(@RequestParam String account, @RequestParam String password, HttpServletRequest request)
			throws Exception {

		if (CharacterTool.isNullOrEmpty(account) || CharacterTool.isNullOrEmpty(password)) {
			request.getSession().removeAttribute(Contants.USER);
			request.setAttribute("message", "用户名和密码都必填");
			return "login";
		}

		JSONResultModel result = checkUser(account);
		if (!result.isSuccess()) {
			TbUser tbUser = new TbUser(account, MD5.getMessageDigest(password.getBytes("utf-8")),
					DateTool.formatData(new Date()), 0);
			TbUserDetail tbUserDetail = new TbUserDetail();
			tbUserDetail.setAccount(tbUser.getAccount());
			userService.saveOrupdateTbUserDetail(tbUserDetail);
			userService.save(tbUser);

			return login(account, password, request);
		} else {
			throw new CustomException("用户已经被注册了");
		}
	}

	@RequestMapping("/public/login.action")
	public String login(@RequestParam String account, @RequestParam String password, HttpServletRequest request)
			throws Exception {

		if (CharacterTool.isNullOrEmpty(account) || CharacterTool.isNullOrEmpty(password)) {
			request.getSession().removeAttribute(Contants.USER);
			request.setAttribute("message", "用户名或密码错误");
			return "login";
		}

		TbUser tbUser = userService.findUserByAccount(account);
		if (tbUser == null || !tbUser.getPassword().equals(MD5.getMessageDigest(password.getBytes("utf-8")))) {
			request.getSession().removeAttribute(Contants.USER);
			request.setAttribute("message", "用户名或密码错误");
			return "login";
		} else {
			request.getSession().setAttribute(Contants.USER, tbUser);
			return "protected/app/appHome";
		}
	}

	@RequestMapping("/public/logout.action")
	public String logoutUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getSession().getAttribute(Contants.USER) != null) {
			request.getSession().removeAttribute(Contants.USER);
		}
		return "login";
	}

	/**
	 * @descritpion 获取未审核的用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findNoCheckUser.action")
	public String findNoCheckUser(HttpServletRequest request) throws Exception {
		TbUser po = (TbUser) request.getSession().getAttribute(Contants.USER);
		if (po.getRoleLevel() > 1) {
			List<TbUser> list = userService.findNoCheckUser();
			request.setAttribute("userList", list);
			return "protected/user/noCheckUserList";
		} else {
			throw new CustomException("您不具备该权限");
		}
	}

	/**
	 * @description 审核用户
	 * @param account
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "adminCheckUser.action")
	public String adminCheckUser(String account, int roleLevel, HttpServletRequest request) throws Exception {
		TbUser po = (TbUser) request.getSession().getAttribute(Contants.USER);
		// 普通，管理员权限申请，超级管理员只有一个
		if (po.getRoleLevel() >= 2 && roleLevel < 3) {
			// 管理员权限申请
			if (roleLevel > 1) {
				// 需要超级管理员
				if (po.getRoleLevel() > 2) {
					JSONResultModel result = checkUser(account);
					if (result.isSuccess()) {
						TbUser tbUser = (TbUser) result.getEntity();
						tbUser.setCheckTime(DateTool.formatData(new Date()));
						tbUser.setRoleLevel(roleLevel);
						userService.update(tbUser);
					}
					return findNoCheckUser(request);
				} else {
					throw new CustomException("您不具备该权限");
				}
			} else {
				JSONResultModel result = checkUser(account);
				if (result.isSuccess()) {
					TbUser tbUser = (TbUser) result.getEntity();
					tbUser.setCheckTime(DateTool.formatData(new Date()));
					tbUser.setRoleLevel(roleLevel);
					userService.update(tbUser);
				}
				return findNoCheckUser(request);
			}
		} else {
			throw new CustomException("您不具备该权限");
		}

	}

	/**
	 * @查找详细信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findUserDetail.action")
	public String findUserDetail(HttpServletRequest request) throws Exception {
		TbUser po = (TbUser) request.getSession().getAttribute(Contants.USER);
		TbUserDetail detail = userService.findUserDetailByAccount(po.getAccount());
		if (detail != null) {
			request.setAttribute("person", detail);
		}
		return "protected/user/userCenter";
	}

	@RequestMapping("/updateUserDetail.action")
	public String updateUserDetail(String account,String name,String company,String teamName
			,String tag,String password,String repeatPassword
			, HttpServletRequest request)
			throws Exception {
		TbUserDetail person = new TbUserDetail();
		person.setName(name);
		person.setCompany(company);
		person.setTeamName(teamName);
		person.setTag(tag);
	
		
		TbUser po = (TbUser) request.getSession().getAttribute(Contants.USER);
		// 需要修改密码
		if (!CharacterTool.isNullOrEmpty(password)) {
			if (CharacterTool.isNullOrEmpty(repeatPassword)) {
				request.setAttribute("message", "确认密码不能为空");
				return findUserDetail(request);
			} else {
				if (!password.equals(repeatPassword)) {
					request.setAttribute("message", "确认密码不正确");
					return findUserDetail(request);
				} else {
					// 修改密码
					po.setPassword(MD5.getMessageDigest(password.getBytes("utf-8")));
					userService.update(po);
				}
			}
		}

		person.setAccount(po.getAccount());
		userService.saveOrupdateTbUserDetail(person);
		request.setAttribute("message", "更新成功");

		return findUserDetail(request);
	}

}
