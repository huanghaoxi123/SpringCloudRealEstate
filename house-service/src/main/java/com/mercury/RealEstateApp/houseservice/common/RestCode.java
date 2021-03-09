package com.mercury.RealEstateApp.houseservice.common;

public enum RestCode {
  OK(0,"OK"),
  UNKNOWN_ERROR(1,"House Service Exception"),
  WRONG_PAGE(10100,"Wrong Page"),
  USER_NOT_FOUND(10101,"Invalid User"),
  ILLEGAL_PARAMS(10102,"Invalid Argument");
  
  
  public final int code;
  public final String msg;
  private RestCode(int code,String msg){
    this.code = code;
    this.msg = msg;
  }

}
