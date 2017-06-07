package com.sample.platform.ui.api.tokens;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sample.platform.ui.api.service.TokenSource;
import com.sample.platform.ui.api.tokens.PublicKeyUpdatingCacheManager;
import com.sample.platform.ui.api.tokens.Token;
import com.sample.platform.ui.api.tokens.TokenUpdatingCacheManager;

public class TokenUpdatingCacheManagerTest {
	private final ObjectMapper om = new ObjectMapper();
	
	private TokenUpdatingCacheManager cm;
	private ConcurrentHashMap<String, Token> cache;
	private TokenSource ts;
	private PublicKeyUpdatingCacheManager pk;
	
	@Before
	public void setUp() {
		this.ts = Mockito.mock(TokenSource.class);
		this.pk = Mockito.mock(PublicKeyUpdatingCacheManager.class);
		this.cache = new ConcurrentHashMap<>();
		this.cm = Mockito.spy(new TokenUpdatingCacheManager("test", this.cache, this.ts, this.pk, 1000 * 60 * 60 * 60 * 24, 1000));
	}
	
	@After
	public void setDown() throws Exception {
		this.cm.destroy();
	}
	
	@Test
	public void testTask() throws Exception {
		Token oldTk = Mockito.mock(Token.class);
		RsaVerifier v = Mockito.mock(RsaVerifier.class);
		
		Mockito.doReturn(new ConcurrentHashMap<>(ImmutableMap.of("v", v))).when(this.pk).getCache();
		
		Map<String, Object> expToken = ImmutableMap.of("exp", (int) Math.floor(new Date().getTime() / 1000.0) - 60 * 60 * 24);
		Jwt expJWT = Mockito.mock(Jwt.class);
		Mockito.doReturn(this.om.writeValueAsString(expToken)).when(expJWT).getClaims();
		
		Map<String, Object> token = ImmutableMap.of("exp", (int) Math.floor(new Date().getTime() / 1000.0) + 60 * 60 * 24);
		Jwt jwt = Mockito.mock(Jwt.class);
		Mockito.doReturn(this.om.writeValueAsString(token)).when(jwt).getClaims();
		
		Mockito.doReturn(ImmutableList.of("oldToken", "invalidToken", "expToken", "token")).when(this.ts).getTokens();
		Mockito.doThrow(InvalidSignatureException.class).when(this.cm).decodeAndVerify("invalidToken", v);
		Mockito.doReturn(expJWT).when(this.cm).decodeAndVerify("expToken", v);
		Mockito.doReturn(jwt).when(this.cm).decodeAndVerify("token", v);
		
		this.cache.put("oldToken", oldTk);
		
		this.cm.task();
		
		Assert.assertEquals(2, this.cache.size());
		Assert.assertSame(oldTk, this.cache.get("oldToken"));
		Assert.assertEquals("token", this.cache.get("token").token);
		Assert.assertEquals(token, this.cache.get("token").json);
	}
}
