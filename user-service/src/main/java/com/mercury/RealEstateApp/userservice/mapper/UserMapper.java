package com.mercury.RealEstateApp.userservice.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mercury.RealEstateApp.userservice.model.User;


@Mapper
public interface UserMapper {

  List<User>  selectUsers();
  
  User selectById(Long id);
  
  List<User> select(User user);
  
  int update(User user);
  
  int insert(User account);
  
  int delete(String email);
  
  User selectByEmail(String email);

}

