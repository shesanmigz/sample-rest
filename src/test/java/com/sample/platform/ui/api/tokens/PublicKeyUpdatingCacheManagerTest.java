package com.sample.platform.ui.api.tokens;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sample.platform.ui.api.service.PublicKeyReader;
import com.sample.platform.ui.api.tokens.PublicKeyUpdatingCacheManager;

public class PublicKeyUpdatingCacheManagerTest {
	private PublicKeyUpdatingCacheManager pk;
	private ConcurrentHashMap<String, RsaVerifier> cache;
	
	@Before
	public void setUp() {
		this.cache = new ConcurrentHashMap<String, RsaVerifier>();
		this.pk = Mockito.spy(new PublicKeyUpdatingCacheManager(1000 * 60 * 60 * 24, 1000, false, false));
		
		Mockito.doReturn(this.cache).when(this.pk).getCache();
	}
	
	@Test
	public void testTask() {
		RsaVerifier v = Mockito.mock(RsaVerifier.class);
		RsaVerifier ver = Mockito.mock(RsaVerifier.class);
		this.cache.put("v", v);
		
		PublicKeyReader r = Mockito.mock(PublicKeyReader.class);
		Mockito.doReturn(r).when(this.pk).getReader();
		
		Mockito.doReturn(ImmutableList.of("v", "ver")).when(r).readPrivateKeys();
		Mockito.doReturn(ver).when(this.pk).createVerifier("ver");
		
		this.pk.task();
		
		Assert.assertEquals(ImmutableMap.of("v", v, "ver", ver), this.cache);

		Mockito.verify(this.pk).createVerifier(Mockito.anyString());
	}

	@Test
	public void testTaskShouldNotThrowIfReaderIsNull() {
		RsaVerifier v = Mockito.mock(RsaVerifier.class);
		this.cache.put("v", v);
		
		Mockito.doReturn(null).when(this.pk).getReader();
		
		this.pk.task();
		
		Assert.assertEquals(ImmutableMap.of("v", v), this.cache);
		
		Mockito.verify(this.pk, Mockito.times(0)).createVerifier(Mockito.anyString());
	}
	
	@Test
	public void testCreateVerifier() throws Exception {
		RsaVerifier v = this.pk.createVerifier(IOUtils.toString(new ClassPathResource("api-authorization-test.crt").getInputStream(), StandardCharsets.UTF_8.name()));
		Jwt jwt = JwtHelper.decodeAndVerify("eyJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ1aS1hcGktZ2F0ZXdheSIsInNjb3BlIjpbImxvY2FsaXphdGlvbl9yZWFkIl0sImV4cCI6MjAwNDQ5ODIxNH0.f0CO7r_b4-B01uUF1khLROONquj6venOD55Im7fU9OnAx9Vvu3OyrO06SIEXHdHEXzJAD3Jj7BfPlL-eOkdxPD-l3jOODtc1qpbduK7m7gOH6_9wuWcbwFM4dqrd3ZNJsdAST-Cr7lZwY2dpg_1Eqy0zFOHQTBVFlc9dJquwcns", v);
		Assert.assertEquals("ui-api-gateway", new ObjectMapper().readValue(jwt.getClaims(), Map.class).get("aud"));
	}
}
