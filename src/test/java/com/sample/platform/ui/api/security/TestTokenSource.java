package com.sample.platform.ui.api.security;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.service.TokenSource;

public class TestTokenSource implements TokenSource {
	private final SecurityTestUtils utils = new SecurityTestUtils();
	private final List<String> tokens;
	
	public TestTokenSource() throws Exception {
		this.tokens = ImmutableList.of(
				utils.genToken(1000 * 60 * 60 * 24, ImmutableList.of("localization"), ImmutableList.of("localization_read")));
	}
	
	@Override
	public List<String> getTokens() {
		return this.tokens;
	}
}
