package com.mercury.RealEstateApp.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.RealEstateApp.apigateway.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public String getusername() {
		return userDao.getusername();
	}
	
	
}
