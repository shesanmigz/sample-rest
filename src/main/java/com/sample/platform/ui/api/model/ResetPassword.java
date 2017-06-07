package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPassword {
	@JsonProperty("email")
	private String email;

	@JsonProperty("tmpPassword")
	private String tmpPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTmpPassword() {
		return tmpPassword;
	}

	public void setTmpPassword(String tmpPassword) {
		this.tmpPassword = tmpPassword;
	}

	public ResetPassword(String email, String tmpPassword) {
		this.email = email;
		this.tmpPassword = tmpPassword;
	}

	public ResetPassword() {
		
	}
}
