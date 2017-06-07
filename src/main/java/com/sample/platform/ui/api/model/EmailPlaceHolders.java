package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailPlaceHolders {
	@JsonProperty("key")
	private String key;

	@JsonProperty("value")
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EmailPlaceHolders(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public EmailPlaceHolders() {
		
	}
}
