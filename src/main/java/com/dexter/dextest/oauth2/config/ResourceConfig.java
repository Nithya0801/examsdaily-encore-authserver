package com.dexter.dextest.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig  implements WebMvcConfigurer {
	@Autowired private Environment env;
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		  registry.addResourceHandler("/uploads/**").addResourceLocations("file:/home/dexter/wspace/uploads/");
		  registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + env.getProperty("resource.uploads.location") );
//	      registry.addResourceHandler("/uploads/**").addResourceLocations("file:/home/dexter/dextest-workspace2/uploads/");
		

}
}
