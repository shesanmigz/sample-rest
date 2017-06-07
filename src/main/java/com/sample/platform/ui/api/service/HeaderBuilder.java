package com.sample.platform.ui.api.service;

import org.springframework.http.HttpHeaders;

/**
 * Creates sets of headers
 */
public interface HeaderBuilder {
	/**
	 * Builds headers using a provided list. Only allowed and valid headers will
	 * be added. The resulting headers will also contain an authorization header
	 * and a trace id header.
	 */
	HttpHeaders build(HttpHeaders httpHeaders, String traceId);
}
