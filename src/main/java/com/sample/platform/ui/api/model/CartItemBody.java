package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemBody {
	@JsonProperty("offerId")
	private String offerId;

	@JsonProperty("quantity")
	private Integer quantity;

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CartItemBody(String offerId, Integer quantity) {
		this.offerId = offerId;
		this.quantity = quantity;
	}

	public CartItemBody() {
		
	}
}
