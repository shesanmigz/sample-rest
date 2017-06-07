package com.sample.platform.ui.api.web.client;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

/**
 * Wraps a response extractor and delegates all calls to it,
 * but also logs all successful HTTP responses.
 * Errors are logged in the LoggingListenableFutureCallbackDecorator.
 */
public class LoggingResponseExtractorDecorator<T> implements ResponseExtractor<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingResponseExtractorDecorator.class);

	private final HttpMethod method;
	private final URI url;
	private final ResponseExtractor<T> responseExtractor;

	public LoggingResponseExtractorDecorator(final HttpMethod method, final URI url, final ResponseExtractor<T> responseExtractor) {
		this.method = method;
		this.url = url;
		this.responseExtractor = responseExtractor;
	}

	@Override
	public T extractData(final ClientHttpResponse response) throws IOException {
		/*
		 * Here we log a successful HTTP response.
		 */
		LOGGER.trace("HTTP response: {} {} {} {}", this.method, this.url, response.getRawStatusCode(), response.getStatusText());
		return this.responseExtractor == null ? null : this.responseExtractor.extractData(response);
	}
}