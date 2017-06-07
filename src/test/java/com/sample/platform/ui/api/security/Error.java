package com.sample.platform.ui.api.security;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
	public final String error;
	public final String description;
	
	public Error(@JsonProperty("error") final String error,
			@JsonProperty("error_description") final String description) {
		this.error = error;
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.error == null) ? 0 : this.error.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Error other = (Error) obj;
		if (this.error == null) {
			if (other.error != null) {
				return false;
			}
		} else if (!this.error.equals(other.error)) {
			return false;
		}
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!this.description.equals(other.description)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return MessageFormat.format("'{'error: {0}, description: {1}'}'", this.error, this.description);
	}
}
