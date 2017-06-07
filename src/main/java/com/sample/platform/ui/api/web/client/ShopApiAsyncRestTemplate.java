package com.sample.platform.ui.api.web.client;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.CompletableToListenableFutureAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRequestCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import com.sample.platform.ui.api.service.HeaderBuilder;

/**
 * As the {@link DefaultAsyncRestTemplate} uses the custom string message
 * converter.
 *
 * In addition it also ensures that only allowed headers with valid values are
 * sent to end-points and adds the appropriate authorization header.
 *
 * @see DefaultAsyncRestTemplate
 */
public class ShopApiAsyncRestTemplate extends AsyncRestTemplate {
	private final String traceId;
	private final HeaderBuilder headerBuilder;
	
	public ShopApiAsyncRestTemplate(final HeaderBuilder headerBuilder, final String traceId) {
		this.headerBuilder = headerBuilder;
		this.traceId = traceId;
	}
	
	@Override
	protected <T> AsyncRequestCallback httpEntityCallback(final HttpEntity<T> requestBody) {
		return super.httpEntityCallback(this.buildHeaders(requestBody));
	}
	
	@Override
	protected <T> AsyncRequestCallback httpEntityCallback(final HttpEntity<T> request, final Type responseType) {
		return super.httpEntityCallback(this.buildHeaders(request), responseType);
	}
	
	@Override
	protected <T> ListenableFuture<T> doExecute(final URI url, final HttpMethod method,
			final AsyncRequestCallback requestCallback, final ResponseExtractor<T> responseExtractor)
			throws RestClientException {
		
		CompletableFuture<T> completableFuture = new CompletableFuture<>();
		ListenableFuture<T> realFuture = super.doExecute(url, method, new LoggingAsyncRequestCallbackDecorator(requestCallback), new LoggingResponseExtractorDecorator<T>(method, url, responseExtractor));
		realFuture.addCallback(new LoggingListenableFutureCallbackDecorator<T>(url, method, completableFuture));
		return new CompletableToListenableFutureAdapter<>(completableFuture);
	}
	
	<T> HttpEntity<T> buildHeaders(final HttpEntity<T> request) {
		if (request instanceof ResponseEntity) {
			throw new IllegalStateException(
					"This may not ever happen since we use the method only when we create AsyncRequestCallback, no ResponseEntity is posible here");
		}
		
		HttpEntity<T> result = null;
		
		if (request != null) {
			if (request instanceof RequestEntity) {
				RequestEntity<T> requestEntity = (RequestEntity<T>) request;
				result = new RequestEntity<>(request.getBody(), this.headerBuilder.build(request.getHeaders(), this.traceId),
						requestEntity.getMethod(), requestEntity.getUrl(), requestEntity.getType());
			} else {
				result = new HttpEntity<>(request.getBody(), this.headerBuilder.build(request.getHeaders(), this.traceId));
			}
		}
		
		return result;
	}
}
