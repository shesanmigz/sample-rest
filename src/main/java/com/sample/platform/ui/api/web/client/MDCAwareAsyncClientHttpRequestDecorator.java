package com.sample.platform.ui.api.web.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Wraps an asynchronous client HTTPrequest and delegates all calls to it. It
 * also decorates a listenable future returned by the delegate with a trace id.
 */
public class MDCAwareAsyncClientHttpRequestDecorator implements AsyncClientHttpRequest {
	private final String traceId;
	private final AsyncClientHttpRequest delegate;
	
	public MDCAwareAsyncClientHttpRequestDecorator(final String traceId, final AsyncClientHttpRequest delegate) {
		this.traceId = traceId;
		this.delegate = delegate;
	}
	
	@Override
	public OutputStream getBody() throws IOException {
		return this.delegate.getBody();
	}
	
	@Override
	public HttpHeaders getHeaders() {
		return this.delegate.getHeaders();
	}
	
	@Override
	public URI getURI() {
		return this.delegate.getURI();
	}
	
	@Override
	public HttpMethod getMethod() {
		return this.delegate.getMethod();
	}
	
	@Override
	public ListenableFuture<ClientHttpResponse> executeAsync() throws IOException {
		return new MDCAwareListenableFutureDecorator<>(this.traceId, this.delegate.executeAsync());
	}
}