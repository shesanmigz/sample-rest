package com.sample.platform.ui.api.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.amazonaws.services.s3.model.S3Object;
import com.sample.platform.ui.api.service.TokenSource;
import com.sample.platform.ui.api.tokens.TokenUtils;

@EnableConfigurationProperties
public class AWSTokenSource extends AWSReader<List<String>> implements TokenSource {
	private static Logger LOGGER = LoggerFactory.getLogger(AWSTokenSource.class);
	
	private final TokenUtils tokenUtils = new TokenUtils();
	
	@Value("${auth.aws-token-source.bucket:}")
	private String bucketName;
	
	@Value("${auth.aws-token-source.prefix:}")
	private String prefix;
	
	@Value("${auth.aws-token-source.region:}")
	private String regionName;
	
	@Value("${auth.aws-token-source.access-key:}")
	private String accessKey;
	
	@Value("${auth.aws-token-source.secret-key:}")
	private String secretKey;
	
	@Override
	protected String getBucketName() {
		return this.bucketName;
	}
	
	@Override
	protected String getPrefix() {
		return this.prefix;
	}
	
	@Override
	protected String getRegionName() {
		return this.regionName;
	}
	
	@Override
	protected String getAccessKey() {
		return this.accessKey;
	}
	
	@Override
	protected String getSecretKey() {
		return this.secretKey;
	}
	
	@Override
	public List<String> getTokens() throws Exception {
		List<String> tokens = new ArrayList<>();
		
		try {
			this.read(tokens);
		} catch (final Exception ex) {
			if (tokens.isEmpty()) {
				/*
				 * This is critical, nothing was read, we need to rethrow the exception.
				 */
				throw ex;
			} else {
				/*
				 * Something was read, so let's try using it later, but don't suppress
				 * the error.
				 */
				AWSTokenSource.LOGGER.error("An error ocuured while reading tokens", ex);
			}
		}
		
		LOGGER.debug("{} token(s) read from bucket {}", tokens.size(), this.getBucketName());
		
		return tokens;
	}
	
	protected void readItem(final List<String> tokens, final S3Object object) throws IOException {
		int count[] = { 0 };
		try (InputStream is = object.getObjectContent()) {
			((List<String>) IOUtils.readLines(is, StandardCharsets.UTF_8.name())).stream().filter(l -> !l.trim().isEmpty())
					.forEach(token -> {
						LOGGER.trace("Token {} read from file {}", this.tokenUtils.safeToken(token), object.getKey());
						tokens.add(token);
						
						count[0] += 1;
					});
		}
		if (count[0] > 1) {
			LOGGER.warn("We expect items in the bucket contain one token, a multiline item was found {}", object.getKey());
		}
	}
}
