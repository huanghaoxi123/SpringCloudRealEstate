package com.mercury.RealEstateApp.houseservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.RealEstateApp.houseservice.mapper.CityMapper;
import com.mercury.RealEstateApp.houseservice.model.City;

@Service
public class CityService {
  
  @Autowired
  private CityMapper cityMapper;
  
  public List<City> getAllCitys(){
    City query = new City();
    return cityMapper.selectCitys(query);
  }

}