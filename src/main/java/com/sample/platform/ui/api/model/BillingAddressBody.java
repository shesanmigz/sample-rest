package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BillingAddressBody {
	@JsonProperty("id")
	private String id;

	public BillingAddressBody(String id) {
		this.id = id;
	}

	public BillingAddressBody() {
		
	}
}
