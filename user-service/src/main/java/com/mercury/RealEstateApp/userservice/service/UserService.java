package com.mercury.RealEstateApp.userservice.service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.mercury.RealEstateApp.userservice.common.UserException;
import com.mercury.RealEstateApp.userservice.common.UserException.Type;
import com.mercury.RealEstateApp.userservice.mapper.UserMapper;
import com.mercury.RealEstateApp.userservice.model.User;
import com.mercury.RealEstateApp.userservice.utils.BeanHelper;
import com.mercury.RealEstateApp.userservice.utils.HashUtils;
import com.mercury.RealEstateApp.userservice.utils.JwtHelper;;

@Service
public class UserService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MailService mailService;

	@Value("${file.prefix}")
	private String imgPrefix;

	/*
	 * ===========Test User service ==========
	 */
	public List<User> getUsers() {
		return userMapper.selectUsers();
	}
	
	
	
	/*try to get user from the redis cache
	 * if the user not find, find user from db
	 * set the cache time be 5
	 * 
	 * */
	
	public User getUserById(Long id) {
		String key = "user:" + id;
		String json = redisTemplate.opsForValue().get(key);
		User user = null;
		if (Strings.isNullOrEmpty(json)) {
			user = userMapper.selectById(id);
			user.setAvatar(imgPrefix + user.getAvatar());
			String string = JSON.toJSONString(user);
			redisTemplate.opsForValue().set(key, string);
			redisTemplate.expire(key, 5, TimeUnit.MINUTES);
		} else {
			user = JSON.parseObject(json, User.class);
		}
		return user;
	}

	public List<User> getUserByQuery(User user) {
		List<User> users = userMapper.select(user);
		users.forEach(u -> {
			u.setAvatar(imgPrefix + u.getAvatar());
		});
		return users;
	}

	/*
	 * ======= Registration and login service ====
	 */

	public boolean addAccount(User user, String enableUrl) {
		user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
		BeanHelper.onInsert(user);
		userMapper.insert(user);
		registerNotify(user.getEmail(), enableUrl);
		return true;
	}

	/*
	 * Sent the activation email
	 */
	private void registerNotify(String email, String enableUrl) {
		String randomKey = HashUtils.hashString(email) + RandomStringUtils.randomAlphabetic(10);
		redisTemplate.opsForValue().set(randomKey, email);
		redisTemplate.expire(randomKey, 1, TimeUnit.HOURS);
		String content = enableUrl + "?key=" + randomKey;
		mailService.sendSimpleMail("Real Estate application website activation email", content, email);
	}

	public boolean enable(String key) {
		String email = redisTemplate.opsForValue().get(key);
		if (StringUtils.isBlank(email)) {
			throw new UserException(UserException.Type.USER_NOT_FOUND, "Invalid key");
		}
		User updateUser = new User();
		updateUser.setEmail(email);
		updateUser.setEnable(1);
		userMapper.update(updateUser);
		return true;
	}

	/*
	 * check password, generate token, then return the object
	 * 
	 */

	public User auth(String email, String passwd) throws UnsupportedEncodingException {

		if (StringUtils.isBlank(email) || StringUtils.isBlank(passwd)) {
			throw new UserException(Type.USER_AUTH_FAIL, "User Auth Fail");
		}
		User user = new User();
		user.setEmail(email);
		user.setPasswd(HashUtils.encryPassword(passwd));
		user.setEnable(1);
		List<User> list = getUserByQuery(user);
		if (!list.isEmpty()) {
			User retUser = list.get(0);
			onLogin(retUser);
			return retUser;
		}
		throw new UserException(Type.USER_AUTH_FAIL, "User Auth Fail");
	}

	public void onLogin(User user) throws UnsupportedEncodingException {

		String token = JwtHelper.genToken(ImmutableMap.of("email", user.getEmail(), "name", user.getName(), "ts",
				Instant.now().getEpochSecond() + ""));
		renewToken(token, user.getEmail());
		user.setToken(token);

	}

	private String renewToken(String token, String email) {
		redisTemplate.opsForValue().set(email, token);
		redisTemplate.expire(email, 30, TimeUnit.MINUTES);
		return token;
	}

	public User getLoginedUserByToken(String token) {
		Map<String, String> map = null;
		try {
			map = JwtHelper.verifyToken(token);
		} catch (Exception e) {
			throw new UserException(Type.USER_NOT_LOGIN, "User not login");
		}
		String email = map.get("email");
		Long expired = redisTemplate.getExpire(email);
		if (expired > 0L) {
			renewToken(token, email);
			User user = getUserByEmail(email);
			user.setToken(token);
			return user;
		}
		throw new UserException(Type.USER_NOT_LOGIN, "User not login");

	}

	private User getUserByEmail(String email) {
		User user = new User();
		user.setEmail(email);
		List<User> list = getUserByQuery(user);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		throw new UserException(Type.USER_NOT_FOUND, "User not found for " + email);
	}

	public void invalidate(String token) throws UnsupportedEncodingException {
		Map<String, String> map = JwtHelper.verifyToken(token);
		redisTemplate.delete(map.get("email"));
	}

	@Transactional(rollbackFor = Exception.class)
	public User updateUser(User user) {
		if (user.getEmail() == null) {
			return null;
		}
		if (!Strings.isNullOrEmpty(user.getPasswd())) {
			user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
		}
		userMapper.update(user);
		return userMapper.selectByEmail(user.getEmail());
	}

	public void resetNotify(String email, String url) {
		String randomKey = "reset_" + RandomStringUtils.randomAlphabetic(10);
		redisTemplate.opsForValue().set(randomKey, email);
		redisTemplate.expire(randomKey, 1, TimeUnit.HOURS);
		String content = url + "?key=" + randomKey;
		mailService.sendSimpleMail("Real Estate application password reset ", content, email);

	}

	public String getResetKeyEmail(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public User reset(String key, String password) {
		String email = getResetKeyEmail(key);
		User updateUser = new User();
		updateUser.setEmail(email);
		updateUser.setPasswd(HashUtils.encryPassword(password));
		userMapper.update(updateUser);
		return getUserByEmail(email);
	}

}