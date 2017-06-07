package com.sample.platform.ui.api.web.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.web.client.AsyncRequestCallback;

/**
 * Wraps a request call back, delegates all calls to it, but also logs any HTTP
 * requests made.
 */
public class LoggingAsyncRequestCallbackDecorator implements AsyncRequestCallback {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAsyncRequestCallbackDecorator.class);

	private final AsyncRequestCallback requestCallback;

	public LoggingAsyncRequestCallbackDecorator(final AsyncRequestCallback requestCallback) {
		this.requestCallback = requestCallback;
	}

	@Override
	public void doWithRequest(final AsyncClientHttpRequest request) throws IOException {
		LOGGER.trace("HTTP request: {} {}", request.getMethod(), request.getURI());

		this.requestCallback.doWithRequest(request);
	}
}