package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuyBodyUrl {
	@JsonProperty("success")
	private String success;

	@JsonProperty("failure")
	private String failure;

	@JsonProperty("abort")
	private String abort;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public String getAbort() {
		return abort;
	}

	public void setAbort(String abort) {
		this.abort = abort;
	}

	public BuyBodyUrl(String success, String failure, String abort) {
		this.success = success;
		this.failure = failure;
		this.abort = abort;
	}

	public BuyBodyUrl() {
		
	}
}
