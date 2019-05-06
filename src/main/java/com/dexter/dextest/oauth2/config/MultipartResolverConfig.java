package com.dexter.dextest.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartResolverConfig {
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	
	    final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(10000000);
	
	    return multipartResolver;
	}	
}
