package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WishlistItemBody {
	@JsonProperty("offerId")
	private String offerId;

	public WishlistItemBody(String offerId) {
		this.offerId = offerId;
	}

	public WishlistItemBody() {
		
	}
}