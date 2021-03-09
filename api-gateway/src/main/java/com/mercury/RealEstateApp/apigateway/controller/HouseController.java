package com.mercury.RealEstateApp.apigateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.mercury.RealEstateApp.apigateway.common.CommonConstants;
import com.mercury.RealEstateApp.apigateway.common.PageData;
import com.mercury.RealEstateApp.apigateway.common.PageParams;
import com.mercury.RealEstateApp.apigateway.common.ResultMsg;
import com.mercury.RealEstateApp.apigateway.common.UserContext;
import com.mercury.RealEstateApp.apigateway.model.Comment;
import com.mercury.RealEstateApp.apigateway.model.House;
import com.mercury.RealEstateApp.apigateway.model.User;
import com.mercury.RealEstateApp.apigateway.model.UserMsg;
import com.mercury.RealEstateApp.apigateway.service.AccountService;
import com.mercury.RealEstateApp.apigateway.service.AgencyService;
import com.mercury.RealEstateApp.apigateway.service.CommentService;
import com.mercury.RealEstateApp.apigateway.service.HouseService;

@Controller
public class HouseController {

	@Autowired
	private HouseService houseService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private CommentService commentService;
	
	@Autowired 
	private AccountService accountService;

	@RequestMapping(value = "house/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
		PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
		List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		
		//
		modelMap.put("recomHouses", rcHouses);
		
		modelMap.put("ps", ps);
		modelMap.put("vo", query);
		
		return "/house/listing";
	}

	@RequestMapping(value = "house/detail", method = { RequestMethod.POST, RequestMethod.GET })
	public String houseDetail(long id, ModelMap modelMap) {
		House house = houseService.queryOneHouse(id);
		List<Comment> comments = commentService.getHouseComments(id);
		List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		if (house.getUserId() != null) {
			if (!Objects.equal(0L, house.getUserId())) {
				modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
			}
		}
		
		//get user name by id;
		Long houseUserId = house.getUserId();
		
		User user = accountService.getUserById(houseUserId);
		
		String userName = user.getName();
		
		String phone = user.getPhone();
		
		house.setPhone(phone);
		
		house.setHuName(userName);
		
		//
		modelMap.put("house", house);
		modelMap.put("recomHouses", rcHouses);
		modelMap.put("commentList", comments);
		return "/house/detail";
	}

	// add

	@RequestMapping(value = "house/toAdd", method = { RequestMethod.POST, RequestMethod.GET })
	public String toAdd(ModelMap modelMap) {
		modelMap.put("citys", houseService.getAllCitys());
		modelMap.put("communitys", houseService.getAllCommunitys());
		return "/house/add";
	}

	@RequestMapping(value = "house/add", method = { RequestMethod.POST, RequestMethod.GET })
	public String doAdd(House house) {
		User user = UserContext.getUser();
		houseService.addHouse(house, user);
		return "redirect:/house/ownlist?" + ResultMsg.successMsg("Successfully add new property").asUrlParams();
	}

	//edit
	@RequestMapping(value = "house/toEdit", method = { RequestMethod.POST, RequestMethod.GET })
	public String houseEdit(Long id, ModelMap modelMap) {
	House house = houseService.queryOneHouse(id);
	modelMap.put("house", house);
	return "/house/edit";
	}

	@RequestMapping(value = "house/edit/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String toEdit(House updateHouse,@PathVariable Long id) {
	System.out.print("test ====" + id);
	House house = houseService.queryOneHouse(id);
	User user = UserContext.getUser();
	houseService.updateHouse(updateHouse, user, id);
	return "redirect:/house/ownlist?" + ResultMsg.successMsg("update success").asUrlParams();
	}
	
	//

	@RequestMapping(value = "house/leaveMsg", method = { RequestMethod.POST, RequestMethod.GET })
	public String houseMsg(UserMsg userMsg) {
		houseService.addUserMsg(userMsg);
		return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" + ResultMsg.successMsg("Leave message successfully").asUrlParams();
	}

	@ResponseBody
	@RequestMapping(value = "house/rating", method = { RequestMethod.POST, RequestMethod.GET })
	public ResultMsg houseRate(Double rating, Long id) {
		houseService.updateRating(id, rating);
		return ResultMsg.success();
	}

	@RequestMapping(value = "house/ownlist", method = { RequestMethod.POST, RequestMethod.GET })
	public String ownlist(House house, PageParams pageParams, ModelMap modelMap) {
		User user = UserContext.getUser();
		house.setUserId(user.getId());
		house.setBookmarked(false);
		modelMap.put("ps", houseService.queryHouse(house, pageParams));
		modelMap.put("pageType", "own");
		return "/house/ownlist";
	}

	@RequestMapping(value = "house/bookmarked", method = { RequestMethod.POST, RequestMethod.GET })
	public String bookmarked(House house, PageParams pageParams, ModelMap modelMap) {
		User user = UserContext.getUser();
		house.setBookmarked(true);
		house.setUserId(user.getId());
		modelMap.put("ps", houseService.queryHouse(house, pageParams));
		modelMap.put("pageType", "book");
		return "/house/ownlist";
	}

	@RequestMapping(value = "house/del", method = { RequestMethod.POST, RequestMethod.GET })
	public String delsale(Long id, String pageType) {
		User user = UserContext.getUser();
		houseService.unbindUser2House(id, user.getId(), pageType.equals("own") ? false : true);
		return "redirect:/house/ownlist";
	}

	@RequestMapping(value = "house/bookmark", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResultMsg bookmark(Long id, ModelMap modelMap) {
		User user = UserContext.getUser();
		houseService.bindUser2House(id, user.getId(), true);
		return ResultMsg.success();
	}

	@RequestMapping(value = "house/unbookmark", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResultMsg unbookmark(Long id, ModelMap modelMap) {
		User user = UserContext.getUser();
		houseService.unbindUser2House(id, user.getId(), true);
		return ResultMsg.success();
	}

}
