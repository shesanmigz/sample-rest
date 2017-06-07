package com.sample.platform.ui.api.security;

import java.util.List;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.Application;
import com.sample.platform.ui.api.controller.WithMocks;
import com.sample.platform.ui.api.model.Country;

/**
 * The goal of the test is to see if we can call services when the security is
 * disabled (which is the case for the test and development profiles by
 * default).
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@ContextConfiguration
@ActiveProfiles("test")
public class NoSecurityTests extends WithMocks {
	private static final String TEST_URL = "/api/v1/localization/countries";
	
	private static final List<Country> RES_COUNTRIES = ImmutableList.of(new Country("Germany", "DE", "DEU", new Integer(276), true),
			new Country("Czechia", "CZ", "CZE", new Integer(203), false),
			new Country("Denmark", "DK", "DNK", new Integer(208), false));
	
	private String token;
	
	private final SecurityTestUtils tu = new SecurityTestUtils();
	
	@Value("${auth.audience-name}")
	private String audienceName;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Configuration
	static class Config {
	}
	
	@Before
	public void setUp() throws Exception {
		this.token = this.tu.genToken(1000 * 60 * 60 * 24, this.audienceName);
		
		Filter filter = this.wac.getBean(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, Filter.class);
		this.mockMvc = this.createMockMVC(this.wac, filter);
		
		this.startMockServer();
	}
	
	@After
	public void setDown() throws Exception {
		this.stopMockServer();
	}

	@Test
	public void testNoAuthHeader() throws Exception {
		this.stub()
				.withGET("/v1/countries")
				.withPOJO(RES_COUNTRIES)
				.build();
		
		this.test()
				.withGET(NoSecurityTests.TEST_URL)
				.expectCollection(RES_COUNTRIES, Country.class)
				.runWithServer();
	}
	
	@Test
	public void testInvalidHeader() throws Exception {
		this.stub()
				.withGET("/v1/countries")
				.withPOJO(RES_COUNTRIES)
				.build();
		
		this.test()
				.withGET(NoSecurityTests.TEST_URL)
				.withHeader("Authorization", this.token)
				.expectCollection(RES_COUNTRIES, Country.class)
				.runWithServer();
	}
	
	@Test
	public void testValidToken() throws Exception {
		this.stub()
				.withGET("/v1/countries")
				.withPOJO(RES_COUNTRIES)
				.build();
		
		this.test()
				.withGET(NoSecurityTests.TEST_URL)
				.withHeader("Authorization", "Bearer " + this.token)
				.expectCollection(RES_COUNTRIES, Country.class)
				.runWithServer();
	}
}