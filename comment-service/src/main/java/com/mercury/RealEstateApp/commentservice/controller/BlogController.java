package com.mercury.RealEstateApp.commentservice.controller;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercury.RealEstateApp.commentservice.common.RestResponse;
import com.mercury.RealEstateApp.commentservice.model.Blog;
import com.mercury.RealEstateApp.commentservice.model.BlogQueryReq;
import com.mercury.RealEstateApp.commentservice.model.ListResponse;
import com.mercury.RealEstateApp.commentservice.service.BlogService;

@RestController
@RequestMapping("blog")
public class BlogController {
  
  @Autowired
  private BlogService blogService;
  
  @RequestMapping("list")
  public RestResponse<ListResponse<Blog>> list(@RequestBody BlogQueryReq req){
    Pair<List<Blog>,Long> pair = blogService.queryBlog(req.getBlog(),req.getLimit(),req.getOffset());
    return RestResponse.success(ListResponse.build(pair.getKey(), pair.getValue()));
  }
  
  @RequestMapping("one")
  public RestResponse<Blog> one(Integer id){
    Blog blog = blogService.queryOneBlog(id);
    return RestResponse.success(blog);
  }

}