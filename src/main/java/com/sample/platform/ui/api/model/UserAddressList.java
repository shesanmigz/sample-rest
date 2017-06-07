package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class UserAddressList {
	private final List<UserAddress> userAddressList;

	@JsonValue
	public List<UserAddress> getUserAddressList() {
		return userAddressList;
	}

	public UserAddressList(List<UserAddress> userAddressList) {
		this.userAddressList = userAddressList;
	}
}
