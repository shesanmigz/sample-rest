package com.sample.platform.ui.api.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.amazonaws.services.s3.model.S3Object;
import com.google.common.base.Strings;
import com.sample.platform.ui.api.service.PublicKeyReader;

/**
 * Reads public keys from an AWS bucket.
 */
@EnableConfigurationProperties
public class AWSPublicKeyReader extends AWSReader<List<String>> implements PublicKeyReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(AWSPublicKeyReader.class);

	@Value("${auth.public-key.bucket:}")
	private String bucketName;

	@Value("${auth.public-key.prefix:}")
	private String prefix;

	@Value("${auth.public-key.region:}")
	private String regionName;

	@Value("${auth.public-key.access-key:}")
	private String accessKey;

	@Value("${auth.public-key.secret-key:}")
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
	protected void readItem(final List<String> destination, final S3Object object) throws Exception {
		String key = IOUtils.toString(object.getObjectContent(), StandardCharsets.UTF_8.name());
		if (!Strings.nullToEmpty(key).trim().isEmpty()) {
			LOGGER.trace("Found public key:\n" + key);
			destination.add(key);
		}
	}

	@Override
	public List<String> readPrivateKeys() {
		List<String> privateKeys = new ArrayList<>();
		try {
			this.read(privateKeys);
		} catch (final Exception ex) {
			LOGGER.error("An error occured while reading private keys", ex);
		}
		return privateKeys;
	}
}
