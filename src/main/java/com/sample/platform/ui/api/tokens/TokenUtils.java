package com.sample.platform.ui.api.tokens;

import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.google.common.collect.ImmutableList;

/**
 * Some token utility methods.
 */
public class TokenUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtils.class);
	
	/**
	 * Removes the signature part from the token.
	 */
	public String safeToken(final String token) {
		String result = null;
		if (token != null) {
			String[] parts = token.split("\\.");
			result = parts[0];
			if (parts.length > 1) {
				 result+= "." + parts[1];
			}
		}
		return result;
	}

	/**
	 * Tries to execute the same operation against available verifiers. If any of
	 * the operations succeeds, the value returned by the operation will be
	 * forwarded to the caller. If non of the operation succeeds, then the
	 * exception thrown by the latest operation will be rethrown.
	 */
	public <T> T useBestKey(final String token, final PublicKeyUpdatingCacheManager cacheManager,
			final Function<RsaVerifier, T> attempt) {
		T result = null;
		
		RuntimeException e = null;
		for (final Entry<String, RsaVerifier> entry : cacheManager.getCache().entrySet()) {
			try {
				result = attempt.apply(entry.getValue());
				e = null;
				break;
			} catch (final RuntimeException ex) {
				LOGGER.trace("Key\n" + entry.getKey() + " is not suitable to decode " + token);
				e = ex;
			}
		}
		
		if (e != null) {
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Searches for a not expired token with the latest expiration date.
	 */
	public Token findToken(final Collection<Token> tokens) {
		Token result = null;
		
		for (final Token token : tokens) {
			Long expires = token.getExpiresAt();
			Long notBefore = token.getNotBefore();
			Long issued = token.getIssuedAt();
			long now = (long) Math.floor(new Date().getTime() / 1000.0);
			
			if ((expires == null || expires > now) && (notBefore == null || notBefore <= now)
					&& (issued == null || issued <= now)) {
				
				if (result == null || expires == null || expires > result.getExpiresAt()) {
					result = token;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Verifies if the token was issued before the given moment, can be used at
	 * this moment and in the future, and is not expired.
	 */
	public boolean verifyDates(final Token token) {
		return this.findToken(ImmutableList.of(token)) != null;
	}
}
