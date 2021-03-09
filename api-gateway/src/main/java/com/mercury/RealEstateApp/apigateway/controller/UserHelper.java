package com.mercury.RealEstateApp.apigateway.controller;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Range;
import com.mercury.RealEstateApp.apigateway.common.ResultMsg;
import com.mercury.RealEstateApp.apigateway.model.User;

public class UserHelper {
	
	public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
	    if (StringUtils.isBlank(key) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
	      return ResultMsg.errorMsg("Invalid argument");
	    }
	    if (!Objects.equal(password, confirmPassword)) {
	      return ResultMsg.errorMsg("Password need to be same");
	    }
	    return ResultMsg.success();
	  }

	  public static ResultMsg validate(User account) {
	    if (StringUtils.isBlank(account.getEmail())) {
	      return ResultMsg.errorMsg("Invalid email");
	    }
	    if (StringUtils.isBlank(account.getName())) {
	      return ResultMsg.errorMsg("Invalid name");
	    }
	    if (StringUtils.isBlank(account.getConfirmPasswd()) || StringUtils.isBlank(account.getPasswd()) || !account.getPasswd().equals(account.getConfirmPasswd())) {
	      return ResultMsg.errorMsg("Invalid Password");
	    }
	    if (account.getPasswd().length() < 6){
	      return ResultMsg.errorMsg("Password length need to more than 6");
	    }
	    if (account.getType() == null || !Range.closed(1, 2).contains(account.getType())){
	      return ResultMsg.errorMsg("Invalid type");
	    }
	    return ResultMsg.success();
	  }

}
