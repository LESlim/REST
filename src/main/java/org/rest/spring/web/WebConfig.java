package org.rest.spring.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan( "org.rest.web" )
@EnableWebMvc
public class WebConfig{
	
	public WebConfig(){
		super();
	}
	
}
