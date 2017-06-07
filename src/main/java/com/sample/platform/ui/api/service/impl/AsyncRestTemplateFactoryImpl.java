package com.sample.platform.ui.api.service.impl;

import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.collect.Lists;
import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.service.AsyncRestTemplateFactory;
import com.sample.platform.ui.api.service.HeaderBuilder;
import com.sample.platform.ui.api.web.client.CustomStringHttpMessageConverter;
import com.sample.platform.ui.api.web.client.MDCAwareAsyncClientHttpRequestFactoryDecorator;
import com.sample.platform.ui.api.web.client.ShopApiAsyncRestTemplate;

@Service
public class AsyncRestTemplateFactoryImpl implements AsyncRestTemplateFactory {
	@Autowired
	private HeaderBuilder headerBuilder;

	private CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClientBuilder.create().useSystemProperties().build();

	@PreDestroy
	public void destroy() throws Exception {
		this.httpAsyncClient.close();
	}

	@Override
	public AsyncRestTemplate createShopAPIAsyncRestTemplate(final int timeout) {
		String traceId = (String) RequestContextHolder.currentRequestAttributes().getAttribute(ShopApiConstants.TRACE_HEADER, RequestAttributes.SCOPE_REQUEST);
		ShopApiAsyncRestTemplate asyncRestTemplate = new ShopApiAsyncRestTemplate(this.headerBuilder, traceId);
		asyncRestTemplate.setAsyncRequestFactory(this.createAsyncRequestFactory(traceId, timeout));
		asyncRestTemplate.setMessageConverters(this.createDefaultMessageConverterList());
		return asyncRestTemplate;
	}

	private AsyncClientHttpRequestFactory createAsyncRequestFactory(final String traceId, final int timeout) {
		HttpComponentsAsyncClientHttpRequestFactory requestFactory = new HttpComponentsAsyncClientHttpRequestFactory();
		requestFactory.setHttpAsyncClient(this.httpAsyncClient);
		requestFactory.setConnectTimeout(timeout);
		requestFactory.setReadTimeout(timeout);
		return new MDCAwareAsyncClientHttpRequestFactoryDecorator(traceId, requestFactory);
	}

	/**
	 * The default list was taken from RestTemplate.RestTemplate()
	 */
	protected List<HttpMessageConverter<?>> createDefaultMessageConverterList() {
		return Lists.newArrayList(new ByteArrayHttpMessageConverter(), new CustomStringHttpMessageConverter(),
				new ResourceHttpMessageConverter(), new SourceHttpMessageConverter<>(),
				new AllEncompassingFormHttpMessageConverter(), new Jaxb2RootElementHttpMessageConverter(),
				new MappingJackson2HttpMessageConverter());
	}
}
