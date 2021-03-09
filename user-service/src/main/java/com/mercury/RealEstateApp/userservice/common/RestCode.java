package com.mercury.RealEstateApp.userservice.common;

public enum RestCode {
	OK(0,"ok"),
	UNKNOWN_ERROR(1,"unknown error"),
	TOKEN_INVALID(2,"TOKEN invalid"),
	USER_NOT_EXIST(3,"user not found"),
	WRONG_PAGE(10100,"wrong page"),
    LACK_PARAMS(10101,"invalid arguments");
	
	public final int code;
	public final String msg;
	
	private RestCode(int code,String msg){
		this.code = code;
		this.msg = msg;
	}


}

