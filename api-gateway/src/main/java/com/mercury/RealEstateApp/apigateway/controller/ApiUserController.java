package com.mercury.RealEstateApp.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mercury.RealEstateApp.apigateway.common.RestResponse;
import com.mercury.RealEstateApp.apigateway.service.UserService;

@Controller
public class ApiUserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("test/username")
	@ResponseBody
	public RestResponse<String> getusername() {
		return RestResponse.success(userService.getusername());
	}
	
	@RequestMapping("test/username1")
	@ResponseBody
	public String getusername1() {
		return "username";
	}
	
}
