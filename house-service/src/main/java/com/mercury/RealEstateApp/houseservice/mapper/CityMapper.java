package com.mercury.RealEstateApp.houseservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mercury.RealEstateApp.houseservice.model.City;

@Mapper
public interface CityMapper {
  
  public List<City> selectCitys(City city);

}
