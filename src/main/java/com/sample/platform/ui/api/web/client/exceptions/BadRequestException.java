package com.sample.platform.ui.api.web.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RestException {
	private static final long serialVersionUID = 1L;
	
	public BadRequestException() {
	}

	public BadRequestException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BadRequestException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(final String message) {
		super(message);
	}

	public BadRequestException(final Throwable cause) {
		super(cause);
	}
}
