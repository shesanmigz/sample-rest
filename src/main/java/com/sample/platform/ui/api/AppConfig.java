package com.sample.platform.ui.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.google.common.base.Strings;
import com.sample.platform.ui.api.service.PublicKeyReader;
import com.sample.platform.ui.api.service.TokenSource;

@Configuration
@ComponentScan
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "auth")
@PropertySource("classpath:application.yml")
public class AppConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		return new TomcatEmbeddedServletContainerFactory();
	}
	
	@Bean
	public TokenSource tokenSource(@Value("${auth.token-source:}") final String className) throws Exception {
		return Strings.nullToEmpty(className).trim().isEmpty() ? null
				: (TokenSource) Class.forName(className).newInstance();
	}
	
	@Bean
	public PublicKeyReader publicKeyReader(@Value("${auth.public-key.reader:}") final String className) throws Exception {
		return Strings.nullToEmpty(className).trim().isEmpty() ? null
				: (PublicKeyReader) Class.forName(className).newInstance();
	}
}
