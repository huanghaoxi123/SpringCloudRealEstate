package com.mercury.RealEstateApp.apigateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;
import com.mercury.RealEstateApp.apigateway.model.House;
import com.mercury.RealEstateApp.apigateway.service.HouseService;

@Controller
public class HomepageController {

	@Autowired
	private HouseService houseService;

	@RequestMapping("index")
	public String accountsRegister(ModelMap modelMap) {
		List<House> houses = houseService.getLastest();
		modelMap.put("recomHouses", houses);
		return "/homepage/index";
	}

	@RequestMapping("")
	public String index() {
		return "redirect:/index";
	}

	@RequestMapping("test")
	@ResponseBody
	public String test() {
		return "test1";
	}

}
