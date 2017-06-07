package com.sample.platform.ui.api.web.client;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestFactory;

/**
 * Creates async request instances decorated with a trace id.  
 */
public class MDCAwareAsyncClientHttpRequestFactoryDecorator implements AsyncClientHttpRequestFactory {
	private final String traceId;
	private final AsyncClientHttpRequestFactory delegate;

	public MDCAwareAsyncClientHttpRequestFactoryDecorator(final String traceId, final AsyncClientHttpRequestFactory delegate) {
		this.traceId = traceId;
		this.delegate = delegate;
	}

	@Override
	public AsyncClientHttpRequest createAsyncRequest(final URI uri, final HttpMethod httpMethod) throws IOException {
		return new MDCAwareAsyncClientHttpRequestDecorator(this.traceId, this.delegate.createAsyncRequest(uri, httpMethod));
	}
}