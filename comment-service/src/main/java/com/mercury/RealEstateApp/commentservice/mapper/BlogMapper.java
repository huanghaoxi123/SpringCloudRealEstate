package com.mercury.RealEstateApp.commentservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mercury.RealEstateApp.commentservice.model.Blog;
import com.mercury.RealEstateApp.commentservice.model.LimitOffset;

@Mapper
public interface BlogMapper {
  
  public List<Blog> selectBlog(@Param("blog") Blog blog, @Param("pageParams")LimitOffset limitOffset);
  
  public Long selectBlogCount(Blog query);

}
