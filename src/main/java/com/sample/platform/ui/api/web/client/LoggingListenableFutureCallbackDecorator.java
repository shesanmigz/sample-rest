package com.sample.platform.ui.api.web.client;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Wraps a callback and delegates all calls to it,
 * but also logs failed requests.
 */
public class LoggingListenableFutureCallbackDecorator<T> implements ListenableFutureCallback<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingListenableFutureCallbackDecorator.class);

	private final URI url;
	private final HttpMethod method;
	private final CompletableFuture<T> completableFuture;

	public LoggingListenableFutureCallbackDecorator(final URI url, final HttpMethod method, final CompletableFuture<T> completableFuture) {
		this.url = url;
		this.method = method;
		this.completableFuture = completableFuture;
	}

	@Override
	public void onSuccess(final T result) {
		/*
		 * We do not need to log successful HTTP responses. The data we receive here
		 * were converted by the
		 * com.sample.platform.ui.api.web.client.LoggingResponseExtractorDecorator.
		 * extractData(ClientHttpResponse). A response is better to log in that
		 * method since it contains a real HTTP response before all conversions.
		 */
		this.completableFuture.complete(result);
	}

	@Override
	public void onFailure(final Throwable th) {
		/**
		 * We have to log here since either HTTP call hasn't succeeded (network
		 * failure, misconfigurations etc); or a server returned 5xx or 4xx status
		 * codes. In those cases nothing was logged in the
		 * com.sample.platform.ui.api.web.client.LoggingResponseExtractorDecorator.
		 * extractData(ClientHttpResponse)
		 */
		if (th instanceof HttpStatusCodeException) {
			HttpStatusCodeException ex = (HttpStatusCodeException) th;
			LOGGER.trace("Failed to make HTTP request {} {}:\n{}", this.method, this.url, ex.getResponseBodyAsString(), th);
		} else {
			LOGGER.trace("Failed to make HTTP request {} {}", this.method, this.url, th);
		}
		
		this.completableFuture.completeExceptionally(th);
	}
}