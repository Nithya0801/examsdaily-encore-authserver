package com.dexter.dextest.oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.dexter.dextest.oauth2.service.UserDetailsService;


@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/* Working code just commented for checking the below
        http
        .authorizeRequests()
        .antMatchers("/oauth/**").permitAll().and()
        .authorizeRequests()
            .anyRequest().authenticated()            
            .and().csrf().disable()
        .formLogin().permitAll()
            .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
            .invalidateHttpSession(true) ;
            */  
        // @formatter:off
		http.sessionManagement().maximumSessions(10);
		http.sessionManagement().sessionFixation().none();
		
		http
		.anonymous().and().authorizeRequests()	
		.antMatchers("/account/**").permitAll()
		.antMatchers("/setting/**").permitAll()
		.antMatchers("/contact/**").permitAll()
		.antMatchers("/demo/**").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/direct/**").permitAll()
//		.antMatchers("/oauth/token/revokeById/**").permitAll()
		.antMatchers("/tokens/**").permitAll()		
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().csrf().disable();
		// @formatter:on        
	}
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/**","/console/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**");
    }

	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}	
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
   
	
/*	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	auth.jdbcAuthentication().dataSource(dataSource)
	.passwordEncoder(passwordEncoder())
	.authoritiesByUsernameQuery("select email,authority from Users where email=?")
	.usersByUsernameQuery("select email,password,enabled from users where email=?");				
	}*/
    
  
}
