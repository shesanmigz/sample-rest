package com.sample.platform.ui.api.tokens;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.sample.platform.ui.api.tokens.Token;
import com.sample.platform.ui.api.tokens.TokenCleaningCacheManager;

public class TokenCleaningCacheManagerTest {
	private TokenCleaningCacheManager tcm;
	private ConcurrentHashMap<String, Token> cache;
	
	@Before
	public void setUp() {
		this.cache = new ConcurrentHashMap<>();
		this.tcm = new TokenCleaningCacheManager("test", cache, 100, 1000);
	}
	
	@After
	public void setDown() throws Exception {
		this.tcm.destroy();
	}
	
	@Test
	public void testPurge() throws InterruptedException {
		Token token1 = new Token("token1", ImmutableMap.of("exp", new Date().getTime() / 1000 + 1));
		Token token2 = new Token("token2", ImmutableMap.of("exp", new Date().getTime() / 1000 + 5));
		
		cache.put("token1", token1);
		cache.put("token2", token2);
		
		long start = new Date().getTime();
		// 2 sec is enough time for purge to be triggered several times and one
		// of the calls should purge the expired token.
		// Theoretically it should take not more than 1100 milliseconds: ttl +
		// purge period
		while (cache.size() > 1 && new Date().getTime() - start < 2000) {
			Thread.sleep(100);
		}
		
		Assert.assertEquals(ImmutableMap.of("token2", token2), cache);
	}
}
