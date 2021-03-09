package com.mercury.RealEstateApp.commentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Objects;
import com.mercury.RealEstateApp.commentservice.common.RestResponse;
import com.mercury.RealEstateApp.commentservice.model.Comment;
import com.mercury.RealEstateApp.commentservice.model.CommentReq;
import com.mercury.RealEstateApp.commentservice.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {
  
  @Autowired
  private CommentService commentService;
  
  @RequestMapping(value="add")
  public RestResponse<Object> leaveComment(@RequestBody CommentReq commentReq){
    Integer type = commentReq.getType();
    if (Objects.equal(1, type)) {
      commentService.addHouseComment(commentReq.getHouseId(),commentReq.getContent(),commentReq.getUserId());
    }else {
      commentService.addBlogComment(commentReq.getBlogId(),commentReq.getContent(),commentReq.getUserId());
    }
    return RestResponse.success();
  }
  
  @RequestMapping("list")
  public RestResponse<List<Comment>> list(@RequestBody CommentReq commentReq){
    Integer type = commentReq.getType();
    List<Comment> list = null;
    if (Objects.equal(1, type)) {
      list = commentService.getHouseComments(commentReq.getHouseId(),commentReq.getSize());
    }else {
      list = commentService.getBlogComments(commentReq.getBlogId(),commentReq.getSize());
    }
    return RestResponse.success(list);
  }
 



}
