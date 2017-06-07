package com.sample.platform.ui.api.tokens;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.sample.platform.ui.api.service.PublicKeyReader;

/**
 * This manager reads private keys periodically from an AWS bucket and updates
 * the cache. The provided cache should be thread safe.
 */
@Component
@EnableConfigurationProperties
public class PublicKeyUpdatingCacheManager extends CacheManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicKeyUpdatingCacheManager.class);
	
	@Autowired
	private PublicKeyReader reader;
	
	private final ConcurrentHashMap<String, RsaVerifier> cache;
	
	public PublicKeyUpdatingCacheManager(@Value("${auth.public-key.read-period}") final long readPeriod,
			@Value("${auth.cleanup-shutdown-timeout}") final long shutdownTimeout,
			@Value("${auth.token-send-enabled}") final boolean tokenSendEnabled,
			@Value("${auth.token-verification-enabled}") final boolean tokenVerificationEnabled) {
		super("AuthPublicKey-ReadKeys", tokenSendEnabled && tokenVerificationEnabled ? readPeriod : 0, shutdownTimeout);
		
		this.cache = new ConcurrentHashMap<>();
	}
	
	public ConcurrentHashMap<String, RsaVerifier> getCache() {
		return this.cache;
	}
	
	PublicKeyReader getReader() {
		return this.reader;
	}
	
	@Override
	public void task() {
		int oldCount = this.getCache().size();
		
		PublicKeyReader reader = this.getReader();
		if (reader != null) {
			for (final String privateKey : reader.readPrivateKeys()) {
				if (!this.getCache().containsKey(privateKey)) {
					this.getCache().put(privateKey, this.createVerifier(privateKey));
					LOGGER.trace("Public key was added to the cache\n{}", privateKey);
				}
			}
		}
		
		LOGGER.debug("New public keys read: {}, total public key count: {}", this.getCache().size() - oldCount,
				this.getCache().size());
	}
	
	RsaVerifier createVerifier(final String key) {
		RsaVerifier verifier = null;
		try {
			String encodedKey = Lists.newArrayList(Splitter.on("\n").split(key)).stream().filter(l -> !l.startsWith("-----"))
					.reduce("", (k, l) -> k + l);
			
			RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(encodedKey)));
			verifier = new RsaVerifier(publicKey);
		} catch (final Exception ex) {
			throw new IllegalStateException("Failed to create a key varifier.", ex);
		}
		return verifier;
	}
}
