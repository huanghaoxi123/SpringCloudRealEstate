package com.mercury.RealEstateApp.apigateway.common;

public enum RestCode {
	  OK(0,"OK"),
	  UNKNOWN_ERROR(1,"user service not avaliable"),
	  WRONG_PAGE(10100,"page invalid"),
	  USER_NOT_FOUND(10101,"user not found"),
	  ILLEGAL_PARAMS(10102,"invalid arguments");
	  
	  
	  public final int code;
	  public final String msg;
	  private RestCode(int code,String msg){
	    this.code = code;
	    this.msg = msg;
	  }

	}

