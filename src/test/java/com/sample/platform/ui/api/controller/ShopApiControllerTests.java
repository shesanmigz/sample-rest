package com.sample.platform.ui.api.controller;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

public abstract class ShopApiControllerTests extends WithMocks {
	public static final String DOMAIN_HEADER_VALUE = "www.xsampledomain-header.com";
	public static final String LANGUAGE_HEADER_VALUE = "de_de";
	public static final String TRACE_HEADER_VALUE = "dcd82870-7b67-4c02-a282-6c711c9b3937";
	public static final String SESSION_HEADER_VALUE = "86859d85-2009-4b9f-98d8-060775bc3e3b";
	public static final String CHECKOUT_HEADER_VALUE = "86859d85-2010-4b9f-98d8-060775bc3e3b";

	@Configuration
	static class Config {
	}

	@Autowired
	protected WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = this.createMockMVC(this.wac);
	}
	
	@After
	public void setDown() {
		this.stopMockServer();
	}
}
