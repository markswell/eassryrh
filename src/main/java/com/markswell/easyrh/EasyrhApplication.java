package com.markswell.easyrh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EasyrhApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/api");
		SpringApplication.run(EasyrhApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EasyrhApplication.class);
	}

}
