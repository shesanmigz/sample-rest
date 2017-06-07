package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailSuccess {
	@JsonProperty("emailIds")
	private List<String> emailIds;

	public List<String> getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(List<String> emailIds) {
		this.emailIds = emailIds;
	}

	public EmailSuccess(List<String> emailIds) {
		this.emailIds = emailIds;
	}

	public EmailSuccess() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailIds == null) ? 0 : emailIds.hashCode());
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
		EmailSuccess other = (EmailSuccess) obj;
		if (emailIds == null) {
			if (other.emailIds != null)
				return false;
		} else if (!emailIds.equals(other.emailIds))
			return false;
		return true;
	}
}
