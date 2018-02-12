package com.lille1.PFE.security;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)

public class StaticResourceConfiguration extends WebMvcConfigurerAdapter{

	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
    	//registry.addResourceHandler("/resources/**").addResourceLocations("file:///C:/Users/abk/workspace/PFE/src/main/resources/static/");
    	super.addResourceHandlers(registry);
    }
	
}
