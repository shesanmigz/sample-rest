package com.sample.platform.ui.api.security;

import java.util.List;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.Application;
import com.sample.platform.ui.api.controller.WithMocks;
import com.sample.platform.ui.api.model.Country;
import com.sample.platform.ui.api.service.TokenSource;

/**
 * The goal of the test is to see if we can call services only if valid tokens
 * are provided. This test suite runs in a test profile which enables the
 * security by default. We need to use a dedicated profile since development and
 * test profiles disable the security by default: for other tests there is no
 * need to set up a secured environment.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@ContextConfiguration
@ActiveProfiles("security-test")
@ConfigurationProperties(prefix = "auth")
@EnableConfigurationProperties
public class SecurityTests extends WithMocks {
	private static final String TEST_URL = "/api/v1/localization/countries";
	private static final String HEALTH_URL = "/health";
	
	private static final List<Country> RES_COUNTRIES = ImmutableList.of(new Country("Germany", "DE", "DEU", new Integer(276), true),
			new Country("Czechia", "CZ", "CZE", new Integer(203), false),
			new Country("Denmark", "DK", "DNK", new Integer(208), false));
	
	@Value("${auth.audience-name}")
	private String audienceName;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private TokenSource ts;
	
	private String token;
	private String expiredToken;
	private String badAudienceToken;
	
	private final SecurityTestUtils tu = new SecurityTestUtils();
	
	@Before
	public void setUp() throws Exception {
		this.token = this.tu.genToken(1000 * 60 * 60 * 24, this.audienceName);
		this.expiredToken = this.tu.genToken(-5001, this.audienceName);
		this.badAudienceToken = this.tu.genToken(1000 * 60 * 60 * 24, "a");
		
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
		this.syncTest()
				.withGET(SecurityTests.TEST_URL)
				.expectStatus(HttpStatus.UNAUTHORIZED)
				.expectPOJO(new Error("unauthorized", "Full authentication is required to access this resource"))
				.runWithServer();
	}
	
	@Test
	public void testInvalidHeader() throws Exception {
		this.syncTest()
				.withGET(SecurityTests.TEST_URL)
				.withHeader(HttpHeaders.AUTHORIZATION, this.token)
				.expectStatus(HttpStatus.UNAUTHORIZED)
				.expectPOJO(new Error("unauthorized", "Full authentication is required to access this resource"))
				.runWithServer();
	}
	
	@Test
	public void testInvalidToken() throws Exception {
		this.syncTest()
				.withGET(SecurityTests.TEST_URL)
				.withHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.token + "a")
				.expectStatus(HttpStatus.UNAUTHORIZED)
				.expectPOJO(new Error("invalid_token", "Cannot convert access token to JSON"))
				.runWithServer();
	}
	
	@Test
	public void testBadAudience() throws Exception {
		this.syncTest()
				.withGET(SecurityTests.TEST_URL)
				.withHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.badAudienceToken)
				.expectStatus(HttpStatus.FORBIDDEN)
				.expectPOJO(new Error("access_denied", "Invalid token does not contain resource id (" + this.audienceName + ")"))
				.runWithServer();
	}
	
	@Test
	public void testExpiredToken() throws Exception {
		this.syncTest()
				.withGET(SecurityTests.TEST_URL)
				.withHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.expiredToken)
				.expectStatus(HttpStatus.UNAUTHORIZED)
				.expectPOJO(new Error("invalid_token", "Access token expired: " + this.expiredToken))
				.runWithServer();
	}
	
	@Test
	public void testValidToken() throws Exception {
		Assert.assertEquals(1, ts.getTokens().size());
		
		ServerCall sc = this.stub()
				.withGET("/v1/countries")
				.withPOJO(RES_COUNTRIES)
				.build();
		
		this.test()
				.withGET(SecurityTests.TEST_URL)
				.withHeader("Authorization", "Bearer " + this.token)
				.expectCollection(RES_COUNTRIES, Country.class)
				.expectServerCall(sc)
				.expectServerHeader(HttpHeaders.AUTHORIZATION, "Bearer " + ts.getTokens().iterator().next())
				.runWithServer();
	}
	
	@Test
	public void testHealthNoAuthHeader() throws Exception {
		this.syncTest()
				.withGET(SecurityTests.HEALTH_URL)
				.expectHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
				.expectEmptyBody()
				.runWithServer();
	}
	
	@Test
	public void testHealthInvalidHeader() throws Exception {
		this.syncTest()
				.withGET(SecurityTests.HEALTH_URL)
				.withHeader(HttpHeaders.AUTHORIZATION, this.token)
				.expectHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
				.expectEmptyBody()
				.runWithServer();
	}
	
	@Test
	public void testHealthValidToken() throws Exception {
		this.syncTest()
				.withGET(SecurityTests.HEALTH_URL)
				.withHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.token)
				.expectHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
				.expectEmptyBody()
				.runWithServer();
	}
}