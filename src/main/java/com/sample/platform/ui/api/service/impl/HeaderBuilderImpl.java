package com.sample.platform.ui.api.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.service.HeaderBuilder;
import com.sample.platform.ui.api.service.TokenSource;
import com.sample.platform.ui.api.tokens.PublicKeyUpdatingCacheManager;
import com.sample.platform.ui.api.tokens.Token;
import com.sample.platform.ui.api.tokens.TokenCleaningCacheManager;
import com.sample.platform.ui.api.tokens.TokenUpdatingCacheManager;
import com.sample.platform.ui.api.tokens.TokenUtils;
import com.sample.platform.ui.api.web.client.exceptions.BadRequestException;

@Service
@ConfigurationProperties(prefix = "header-builder")
public class HeaderBuilderImpl implements HeaderBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderBuilderImpl.class);
	
	private final List<AllowedHeaderDefinition> headers = new ArrayList<>();
	private final ConcurrentHashMap<String, Token> cache = new ConcurrentHashMap<>();
	private final TokenUtils tokenUtils = new TokenUtils();
	
	private final boolean tokenSendEnabled;
	private final TokenCleaningCacheManager tokenCleaningCacheManager;
	private final TokenUpdatingCacheManager tokenUpdatingCacheManager;
	private final PublicKeyUpdatingCacheManager publicKeyUpdatingCacheManager;
	
	public HeaderBuilderImpl(@Value("${auth.cleanup-period}") final long cleanupPeriod,
			@Value("${auth.read-period}") final long readPeriod,
			@Value("${auth.cleanup-shutdown-timeout}") final long shutdownTimeout,
			@Value("${auth.token-send-enabled}") final boolean tokenSendEnabled, @Autowired final TokenSource tokenSource,
			@Autowired final PublicKeyUpdatingCacheManager publicKeyUpdatingCacheManager) {
		
		LOGGER.info("Auth token sending is {}", tokenSendEnabled ? "enabled" : "disabled");
		
		this.tokenSendEnabled = tokenSendEnabled;
		this.publicKeyUpdatingCacheManager = publicKeyUpdatingCacheManager;
		this.tokenCleaningCacheManager = this.tokenSendEnabled
				? new TokenCleaningCacheManager("AuthSend-CleanExpiredTokens", this.cache, cleanupPeriod, shutdownTimeout) : null;
		this.tokenUpdatingCacheManager = this.tokenSendEnabled ? new TokenUpdatingCacheManager("AuthSend-ReadTokens",
				this.cache, tokenSource, publicKeyUpdatingCacheManager, readPeriod, shutdownTimeout) : null;
	}
	
	ConcurrentHashMap<String, Token> getCache() {
		return this.cache;
	}
	
	@PostConstruct
	public void init() {
		if (this.publicKeyUpdatingCacheManager != null) {
			this.publicKeyUpdatingCacheManager.task();
		}
		
		if (this.tokenUpdatingCacheManager != null) {
			// This is to make sure we have tokens before we are ready to accept
			// incoming HTTP requests
			this.tokenUpdatingCacheManager.task();
			if (this.getCache().isEmpty()) {
				throw new IllegalStateException("No tokens read");
			}
		}
	}
	
	@PreDestroy
	public void destroy() throws Exception {
		if (this.tokenCleaningCacheManager != null) {
			this.tokenCleaningCacheManager.destroy();
			this.tokenUpdatingCacheManager.destroy();
		}
		
		if (this.publicKeyUpdatingCacheManager != null) {
			this.publicKeyUpdatingCacheManager.destroy();
		}
	}
	
	public List<AllowedHeaderDefinition> getHeaders() {
		return this.headers;
	}
	
	@Override
	public HttpHeaders build(final HttpHeaders headers, final String traceId) {
		HttpHeaders newHeaders = new HttpHeaders();
		for (final Entry<String, List<String>> entry : headers.entrySet()) {
			AllowedHeaderDefinition header = this.findHeader(entry.getKey());
			if (header != null && !header.getPatterns().isEmpty()) {
				this.validateHeaderValue(header, entry.getValue());
				LOGGER.trace("Header {}: {} added to the request", header.getName(), entry.getValue().iterator().next());
				newHeaders.put(header.getName(), entry.getValue());
			} else {
				LOGGER.trace("Ignoring header {}: {}", header == null ? entry.getKey() : header.getName(),
						Joiner.on("; ").join(entry.getValue().iterator()));
			}
		}
		
		if (this.tokenSendEnabled) {
			this.addAuthHeader(newHeaders);
		}
		
		if (traceId != null) {
			this.addTraceIdHeader(newHeaders, traceId);
		}
		
		return newHeaders;
	}
	
	private void validateHeaderValue(final AllowedHeaderDefinition header, final List<String> headerValues) {
		if (headerValues.size() != 1) {
			String message = header.name + " may have only one value; multi-value headers are not supported.";
			/*
			 * To see the error in the logs we need to log it explicitly because BAD
			 * requests are considered to be client errors and not errors of the
			 * application, and no stack traces are logged in this case.
			 */
			LOGGER.debug(message);
			throw new BadRequestException(message);
		}
		
		String headerValue = headerValues.iterator().next();
		
		boolean matches = false;
		for (final CompiledPattern pattern : header.getPatterns()) {
			if (pattern.getPattern().matcher(headerValue).matches()) {
				matches = true;
				break;
			}
		}
		if (!matches) {
			String patterns = Joiner.on(";").join(header.getPatterns().stream().map(CompiledPattern::getRegExp).iterator());
			String message = MessageFormat.format("{0} is not a valid value for {1}; patterns used: {2}", headerValue,
					header.name, patterns);
			LOGGER.debug(message);
			throw new BadRequestException(message);
		}
	}
	
	private AllowedHeaderDefinition findHeader(final String headerName) {
		AllowedHeaderDefinition foundHeader = null;
		for (final AllowedHeaderDefinition header : this.headers) {
			if (headerName.equalsIgnoreCase(header.name)) {
				foundHeader = header;
				break;
			}
		}
		return foundHeader;
	}
	
	private void addAuthHeader(final HttpHeaders headers) {
		Token token = this.tokenUtils.findToken(this.getCache().values());
		if (token == null) {
			throw new IllegalStateException("No token found");
		}
		
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.token);
		LOGGER.trace("Header {}: {} added to the request", HttpHeaders.AUTHORIZATION, "Bearer " + this.tokenUtils.safeToken(token.token));
	}
	
	private void addTraceIdHeader(final HttpHeaders headers, final String traceId) {
		List<String> list = Optional.ofNullable(headers.get(ShopApiConstants.TRACE_HEADER)).orElseGet(ImmutableList::of);
		if (list.isEmpty()) {
			headers.add(ShopApiConstants.TRACE_HEADER, traceId);

			LOGGER.trace("Header {}: {} added to the request", ShopApiConstants.TRACE_HEADER, traceId);
		}
	}
	
	public static class AllowedHeaderDefinition {
		public String name;
		public List<CompiledPattern> patterns;
		
		public AllowedHeaderDefinition() {
			this.name = null;
			this.patterns = new ArrayList<>();
		}
		
		public AllowedHeaderDefinition(final String name, final List<String> patterns) {
			this.name = name;
			
			this.patterns = new ArrayList<>();
			for (final String pattern : patterns) {
				this.patterns.add(new CompiledPattern(pattern));
			}
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setName(final String name) {
			this.name = name;
		}
		
		public List<CompiledPattern> getPatterns() {
			return this.patterns;
		}
		
		public void setPatterns(final List<CompiledPattern> patterns) {
			this.patterns = patterns;
		}
	}
	
	/**
	 * This wrapper class is needed for Spring to properly set compiled patterns
	 * from strings.
	 */
	public static class CompiledPattern {
		private final Pattern pattern;
		private final String regExp;
		
		public CompiledPattern(final String regExp) {
			this.regExp = regExp;
			this.pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
		}
		
		public String getRegExp() {
			return this.regExp;
		}
		
		public Pattern getPattern() {
			return this.pattern;
		}
	}
}
