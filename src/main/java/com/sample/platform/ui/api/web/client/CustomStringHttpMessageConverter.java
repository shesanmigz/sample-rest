package com.sample.platform.ui.api.web.client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;

import com.google.common.collect.Lists;

/**
 * StringHttpMessageConverter is responsible for sending Accept-Charset header.
 * This behaviour can be switched off by setting StringHttpMessageConverter.writeAcceptCharset to false
 * but then we would need to set the header for every request we send.
 * 
 * This class overrides the default collection of supported character sets.
 */
public class CustomStringHttpMessageConverter extends StringHttpMessageConverter {
	public CustomStringHttpMessageConverter() {
		// This actually affects the "charset" field of the Content-Type header when it's not specified explicitly
		super(StandardCharsets.UTF_8);
	}

	@Override
	protected List<Charset> getAcceptedCharsets() {
		return Lists.newArrayList(StandardCharsets.UTF_8);
	}
}
