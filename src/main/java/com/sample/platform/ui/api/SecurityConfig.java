package com.sample.platform.ui.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.sample.platform.ui.api.tokens.CustomTokenConverter;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "auth")
public class SecurityConfig extends ResourceServerConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Value("${auth.token-verification-enabled}")
	private boolean tokenVerificationEnabled;
	
	@Value("${auth.audience-name}")
	private String audienceName;
	
	@Value("${auth.cleanup-period}")
	private long cleanupPeriod;
	
	@Value("${auth.cleanup-shutdown-timeout}")
	private long shutdownTimeout;
	
	@Autowired
	private CustomTokenConverter converter;
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(this.converter);
	}
	
	@Bean
	public CustomTokenConverter createConverter() throws Exception {
		return new CustomTokenConverter(this.audienceName, this.cleanupPeriod, this.shutdownTimeout,
				this.tokenVerificationEnabled);
	}
	
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		LOGGER.info("Auth token verification is {}", this.tokenVerificationEnabled ? "enabled" : "disabled");
		
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl antMatchers = http.authorizeRequests()
				.antMatchers("/api/v1/**");
		
		if (this.tokenVerificationEnabled) {
			antMatchers.access("#oauth2.isOAuth()");
		} else {
			antMatchers.permitAll();
		}
	}
	
	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(this.audienceName);
	}
}
