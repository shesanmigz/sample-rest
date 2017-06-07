package com.sample.platform.ui.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sample.platform.ui.api.service.GitService;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private GitService gitService;
	
	@Bean
	public UiConfiguration uiConfig() {
		return new UiConfiguration(null, "list", "alpha", "schema", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
				false, true);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sample.platform.ui.api.controller")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(null).description(null).termsOfServiceUrl(null).license(null).licenseUrl(null).version(this.gitService.getCommitId())
				.build();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui.html");

		registry.addRedirectViewController("/v2/api-docs", "/v2/api-docs?group=restful-api");
	}
}
