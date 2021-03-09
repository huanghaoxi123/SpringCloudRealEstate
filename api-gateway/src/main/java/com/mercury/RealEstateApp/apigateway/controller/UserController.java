package com.mercury.RealEstateApp.apigateway.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mercury.RealEstateApp.apigateway.common.ResultMsg;
import com.mercury.RealEstateApp.apigateway.common.UserContext;
import com.mercury.RealEstateApp.apigateway.model.Agency;
import com.mercury.RealEstateApp.apigateway.model.User;
import com.mercury.RealEstateApp.apigateway.service.AccountService;
import com.mercury.RealEstateApp.apigateway.service.AgencyService;

@Controller
public class UserController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AgencyService agencyService;

	/*
	 * ---------------------Registration---------------------------
	 */
	@RequestMapping(value = "accounts/register", method = { RequestMethod.POST, RequestMethod.GET })
	public String accountsSubmit(User account, ModelMap modelMap) {
		if (account == null || account.getName() == null) {
			modelMap.put("agencyList", agencyService.getAllAgency());
			return "user/accounts/register";
		}
		ResultMsg retMsg = UserHelper.validate(account);

		if (retMsg.isSuccess()) {
			boolean exist = accountService.isExist(account.getEmail());
			if (!exist) {
				accountService.addAccount(account);
				modelMap.put("success_email", account.getEmail());
				return "user/accounts/registerSubmit";
			} else {
				return "redirect:/accounts/register?" + ResultMsg.errorMsg("Email already exist").asUrlParams();
			}
		} else {
			return "redirect:/accounts/register?" + ResultMsg.errorMsg("Invalid argument").asUrlParams();
		}
	}

	@RequestMapping("accounts/verify")
	public String verify(String key) {
		boolean result = accountService.enable(key);
		if (result) {
			return "redirect:/index?" + ResultMsg.successMsg("User Account Activated Successfully").asUrlParams();
		} else {
			return "redirect:/accounts/signin?" + ResultMsg.errorMsg("User Account Activated Failed").asUrlParams();
		}
	}

	/*
	 * ---------Login ----------
	 */
	@RequestMapping(value = "/accounts/signin", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginSubmit(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (username == null || password == null) {
			req.setAttribute("target", req.getParameter("target"));
			return "/user/accounts/signin";
		}
		User user = accountService.auth(username, password);
		if (user == null) {
			return "redirect:/accounts/signin?" + "username=" + username + "&"
					+ ResultMsg.errorMsg("Invalid username or password").asUrlParams();
		} else {
			UserContext.setUser(user);
			return StringUtils.isNotBlank(req.getParameter("target")) ? "redirect:" + req.getParameter("target")
					: "redirect:/index";
		}
	}

	/*
	 * ------Logout-------
	 */
	@RequestMapping("accounts/logout")
	public String logout(HttpServletRequest req) {
		User user = UserContext.getUser();
		accountService.logout(user.getToken());
		return "redirect:/index";
	}

	/*
	 * 
	 * Personal information page
	 * 
	 */
	@RequestMapping(value = "accounts/profile", method = { RequestMethod.POST, RequestMethod.GET })
	public String profile(ModelMap model) {
		List<Agency> list = agencyService.getAllAgency();
		model.addAttribute("agencyList", list);
		return "/user/accounts/profile";
	}

	@RequestMapping(value = "accounts/profileSubmit", method = { RequestMethod.POST, RequestMethod.GET })
	public String profileSubmit(HttpServletRequest req, User updateUser, ModelMap model) {
		if (updateUser.getEmail() == null) {
			return "redirect:/accounts/profile?" + ResultMsg.errorMsg("Email can not be empty").asUrlParams();
		}
		User user = accountService.updateUser(updateUser);
		UserContext.setUser(user);
		return "redirect:/accounts/profile?" + ResultMsg.successMsg("Update successfully").asUrlParams();
	}

	/*
	 * Change the User Password
	 */
	@RequestMapping("accounts/changePassword")
	public String changePassword(String email, String password, String newPassword, String confirmPassword,
			ModelMap mode) {
		User user = accountService.auth(email, password);
		if (user == null || !confirmPassword.equals(newPassword)) {
			return "redirct:/accounts/profile?" + ResultMsg.errorMsg("Password Need to Match").asUrlParams();
		}
		User updateUser = new User();
		updateUser.setPasswd(newPassword);
		updateUser.setEmail(email);
		accountService.updateUser(updateUser);
		return "redirect:/accounts/profile?" + ResultMsg.successMsg("Update Information Successfully").asUrlParams();
	}

	@RequestMapping("accounts/remember")
	public String remember(String username, ModelMap modelMap) {
		if (StringUtils.isBlank(username)) {
			return "redirect:/accounts/signin?" + ResultMsg.errorMsg("Email Can't be Empty").asUrlParams();
		}
		accountService.remember(username);
		modelMap.put("email", username);
		return "/user/accounts/remember";
	}

	@RequestMapping("accounts/reset")
	public String reset(String key, ModelMap modelMap) {
		String email = accountService.getResetEmail(key);
		if (StringUtils.isBlank(email)) {
			return "redirect:/accounts/signin?" + ResultMsg.errorMsg("Invalid Reset Link");
		}
		modelMap.put("email", email);
		modelMap.put("success_key", key);
		return "/user/accounts/reset";
	}

	@RequestMapping(value = "accounts/resetSubmit", method = { RequestMethod.POST, RequestMethod.GET })
	public String resetSubmit(HttpServletRequest req, User user) {
		ResultMsg retMsg = UserHelper.validateResetPassword(user.getKey(), user.getPasswd(), user.getConfirmPasswd());
		if (!retMsg.isSuccess()) {
			String suffix = "";
			if (StringUtils.isNotBlank(user.getKey())) {
				suffix = "email=" + accountService.getResetEmail(user.getKey()) + "&key=" + user.getKey() + "&";
			}
			return "redirect:/accounts/reset?" + suffix + retMsg.asUrlParams();
		}
		User updatedUser = accountService.reset(user.getKey(), user.getPasswd());
		UserContext.setUser(updatedUser);
		return "redirect:/index?" + retMsg.asUrlParams();
	}

}
