package com.sample.platform.ui.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class Application extends AsyncConfigurerSupport {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
