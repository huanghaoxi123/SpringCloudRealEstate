package com.mercury.RealEstateApp.userservice.service;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.RealEstateApp.userservice.common.PageParams;
import com.mercury.RealEstateApp.userservice.mapper.AgencyMapper;
import com.mercury.RealEstateApp.userservice.model.Agency;
import com.mercury.RealEstateApp.userservice.model.User;


@Service
public class AgencyService {
	
	  @Autowired
	  private AgencyMapper agencyMapper;
	  
	  @Value("${file.prefix}")
	  private String imgPrefix;
	  
	  public List<Agency> getAllAgency(){
	    return agencyMapper.select(new Agency());
	  }

	  public Agency getAgency(Integer id){
	    Agency agency = new Agency();
	    agency.setId(id);
	    List<Agency> agencies = agencyMapper.select(agency);
	    if (agencies.isEmpty()) {
	      return null;
	    }
	    return agencies.get(0);
	  }

	  @Transactional(rollbackFor=Exception.class)
	  public int add(Agency agency) {
	    return  agencyMapper.insert(agency);
	  }

	  public Pair<List<User>,Long> getAllAgent(PageParams pageParams) {
	    List<User> agents =  agencyMapper.selectAgent(new User(),pageParams);
	    setImg(agents);
	    Long       count  =  agencyMapper.selectAgentCount(new User());
	    return ImmutablePair.of(agents, count);
	  }

	  
	  public void setImg(List<User> users){
	    users.forEach(u -> {
	      u.setAvatar(imgPrefix + u.getAvatar());
	    });
	  }
	  
	  public User getAgentDetail(Long id) {
	    User user = new User();
	    user.setId(id);
	    user.setType(2);
	    List<User> list = agencyMapper.selectAgent(user, new PageParams(1, 1));
	    setImg(list);
	    if (!list.isEmpty()) {
	      User agent = list.get(0);
	      Agency agency = new Agency();
	      agency.setId(agent.getAgencyId().intValue());
	      List<Agency> agencies = agencyMapper.select(agency);
	      if (!agencies.isEmpty()) {
	          agent.setAgencyName(agencies.get(0).getName());
	      }
	      return agent;
	    }
	    return null;
	  }

}
