package com.sample.platform.ui.api.controller;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CartControllerServerTests extends CartControllerTests {
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		this.startMockServer();
	}
}