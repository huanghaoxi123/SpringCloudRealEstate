package com.mercury.RealEstateApp.commentservice.common;

public enum RestCode {
  OK(0,"OK"),
  UNKNOWN_ERROR(1,"Comment service not avaliable"),
  WRONG_PAGE(10100,"Wrong page"),
  USER_NOT_FOUND(10101,"User not found"),
  ILLEGAL_PARAMS(10102,"invalid argument");
  
  
  public final int code;
  public final String msg;
  private RestCode(int code,String msg){
    this.code = code;
    this.msg = msg;
  }

}
