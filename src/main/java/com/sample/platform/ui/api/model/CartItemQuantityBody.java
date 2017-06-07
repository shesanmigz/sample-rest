package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemQuantityBody {
	@JsonProperty("quantity")
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CartItemQuantityBody(Integer quantity) {
		this.quantity = quantity;
	}

	public CartItemQuantityBody() {
		
	}
}
