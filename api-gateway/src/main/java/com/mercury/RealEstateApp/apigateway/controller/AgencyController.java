package com.mercury.RealEstateApp.apigateway.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Objects;
import com.mercury.RealEstateApp.apigateway.common.CommonConstants;
import com.mercury.RealEstateApp.apigateway.common.PageData;
import com.mercury.RealEstateApp.apigateway.common.PageParams;
import com.mercury.RealEstateApp.apigateway.common.ResultMsg;
import com.mercury.RealEstateApp.apigateway.common.UserContext;
import com.mercury.RealEstateApp.apigateway.model.Agency;
import com.mercury.RealEstateApp.apigateway.model.House;
import com.mercury.RealEstateApp.apigateway.model.User;
import com.mercury.RealEstateApp.apigateway.service.AgencyService;
import com.mercury.RealEstateApp.apigateway.service.HouseService;
import com.mercury.RealEstateApp.apigateway.service.MailService;

@Controller
public class AgencyController {

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private HouseService houseService;

	@Autowired
	private MailService mailService;

	@RequestMapping("agency/create")
	public String agencyCreate() {
		User user = UserContext.getUser();
		if (user == null || !Objects.equal(user.getEmail(), "spring_boot@163.com")) {
			return "redirect:/accounts/signin?" + ResultMsg.errorMsg("Please Login").asUrlParams();
		}
		return "/user/agency/create";
	}

	@RequestMapping("agency/submit")
	public String agencySubmit(Agency agency) {
		User user = UserContext.getUser();
		if (user == null || !Objects.equal(user.getEmail(), "spring_boot@163.com")) {
			return "redirect:/accounts/signin?" + ResultMsg.errorMsg("Please Login").asUrlParams();
		}
		agencyService.add(agency);
		return "redirect:/index?" + ResultMsg.errorMsg("Create Successfully").asUrlParams();
	}

	@RequestMapping("agency/list")
	public String agencyList(ModelMap modelMap) {
		List<Agency> agencies = agencyService.getAllAgency();
		List<House> houses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", houses);
		modelMap.put("agencyList", agencies);
		return "/user/agency/agencyList";
	}

	@RequestMapping("/agency/agentList")
	public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
		if (pageSize == null) {
			pageSize = 6;
		}
		PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
		List<House> houses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", houses);
		modelMap.put("ps", ps);
		return "/user/agent/agentList";
	}

	@RequestMapping("/agency/agentDetail")
	public String agentDetail(Long id, ModelMap modelMap) {
		User user = agencyService.getAgentDetail(id);
		List<House> houses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		House query = new House();
		query.setUserId(id);
		query.setBookmarked(false);
		PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3, 1));
		if (bindHouse != null) {
			modelMap.put("bindHouses", bindHouse.getList());
		}
		modelMap.put("recomHouses", houses);
		modelMap.put("agent", user);
		return "/user/agent/agentDetail";
	}
	
	
//	@RequestMapping("/agency/agentList")
//	public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
//		if (pageSize == null) {
//			pageSize = 6;
//		}
//		PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
//		List<House> houses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
//		modelMap.put("recomHouses", houses);
//		modelMap.put("ps", ps);
//		return "/user/agent/agentList";
//	}

// 
	@RequestMapping("/agency/agencyDetail")
	public String agencyDetail(Integer id, ModelMap modelMap,Integer pageSize, Integer pageNum) {
		Agency agency = agencyService.getAgency(id);
		List<House> houses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		
		PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
		
		List<User> ls = ps.getList();// cast int to long
		Long id1 = Long.valueOf(id.longValue());
		
		Iterator<User> it = ls.iterator();
		while (it.hasNext()) {
		    User user = it.next();
		    try {
		    if (user.getAgencyId()!= id1) {
		        it.remove();
		    }
		    }catch(Throwable e){
		    	e.printStackTrace();
		    }
		}
		ps.setList(ls);
		
		modelMap.put("ps", ps);
		modelMap.put("recomHouses", houses);
		modelMap.put("agency", agency);
		return "/user/agency/agencyDetail";
	}

	@RequestMapping("/agency/agentMsg")
	public String agentMsg(Long id, String msg, String name, String email, ModelMap modelMap) {
		User user = agencyService.getAgentDetail(id);
		mailService.sendSimpleMail("From " + email + " Consultation", msg, user.getEmail());
		return "redirect:/agency/agentDetail?id=" + id + "&" + ResultMsg.successMsg("leave a message succeessfully").asUrlParams();
	}

}
