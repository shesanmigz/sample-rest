package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentMethodBody {
	@JsonProperty("id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PaymentMethodBody(String id) {
		this.id = id;
	}

	public PaymentMethodBody() {
		
	}
}
