package com.sample.platform.ui.api.service;

import org.springframework.web.client.AsyncRestTemplate;

/**
 * Creates different variants of async rest templates.
 */
public interface AsyncRestTemplateFactory {
	/**
	 * This one sets utf-8 as the Accept-Charset and passes only allowed headers.
	 */
	AsyncRestTemplate createShopAPIAsyncRestTemplate(int timeout);
}
