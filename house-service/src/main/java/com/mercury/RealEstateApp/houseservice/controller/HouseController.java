package com.mercury.RealEstateApp.houseservice.controller;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Objects;
import com.mercury.RealEstateApp.houseservice.common.CommonConstants;
import com.mercury.RealEstateApp.houseservice.common.HouseUserType;
import com.mercury.RealEstateApp.houseservice.common.LimitOffset;
import com.mercury.RealEstateApp.houseservice.common.RestCode;
import com.mercury.RealEstateApp.houseservice.common.RestResponse;
import com.mercury.RealEstateApp.houseservice.common.ResultMsg;
import com.mercury.RealEstateApp.houseservice.model.City;
import com.mercury.RealEstateApp.houseservice.model.Community;
import com.mercury.RealEstateApp.houseservice.model.House;
import com.mercury.RealEstateApp.houseservice.model.HouseQueryReq;
import com.mercury.RealEstateApp.houseservice.model.HouseUserReq;
import com.mercury.RealEstateApp.houseservice.model.ListResponse;
import com.mercury.RealEstateApp.houseservice.model.UserMsg;
import com.mercury.RealEstateApp.houseservice.service.HouseService;
import com.mercury.RealEstateApp.houseservice.service.RecommendService;

@RequestMapping("house")
@RestController
public class HouseController {
  
  @Autowired
  private HouseService houseService;
  
  @Autowired
  private RecommendService recommendService;

  @RequestMapping("list")
  public RestResponse<ListResponse<House>> houseList(@RequestBody HouseQueryReq req){
    Integer limit  = req.getLimit();
    Integer offset = req.getOffset();
    House   query  = req.getQuery();
    Pair<List<House>, Long> pair = houseService.queryHouse(query,LimitOffset.build(limit, offset));
    return RestResponse.success(ListResponse.build(pair.getKey(), pair.getValue()));
  }
  
  @RequestMapping("detail")
  public RestResponse<House> houseDetail(long id){
    House house = houseService.queryOneHouse(id);
    recommendService.increaseHot(id);
    return RestResponse.success(house);
  }
  
  @RequestMapping("addUserMsg")
  public RestResponse<Object> houseMsg(@RequestBody UserMsg userMsg){
    houseService.addUserMsg(userMsg);
    return RestResponse.success();
  }
  
  @ResponseBody
  @RequestMapping("rating")
  public RestResponse<Object> houseRate(Double rating,Long id){
    houseService.updateRating(id,rating);
    return RestResponse.success();
  }
  
  @ResponseBody
  @RequestMapping("v1/rating")
  public ResultMsg houseV1Rate(Double rating,Long id){
    houseService.updateRating(id,rating);
    return ResultMsg.success();
  }
  
  
  @RequestMapping("allCommunitys")
  public RestResponse<List<Community>> toAdd(){
    List<Community> list = houseService.getAllCommunitys();
    return RestResponse.success(list);
  }
  
  @RequestMapping("allCitys")
  public RestResponse<List<City>> allCitys(){
    List<City> list = houseService.getAllCitys();
    return RestResponse.success(list);
  }
  
  /**
   * ????????????
   * @param house
   * @return
   */
  @RequestMapping("add")
  public RestResponse<Object> doAdd(@RequestBody House house){
    house.setState(CommonConstants.HOUSE_STATE_UP);
    if (house.getUserId() == null) {
      return RestResponse.error(RestCode.ILLEGAL_PARAMS);
    }
    
    Community com = houseService.getOneComById(house.getCommunityId());
    String address = house.getAddress() +", " + com.getName() + ", NJ";
    house.setAddress(address);
    houseService.addHouse(house,house.getUserId());
    return RestResponse.success();
  }
  
  
  //edit house
  @RequestMapping("edit")
  public RestResponse<Object> doEdit(@RequestBody House house, Long id){
    house.setState(CommonConstants.HOUSE_STATE_UP);
//    if (house.getUserId() == null) {
//      return RestResponse.error(RestCode.ILLEGAL_PARAMS);
//    }
    houseService.updateHouse(house,id);
    return RestResponse.success();
  }
  
  
  @RequestMapping("bind")
  public RestResponse<Object> delsale(@RequestBody HouseUserReq req){
    Integer bindType = req.getBindType();
    HouseUserType houseUserType = Objects.equal(bindType, 1) ? HouseUserType.SALE : HouseUserType.BOOKMARK;
    if (req.isUnBind()) {
      houseService.unbindUser2Houser(req.getHouseId(),req.getUserId(),houseUserType);
    }else {
      houseService.bindUser2House(req.getHouseId(), req.getUserId(), houseUserType);
    }
    return RestResponse.success();
  }
  
  @RequestMapping("hot")
  public RestResponse<List<House>> getHotHouse(Integer size){
   List<House> list =  recommendService.getHotHouse(size);
    return RestResponse.success(list);
  }
  
  @RequestMapping("lastest")
  public RestResponse<List<House>> getLastest(){
    return RestResponse.success(recommendService.getLastest());
  }
  
}

