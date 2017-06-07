package com.sample.platform.ui.api.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.service.PublicKeyReader;

public class TestPublicKeyReader implements PublicKeyReader {
	@Override
	public List<String> readPrivateKeys() {
		String key = null;
		try {
			key = IOUtils.toString(new ClassPathResource("api-authorization-test.crt").getInputStream(), StandardCharsets.UTF_8.name());
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
		return ImmutableList.of(key);
	}
}
