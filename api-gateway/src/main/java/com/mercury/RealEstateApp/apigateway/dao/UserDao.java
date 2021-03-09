package com.mercury.RealEstateApp.apigateway.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.mercury.RealEstateApp.apigateway.common.RestResponse;
import com.mercury.RealEstateApp.apigateway.config.GenericRest;
import com.mercury.RealEstateApp.apigateway.model.Agency;
import com.mercury.RealEstateApp.apigateway.model.ListResponse;
import com.mercury.RealEstateApp.apigateway.model.User;
import com.mercury.RealEstateApp.apigateway.utils.Rests;

@Repository
public class UserDao {

	@Autowired
	private GenericRest rest;

	@Value("${user.service.name}")
	private String userServiceName;

	// test user service
	public String getusername() {
		String url = "direct://http://127.0.0.1:8083/user/getusername";
		RestResponse<String> response = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
		}).getBody();
		return response.getResult();
	}

	// get the app user list
	public List<User> getUserList(User query) {
		ResponseEntity<RestResponse<List<User>>> resultEntity = rest.post("http://" + userServiceName + "/user/getList",
				query, new ParameterizedTypeReference<RestResponse<List<User>>>() {
				});
		RestResponse<List<User>> restResponse = resultEntity.getBody();
		if (restResponse.getCode() == 0) {
			return restResponse.getResult();
		} else {
			return null;
		}
	}

	// add the user to application
	public User addUser(User account) {
		String url = "http://" + userServiceName + "/user/add";
		ResponseEntity<RestResponse<User>> responseEntity = rest.post(url, account,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		} else {
			throw new IllegalStateException("Can not add user");
		}
	}

	/**
	 * user activation
	 * 
	 * @param key
	 */
	public boolean enable(String key) {
		Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/user/enable?key=" + key);
			ResponseEntity<RestResponse<Object>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<Object>>() {
					});
			return responseEntity.getBody();
		});
		return true;
	}

	public User authUser(User user) {
		String url = "http://" + userServiceName + "/user/auth";
		ResponseEntity<RestResponse<User>> responseEntity = rest.post(url, user,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		} else {
			throw new IllegalStateException("Can not auth user");
		}
	}

	public User getUserByToken(String token) {
		String url = "http://" + userServiceName + "/user/get?token=" + token;
		ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if (response == null || response.getCode() != 0) {
			return null;
		}
		return response.getResult();
	}

	public void logout(String token) {
		String url = "http://" + userServiceName + "/user/logout?token=" + token;
		rest.get(url, new ParameterizedTypeReference<RestResponse<Object>>() {
		});
	}

	public List<Agency> getAllAgency() {
		return Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/agency/list");
			ResponseEntity<RestResponse<List<Agency>>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<List<Agency>>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public User updateUser(User user) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/user/update");
			ResponseEntity<RestResponse<User>> responseEntity = rest.post(url, user,
					new ParameterizedTypeReference<RestResponse<User>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public User getAgentById(Long id) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/agency/agentDetail?id=" + id);
			ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<User>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public Agency getAgencyById(Integer id) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/agency/agencyDetail?id=" + id);
			ResponseEntity<RestResponse<Agency>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<Agency>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public void addAgency(Agency agency) {
		Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/agency/add");
			ResponseEntity<RestResponse<Object>> responseEntity = rest.post(url, agency,
					new ParameterizedTypeReference<RestResponse<Object>>() {
					});
			return responseEntity.getBody();
		});
	}

	public ListResponse<User> getAgentList(Integer limit, Integer offset) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/agency/agentList?limit=" + limit + "&offset=" + offset);
			ResponseEntity<RestResponse<ListResponse<User>>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<ListResponse<User>>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public String getEmail(String key) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/user/getKeyEmail?key=" + key);
			ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<String>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public User reset(String key, String password) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(userServiceName, "/user/reset?key=" + key + "&password=" + password);
			ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<User>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public void resetNotify(String email, String url) {

			String sendUrl = Rests.toUrl(userServiceName, "/user/resetNotify?email=" + email + "&url=" + url);
			rest.get(sendUrl, new ParameterizedTypeReference<RestResponse<Object>>() {
			});

	}

}
