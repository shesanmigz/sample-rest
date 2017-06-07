package com.sample.platform.ui.api.tokens;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;

import com.sample.platform.ui.api.service.TokenSource;

/**
 * This manager reads tokens periodically from a token source and updates the
 * cache. The provided cache should be thread safe.
 */
public class TokenUpdatingCacheManager extends CacheManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenUpdatingCacheManager.class);
	
	private final TokenUtils tokenUtils = new TokenUtils();
	private final JsonParser jsonParser = JsonParserFactory.create();
	
	private final TokenSource tokenSource;
	private final PublicKeyUpdatingCacheManager publicKeyUpdatingCacheManager;
	private final ConcurrentHashMap<String, Token> cache;
	
	public TokenUpdatingCacheManager(final String name, final ConcurrentHashMap<String, Token> cache,
			final TokenSource tokenSource, final PublicKeyUpdatingCacheManager publicKeyUpdatingCacheManager,
			final long readPeriod, final long shutdownTimeout) {
		super(name, readPeriod, shutdownTimeout);
		
		this.cache = cache;
		this.tokenSource = tokenSource;
		this.publicKeyUpdatingCacheManager = publicKeyUpdatingCacheManager;
	}
	
	TokenSource getTokenSource() {
		return this.tokenSource;
	}
	
	@Override
	public void task() {
		List<String> tokens = new ArrayList<>();
		
		try {
			tokens = this.getTokenSource().getTokens();
		} catch (final Exception ex) {
			LOGGER.error("Failed to read tokens", ex);
		}
		
		int count = 0;
		for (final String token : tokens) {
			if (this.cache.containsKey(token)) {
				TokenUpdatingCacheManager.LOGGER.trace("Old token {} found in the cache", this.tokenUtils.safeToken(token));
			} else {
				try {
					String content = this.tokenUtils
							.useBestKey(token, this.publicKeyUpdatingCacheManager, v -> this.decodeAndVerify(token, v)).getClaims();
					Token tk = new Token(token, this.jsonParser.parseMap(content));
					if (this.tokenUtils.verifyDates(tk)) {
						this.cache.put(token, tk);
						count += 1;
						TokenUpdatingCacheManager.LOGGER.trace(MessageFormat.format(
								"New token {0} expirig at {1,date,yyyy-MM-dd HH:mm:ss.SSSZ} was added to the cache",
								this.tokenUtils.safeToken(tk.token),
								Optional.ofNullable(tk.getExpiresAt()).map(d -> d * 1000).orElse(null)));
					} else {
						TokenUpdatingCacheManager.LOGGER.trace(MessageFormat.format(
								"Token {0} expired at {1,date,yyyy-MM-dd HH:mm:ss.SSSZ} wasn''t added to the cache",
								this.tokenUtils.safeToken(tk.token),
								Optional.ofNullable(tk.getExpiresAt()).map(d -> d * 1000).orElse(null)));
					}
				} catch (final InvalidSignatureException ex) {
					LOGGER.error("Invalid token was ignored: {}", this.tokenUtils.safeToken(token));
				}
			}
		}
		
		TokenUpdatingCacheManager.LOGGER.debug("Tokens added: {}, total token count: {}", count, this.cache.size());
	}
	
	Jwt decodeAndVerify(final String token, final RsaVerifier verifier) {
		return JwtHelper.decodeAndVerify(token, verifier);
	}
}
