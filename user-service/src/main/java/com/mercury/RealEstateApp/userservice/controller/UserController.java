package com.mercury.RealEstateApp.userservice.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercury.RealEstateApp.userservice.common.RestResponse;
import com.mercury.RealEstateApp.userservice.model.User;
import com.mercury.RealEstateApp.userservice.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/*
	 * ======= TEST USER SERVICE =======
	 */

	@RequestMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@RequestMapping("getusername")
	public RestResponse<String> getusername() {
		LOGGER.info("incoming request");
		return RestResponse.success("test-username");
	}

	@RequestMapping("getById")
	public RestResponse<User> getUserById(Long id) {
		User user = userService.getUserById(id);
		return RestResponse.success(user);
	}

	@RequestMapping("getList")
	public RestResponse<List<User>> getUserList(@RequestBody User user) {
		List<User> users = userService.getUserByQuery(user);
		return RestResponse.success(users);
	}

	/*
	 * ============ Registeration and Login =========
	 */

	@RequestMapping("add")
	public RestResponse<User> add(@RequestBody User user) {
		userService.addAccount(user, user.getEnableUrl());
		return RestResponse.success();
	}

	@RequestMapping("enable")
	public RestResponse<Object> enable(String key) {
		userService.enable(key);
		return RestResponse.success();
	}

	@RequestMapping("auth")
	public RestResponse<User> auth(@RequestBody User user) throws UnsupportedEncodingException {
		User finalUser = userService.auth(user.getEmail(), user.getPasswd());
		return RestResponse.success(finalUser);
	}

	@RequestMapping("get")
	public RestResponse<User> getUser(String token) {
		User finalUser = userService.getLoginedUserByToken(token);
		return RestResponse.success(finalUser);
	}

	@RequestMapping("logout")
	public RestResponse<Object> logout(String token) throws UnsupportedEncodingException {
		userService.invalidate(token);
		return RestResponse.success();
	}

	@RequestMapping("update")
	public RestResponse<User> update(@RequestBody User user) {
		User updateUser = userService.updateUser(user);
		return RestResponse.success(updateUser);
	}

	@RequestMapping("reset")
	public RestResponse<User> reset(String key, String password) {
		User updateUser = userService.reset(key, password);
		return RestResponse.success(updateUser);
	}

	@RequestMapping("getKeyEmail")
	public RestResponse<String> getKeyEmail(String key) {
		String email = userService.getResetKeyEmail(key);
		return RestResponse.success(email);
	}

	@RequestMapping("resetNotify")
	public RestResponse<User> resetNotify(String email, String url) {
		userService.resetNotify(email, url);
		return RestResponse.success();
	}

}
