package com.sample.platform.ui.api.tokens;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.google.common.base.Strings;

public class CustomTokenConverter extends JwtAccessTokenConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomTokenConverter.class);
	
	private final TokenUtils tokenUtils = new TokenUtils();
	private final ConcurrentHashMap<String, Token> cache = new ConcurrentHashMap<>();
	
	private final String audienceName;
	private final TokenCleaningCacheManager tokenCleaningCacheManager;
	
	@Autowired
	private PublicKeyUpdatingCacheManager publicKeyUpdatingCacheManager;
	
	public CustomTokenConverter(final String audienceName, final long cleanupPeriod, final long shutdownTimeout,
			final boolean tokenVerificationEnabled) throws Exception {
		if (Strings.nullToEmpty(audienceName).trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		this.audienceName = audienceName;
		this.tokenCleaningCacheManager = tokenVerificationEnabled
				? new TokenCleaningCacheManager("AuthVerify-CleanExpiredTokens", cache, cleanupPeriod, shutdownTimeout) : null;
	}
	
	@PreDestroy
	public void destroy() throws Exception {
		if (this.tokenCleaningCacheManager != null) {
			this.tokenCleaningCacheManager.destroy();
		}
	}
	
	Map<String, Token> getCache() {
		return this.cache;
	}
	
	PublicKeyUpdatingCacheManager getPublicKeyUpdatingCacheManager() {
		return this.publicKeyUpdatingCacheManager;
	}

	@Override
	protected Map<String, Object> decode(final String stringToken) {
		/*
		 * The biggest issue here is that we do not synchronize the cache access and
		 * the cache modification. It means that while we are decoding a token,
		 * someone else can try using the same token and since the token hasn't yet
		 * been added to the cache the second call will start the token decoding
		 * once again. A possible case is when requests are sent at the same time
		 * and they use the same new token unknown to the application. All of the
		 * callers will need to wait until the token is decoded. But in normal case
		 * this approach has an advantage for old token retrieval, which may work
		 * faster since we do not need to synchronize and we can use some benefits
		 * of the concurrent map optimizations. In any case this is a thread-safe
		 * map and it will remain consistent even if it's being modified from within
		 * different threads.
		 */
		Token token = this.getCache().get(stringToken);
		if (token == null) {
			LOGGER.trace("Cache miss for {}", this.tokenUtils.safeToken(stringToken));
			
			token = this.tokenUtils.useBestKey(stringToken, this.getPublicKeyUpdatingCacheManager(), v -> {
				this.setVerifier(v);
				return this.doDecode(stringToken);
			});
			
			if (token.getAudience().contains(this.audienceName)) {
				/*
				 * We cache only tokens issued for us. Other tokens will be rejected
				 * later by the Spring OAuth security; the thrown error satisfies our
				 * requirements, no need to override here.
				 */
				this.getCache().put(stringToken, token);
			} else {
				LOGGER.warn("Token {} misses audience {}", this.tokenUtils.safeToken(stringToken), this.audienceName);
			}
		} else {
			LOGGER.trace("Cache hit for {}", this.tokenUtils.safeToken(stringToken));
			/*
			 * The key signature is valid and the key contains the needed audience, so
			 * we need to check only dates which works faster than the normal verify
			 * since we do not need to decode the token.
			 */
			if (!this.tokenUtils.verifyDates(token)) {
				this.getCache().remove(stringToken);
				
				throw new OAuth2AccessDeniedException(
						MessageFormat.format("Access token {0} expired at {1,date,yyyy-MM-dd HH:mm:ss.SSSZ}", this.tokenUtils.safeToken(token.token),
								Optional.ofNullable(token.getExpiresAt()).map(d -> d * 1000).orElse(null)));
			}
		}
		
		return token.json;
	}
	
	Token doDecode(final String token) {
		return new Token(token, super.decode(token));
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// It makes no sense to set any verifiers here since we may need to try
		// several keys.
	}
}
