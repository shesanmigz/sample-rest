package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("redirectUrl")
	private String redirectUrl;

	@JsonProperty("message")
	private String message;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result(Boolean success, String redirectUrl, String message) {
		this.success = success;
		this.redirectUrl = redirectUrl;
		this.message = message;
	}

	public Result() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((redirectUrl == null) ? 0 : redirectUrl.hashCode());
		result = prime * result + ((success == null) ? 0 : success.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (redirectUrl == null) {
			if (other.redirectUrl != null)
				return false;
		} else if (!redirectUrl.equals(other.redirectUrl))
			return false;
		if (success == null) {
			if (other.success != null)
				return false;
		} else if (!success.equals(other.success))
			return false;
		return true;
	}
}