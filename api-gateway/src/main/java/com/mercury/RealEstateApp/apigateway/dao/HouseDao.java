package com.mercury.RealEstateApp.apigateway.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.mercury.RealEstateApp.apigateway.common.HouseUserType;
import com.mercury.RealEstateApp.apigateway.common.RestResponse;
import com.mercury.RealEstateApp.apigateway.config.GenericRest;
import com.mercury.RealEstateApp.apigateway.model.City;
import com.mercury.RealEstateApp.apigateway.model.Community;
import com.mercury.RealEstateApp.apigateway.model.House;
import com.mercury.RealEstateApp.apigateway.model.HouseQueryReq;
import com.mercury.RealEstateApp.apigateway.model.HouseUserReq;
import com.mercury.RealEstateApp.apigateway.model.ListResponse;
import com.mercury.RealEstateApp.apigateway.model.User;
import com.mercury.RealEstateApp.apigateway.model.UserMsg;
import com.mercury.RealEstateApp.apigateway.utils.Rests;

@Repository
public class HouseDao {

	@Autowired
	private GenericRest rest;

	@Value("${house.service.name}")
	private String houseServiceName;

	public List<City> getAllCitys() {
		RestResponse<List<City>> resp = Rests.exc(() -> {
			String url = Rests.toUrl(houseServiceName, "/house/allCitys");
			ResponseEntity<RestResponse<List<City>>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<List<City>>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	public List<Community> getAllCommunitys() {
		RestResponse<List<Community>> resp = Rests.exc(() -> {
			String url = Rests.toUrl(houseServiceName, "/house/allCommunitys");
			ResponseEntity<RestResponse<List<Community>>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<List<Community>>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	public void addHouse(House house) {

		String url = Rests.toUrl(houseServiceName, "/house/add");
		rest.post(url, house, new ParameterizedTypeReference<RestResponse<Object>>() {
		});

		// Rests.exc(() -> {
		// String url = Rests.toUrl(houseServiceName, "/house/add");
		// ResponseEntity<RestResponse<Object>> responseEntity = rest.post(url, house,
		// new ParameterizedTypeReference<RestResponse<Object>>() {
		// });
		// return responseEntity.getBody();
		// });

	}
	
	public void updateHouse(House updateHouse, User user, Long id) {

		String url = Rests.toUrl(houseServiceName, "/house/edit?id=" + id);
		rest.post(url, updateHouse, new ParameterizedTypeReference<RestResponse<Object>>() {
		});

		// Rests.exc(() -> {
		// String url = Rests.toUrl(houseServiceName, "/house/add");
		// ResponseEntity<RestResponse<Object>> responseEntity = rest.post(url, house,
		// new ParameterizedTypeReference<RestResponse<Object>>() {
		// });
		// return responseEntity.getBody();
		// });

	}

	public void rating(Long id, Double rating) {
		Rests.exc(() -> {
			String url = Rests.toUrl(houseServiceName, "/house/rating?id=" + id + "&rating=" + rating);
			ResponseEntity<RestResponse<Object>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<Object>>() {
					});
			return responseEntity.getBody();
		});
	}

	public void addUserMsg(UserMsg userMsg) {
		Rests.exc(() -> {
			String url = Rests.toUrl(houseServiceName, "/house/addUserMsg");
			ResponseEntity<RestResponse<Object>> responseEntity = rest.post(url, userMsg,
					new ParameterizedTypeReference<RestResponse<Object>>() {
					});
			return responseEntity.getBody();
		});
	}

	public List<House> getLastest() {
		RestResponse<List<House>> resp = Rests.exc(() -> {

			String url = Rests.toUrl(houseServiceName, "/house/lastest");
			ResponseEntity<RestResponse<List<House>>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<List<House>>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	public ListResponse<House> getHouses(House query, Integer limit, Integer offset) {
		RestResponse<ListResponse<House>> resp = Rests.exc(() -> {
			HouseQueryReq req = new HouseQueryReq();
			req.setLimit(limit);
			req.setOffset(offset);
			req.setQuery(query);
			String url = Rests.toUrl(houseServiceName, "/house/list");
			ResponseEntity<RestResponse<ListResponse<House>>> responseEntity = rest.post(url, req,
					new ParameterizedTypeReference<RestResponse<ListResponse<House>>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	public List<House> getHotHouse(Integer recomSize) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(houseServiceName, "/house/hot" + "?size=" + recomSize);
			ResponseEntity<RestResponse<List<House>>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<List<House>>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public House getOneHouse(long id) {
		return Rests.exc(() -> {
			String url = Rests.toUrl(houseServiceName, "/house/detail?id=" + id);
			ResponseEntity<RestResponse<House>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<House>>() {
					});
			return responseEntity.getBody();
		}).getResult();
	}

	public void bindUser2House(Long houseId, Long userId, boolean bookmark) {
		HouseUserReq req = new HouseUserReq();
		req.setUnBind(false);
		req.setBindType(HouseUserType.BOOKMARK.value);
		req.setUserId(userId);
		req.setHouseId(houseId);
		bindOrInBind(req);
	}

	private void bindOrInBind(HouseUserReq req) {
		Rests.exc(() -> {
			String url = Rests.toUrl(houseServiceName, "/house/bind");
			ResponseEntity<RestResponse<Object>> responseEntity = rest.post(url, req,
					new ParameterizedTypeReference<RestResponse<Object>>() {
					});
			return responseEntity.getBody();
		});
	}

	public void unbindUser2House(Long houseId, Long userId, boolean book) {
		HouseUserReq req = new HouseUserReq();
		req.setUnBind(true);
		if (book) {
			req.setBindType(HouseUserType.BOOKMARK.value);
		} else {
			req.setBindType(HouseUserType.SALE.value);
		}
		req.setUserId(userId);
		req.setHouseId(houseId);
		bindOrInBind(req);

	}

}
