package com.dexter.dextest.oauth2;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling

public class DextestOauth2AuthServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DextestOauth2AuthServerApplication.class, args);
	}
}
