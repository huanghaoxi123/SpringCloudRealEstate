package com.mercury.RealEstateApp.commentservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.mercury.RealEstateApp.commentservice.common.RestResponse;
import com.mercury.RealEstateApp.commentservice.common.Rests;
import com.mercury.RealEstateApp.commentservice.common.Rests.RestFunction;
import com.mercury.RealEstateApp.commentservice.model.User;
import com.mercury.RealEstateApp.commentservice.service.GenericRest;

@Repository
public class UserDao {

  @Autowired
  private GenericRest rest;
  
  @Value("${user.service.name}")
  private String userServiceName;

  public User getUserDetail(Long userId) {
    RestResponse<User> resp = Rests.exc(new RestFunction<RestResponse<User>>() {

      @Override
      public RestResponse<User> call() throws Exception {
        String url = Rests.toUrl(userServiceName, "/user/getById" + "?id="+userId);
        withParams(ImmutableMap.of("userId", userId));
        ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,new ParameterizedTypeReference<RestResponse<User>>() {});
        return responseEntity.getBody();

      }
     });
     return resp.getResult();
  }
  
  
}
