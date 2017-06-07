package com.sample.platform.ui.api.tokens;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sample.platform.ui.api.security.SecurityTestUtils;
import com.sample.platform.ui.api.tokens.CustomTokenConverter;
import com.sample.platform.ui.api.tokens.PublicKeyUpdatingCacheManager;
import com.sample.platform.ui.api.tokens.Token;

public class CustomTokenConverterTest {
	private static final String TEST_AUDIENCE = "test-audience";
	
	private final SecurityTestUtils su = new SecurityTestUtils();
	
	private CustomTokenConverter ctc;
	private PublicKeyUpdatingCacheManager pk;
	
	@Before
	public void setUp() throws Exception {
		this.pk = Mockito.mock(PublicKeyUpdatingCacheManager.class);
		this.ctc = Mockito.spy(new CustomTokenConverter(CustomTokenConverterTest.TEST_AUDIENCE, 100, 1000, false));
		
		Mockito.doReturn(this.pk).when(this.ctc).getPublicKeyUpdatingCacheManager();
		Mockito.doReturn(new ConcurrentHashMap<>(ImmutableMap.of("v", Mockito.mock(RsaVerifier.class)))).when(this.pk).getCache();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void testInvalidToken() throws Exception {
		String token = this.su.genToken(0, "a") + "a";
		
		Mockito.doThrow(InvalidTokenException.class).when(this.ctc).doDecode(token);
		
		this.ctc.decode(token);
	}
	
	@Test
	public void testInvalidAudience() throws Exception {
		Token token = new Token("token", ImmutableMap.of("aud", ImmutableList.of("a")));
		Mockito.doReturn(token).when(this.ctc).doDecode("token");
		
		Assert.assertSame(token.json, this.ctc.decode("token"));
		Assert.assertTrue(this.ctc.getCache().isEmpty());
	}
	
	@Test
	public void testExpiredToken() throws Exception {
		Token token = new Token("token", ImmutableMap.of("aud", ImmutableList.of(CustomTokenConverterTest.TEST_AUDIENCE),
				"exp", (new Date().getTime() / 1000) - 1));
		Mockito.doReturn(token).when(this.ctc).doDecode("token");
		
		Assert.assertSame(token.json, this.ctc.decode("token"));
		Assert.assertEquals(ImmutableMap.of("token", token), this.ctc.getCache());
	}
	
	@Test
	public void testCacheHit() throws Exception {
		Token token = new Token("token", ImmutableMap.of("aud", ImmutableList.of(CustomTokenConverterTest.TEST_AUDIENCE)));
		Mockito.doReturn(token).when(this.ctc).doDecode("token");
		
		Assert.assertSame(token.json, this.ctc.decode("token"));
		Mockito.verify(this.ctc, Mockito.times(1)).doDecode("token");
		
		Assert.assertSame(token.json, this.ctc.decode("token"));
		Mockito.verify(this.ctc, Mockito.times(1)).doDecode("token");
	}
}
