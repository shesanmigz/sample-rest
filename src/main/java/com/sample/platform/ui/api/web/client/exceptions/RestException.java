package com.sample.platform.ui.api.web.client.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Subclasses should be annotated with @ResponseStatus,
 * it will be used to extract the HTTP status code.
 */
public abstract class RestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RestException() {
	}

	public RestException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RestException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RestException(final String message) {
		super(message);
	}

	public RestException(final Throwable cause) {
		super(cause);
	}
	
	public int status() {
		ResponseStatus annotation = this.getClass().getDeclaredAnnotation(ResponseStatus.class);
;		return annotation.value().value();
	}
}
