package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompleteBody {
	@JsonProperty("url")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CompleteBody(String url) {
		this.url = url;
	}

	public CompleteBody() {
		
	}
}
