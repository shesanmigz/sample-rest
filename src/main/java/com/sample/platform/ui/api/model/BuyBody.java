package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuyBody {
	@JsonProperty("url")
	private BuyBodyUrl url;

	public BuyBodyUrl getUrl() {
		return url;
	}

	public void setUrl(BuyBodyUrl url) {
		this.url = url;
	}

	public BuyBody(BuyBodyUrl url) {
		this.url = url;
	}

	public BuyBody() {
		
	}
}
