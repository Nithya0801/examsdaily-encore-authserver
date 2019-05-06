package com.dexter.dextest.oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        TokenStore tokenStore = new JdbcTokenStore(dataSource);
        resources.resourceId("examsdaily-oauth2-api-services")
                .tokenStore(tokenStore);   	
    }
 

    @Override
    public void configure(HttpSecurity http) throws Exception {
		http
		.anonymous().and().authorizeRequests()
		.antMatchers("/account/**").permitAll()
		.antMatchers("/setting/**").permitAll()
		.antMatchers("/contact/**").permitAll()
		.antMatchers("/demo/**").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/oauth/token/revokeById/**").permitAll()
		.antMatchers("/tokens/**").permitAll()		
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		       
//            .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
		.and().csrf().disable() ;
                // Add headers required for CORS requests.
//                .headers().addHeaderWriter((request, response) -> {
//                    response.addHeader("Access-Control-Allow-Origin", "*");
//                    if (request.getMethod().equals("OPTIONS")) {
//                        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
//                        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
//                    }
//                });
    }
}