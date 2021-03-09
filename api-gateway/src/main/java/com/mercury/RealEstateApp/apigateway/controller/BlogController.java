package com.mercury.RealEstateApp.apigateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mercury.RealEstateApp.apigateway.common.CommonConstants;
import com.mercury.RealEstateApp.apigateway.common.PageData;
import com.mercury.RealEstateApp.apigateway.common.PageParams;
import com.mercury.RealEstateApp.apigateway.model.Blog;
import com.mercury.RealEstateApp.apigateway.model.Comment;
import com.mercury.RealEstateApp.apigateway.model.House;
import com.mercury.RealEstateApp.apigateway.service.CommentService;
import com.mercury.RealEstateApp.apigateway.service.HouseService;

@Controller
public class BlogController {
  
  
  @Autowired
  private CommentService commentService;
  
  @Autowired
  private HouseService houseService; 
  
  
  @RequestMapping(value="blog/list",method={RequestMethod.POST,RequestMethod.GET})
  public String list(Integer pageSize,Integer pageNum,Blog query,ModelMap modelMap){
    PageData<Blog> ps = commentService.queryBlogs(query,PageParams.build(pageSize, pageNum));
    List<House> houses =  houseService.getHotHouse(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("ps", ps);
    return "/blog/listing";
  }
  
  @RequestMapping(value="blog/detail",method={RequestMethod.POST,RequestMethod.GET})
  public String blogDetail(int id,ModelMap modelMap){
    Blog blog = commentService.queryOneBlog(id);
    List<Comment> comments = commentService.getBlogComments(id);
    List<House> houses =  houseService.getHotHouse(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("blog", blog);
    modelMap.put("commentList", comments);
    return "/blog/detail";
  }
}
