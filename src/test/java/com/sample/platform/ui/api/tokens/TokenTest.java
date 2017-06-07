package com.sample.platform.ui.api.tokens;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sample.platform.ui.api.tokens.Token;

public class TokenTest {
	@Test
	public void testGetExpiresAt() {
		Assert.assertEquals(1, (long) new Token("token", ImmutableMap.of("exp", (byte) 1)).getExpiresAt());
		Assert.assertEquals(2, (long) new Token("token", ImmutableMap.of("exp", (short) 2)).getExpiresAt());
		Assert.assertEquals(3, (long) new Token("token", ImmutableMap.of("exp", (int) 3)).getExpiresAt());
		Assert.assertEquals(4, (long) new Token("token", ImmutableMap.of("exp", (long) 4)).getExpiresAt());
		Assert.assertEquals(5, (long) new Token("token", ImmutableMap.of("exp", 5f)).getExpiresAt());
		Assert.assertEquals(6, (long) new Token("token", ImmutableMap.of("exp", 6.0)).getExpiresAt());
		Assert.assertEquals(0, (long) new Token("token", ImmutableMap.of("exp", new Date(7))).getExpiresAt());
		Assert.assertEquals(1, (long) new Token("token", ImmutableMap.of("exp", new Date(1000))).getExpiresAt());
	}
	
	@Test
	public void testGetIssuedAt() {
		Assert.assertEquals(1, (long) new Token("token", ImmutableMap.of("iat", (byte) 1)).getIssuedAt());
		Assert.assertEquals(2, (long) new Token("token", ImmutableMap.of("iat", (short) 2)).getIssuedAt());
		Assert.assertEquals(3, (long) new Token("token", ImmutableMap.of("iat", (int) 3)).getIssuedAt());
		Assert.assertEquals(4, (long) new Token("token", ImmutableMap.of("iat", (long) 4)).getIssuedAt());
		Assert.assertEquals(5, (long) new Token("token", ImmutableMap.of("iat", 5f)).getIssuedAt());
		Assert.assertEquals(6, (long) new Token("token", ImmutableMap.of("iat", 6.0)).getIssuedAt());
		Assert.assertEquals(0, (long) new Token("token", ImmutableMap.of("iat", new Date(7))).getIssuedAt());
		Assert.assertEquals(1, (long) new Token("token", ImmutableMap.of("iat", new Date(1000))).getIssuedAt());
	}
	
	@Test
	public void testGetNotBefore() {
		Assert.assertEquals(1, (long) new Token("token", ImmutableMap.of("nbf", (byte) 1)).getNotBefore());
		Assert.assertEquals(2, (long) new Token("token", ImmutableMap.of("nbf", (short) 2)).getNotBefore());
		Assert.assertEquals(3, (long) new Token("token", ImmutableMap.of("nbf", (int) 3)).getNotBefore());
		Assert.assertEquals(4, (long) new Token("token", ImmutableMap.of("nbf", (long) 4)).getNotBefore());
		Assert.assertEquals(5, (long) new Token("token", ImmutableMap.of("nbf", 5f)).getNotBefore());
		Assert.assertEquals(6, (long) new Token("token", ImmutableMap.of("nbf", 6.0)).getNotBefore());
		Assert.assertEquals(0, (long) new Token("token", ImmutableMap.of("nbf", new Date(7))).getNotBefore());
		Assert.assertEquals(1, (long) new Token("token", ImmutableMap.of("nbf", new Date(1000))).getNotBefore());
	}
	
	@Test
	public void testGetAudience() {
		Assert.assertEquals(ImmutableList.of("aud1"), new Token("token", ImmutableMap.of("aud", "aud1")).getAudience());
		Assert.assertEquals(ImmutableList.of("aud1"),
				new Token("token", ImmutableMap.of("aud", ImmutableList.of("aud1"))).getAudience());
		Assert.assertEquals(ImmutableList.of("aud1", "aud2"),
				new Token("token", ImmutableMap.of("aud", ImmutableList.of("aud1", "aud2"))).getAudience());
		Assert.assertEquals(ImmutableList.of("aud1"),
				new Token("token", ImmutableMap.of("aud", new String[] { "aud1" })).getAudience());
		Assert.assertEquals(ImmutableList.of("aud1", "aud2"),
				new Token("token", ImmutableMap.of("aud", new String[] { "aud1", "aud2" })).getAudience());
	}
}
