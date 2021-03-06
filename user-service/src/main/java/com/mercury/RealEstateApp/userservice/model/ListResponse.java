package com.mercury.RealEstateApp.userservice.model;

import java.util.List;

public class ListResponse<T> {
  
  private List<T> list;
  
  private Long count;
  
  public static <T> ListResponse<T> build(List<T> list,Long count){
    ListResponse<T> response = new ListResponse<T>();
    response.setCount(count);
    response.setList(list);
    return response;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }
  
  

}
