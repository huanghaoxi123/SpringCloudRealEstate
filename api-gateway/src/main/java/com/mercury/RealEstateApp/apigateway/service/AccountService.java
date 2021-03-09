package com.mercury.RealEstateApp.apigateway.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mercury.RealEstateApp.apigateway.dao.UserDao;
import com.mercury.RealEstateApp.apigateway.model.User;
import com.mercury.RealEstateApp.apigateway.utils.BeanHelper;


@Service
public class AccountService {
	
	  @Value("${domain.name}")
	  private String domainName;
	  	  
	  @Autowired
	  private FileService fileService;
	  
	  @Autowired
	  private UserDao userDao;
	  
	  
	  public User getUserById(Long id){
	    User queryUser = new User();
	    queryUser.setId(id);
	    List<User> users =  getUserByQuery(queryUser);
	    if (!users.isEmpty()) {
	      return users.get(0);
	    }
	    return null;
	  }
	  	  
	  public List<User> getUserByQuery(User query){
	    List<User> users =  userDao.getUserList(query);
	    return users;
	  }
	  
	  public boolean addAccount(User account){
	    if (account.getAvatarFile() != null) {
	        List<String> imags = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
	        account.setAvatar(imags.get(0));
	    }
	    account.setEnableUrl("http://"+domainName+"/accounts/verify");
	    BeanHelper.setDefaultProp(account, User.class);
	    userDao.addUser(account);
	    return true;
	  }
	  
	  
	  //check if user exist in the db
	  public boolean isExist(String email){
	    return getUser(email) != null;
	  }

	  private User getUser(String email) {
	    User queryUser = new User();
	    queryUser.setEmail(email);
	    List<User> users =  getUserByQuery(queryUser);
	    if (!users.isEmpty()) {
	       return users.get(0);
	    }
	    return null;
	  }
	  
	  /*
	   * enable the user account
	   * */
	  public boolean enable(String key){
	    return userDao.enable(key);
	  }
	  
	  
	  /*
	   * User auth
	   * */
	  public User auth(String username, String password) {
	    if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
	       return null;
	    }
	    User user = new User();
	    user.setEmail(username);
	    user.setPasswd(password);
	    try {
	      user = userDao.authUser(user);
	    } catch (Exception e) {
	      return null;
	    }
	    return user;
	  }
	  
	  //user logout
	  
	  public void logout(String token) {
	    userDao.logout(token);
	  }
 
	  /**
	   * 调用重置通知接口
	   * @param email
	   */
	  @Async
	  public void remember(String email){
	    userDao.resetNotify(email,"http://" + domainName + "/accounts/reset");
	  }
	  
	  /**
	   * 重置密码操作
	   * @param email
	   * @param key
	   */
	  public User reset(String key,String password){
	    return userDao.reset(key,password);
	  }
	  

	  public String getResetEmail(String key) {
	    String email = userDao.getEmail(key);
	    return email;
	  }


	  public User updateUser(User user){
	    BeanHelper.onUpdate(user);
	    return  userDao.updateUser(user);
	  }

}
