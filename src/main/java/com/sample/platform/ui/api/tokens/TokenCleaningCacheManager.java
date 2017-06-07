package com.sample.platform.ui.api.tokens;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Monitors a token cache. Runs a scheduled thread and if it finds an expired
 * token the token gets purged from the provided cache.
 */
public class TokenCleaningCacheManager extends CacheManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenCleaningCacheManager.class);
	
	private final TokenUtils tokenUtils = new TokenUtils();
	
	private final ConcurrentHashMap<String, Token> cache;
	
	public TokenCleaningCacheManager(final String name, final ConcurrentHashMap<String, Token> cache,
			final long cleanupPeriod, final long shutdownTimeout) {
		super(name, cleanupPeriod, shutdownTimeout);
		
		this.cache = cache;
	}
	
	public void task() {
		LOGGER.debug("Purge has started");
		
		int remain = 0;
		int removed = 0;
		/*
		 * The iterator may walk through an obsolete snapshot So, it's possible that
		 * while we are validating some keys a new key is added and this key becomes
		 * invalid very fast, so it should be removed from the cache. We will miss
		 * such keys, but this is not a problem, since the key will be removed
		 * either the next time or when the key is taken from the cache for usage,
		 * in the latter case the caller has to check if the token is not expired.
		 */
		Iterator<Entry<String, Token>> it = cache.entrySet().iterator();
		while (it.hasNext()) {
			if (Thread.interrupted()) {
				break;
			}
			
			Entry<String, Token> entry = it.next();
			if (this.tokenUtils.verifyDates(entry.getValue())) {
				remain += 1;
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace(MessageFormat.format("{0} will expire at {1,date,yyyy-MM-dd HH:mm:ss.SSSZ}", this.tokenUtils.safeToken(entry.getKey()),
							Optional.ofNullable(entry.getValue().getExpiresAt()).map(d -> d * 1000).orElse(null)));
				}
			} else {
				removed += 1;
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace(MessageFormat.format("{0} will expire at {1,date,yyyy-MM-dd HH:mm:ss.SSSZ}", this.tokenUtils.safeToken(entry.getKey()),
							Optional.ofNullable(entry.getValue().getExpiresAt()).map(d -> d * 1000).orElse(null)));
				}
				it.remove();
			}
		}
		
		LOGGER.debug("Purge has finished, tokens remain: {}, tokens removed: {}", remain, removed);
	}
	
}
