package com.openproject.ivcargo.view;

import org.apache.tomcat.jni.Thread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * This is the MAIN Stater class
 * by which the application initiates
 * start and finds the controller(MainController) 
 */
@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer 
{
	static Thread t;
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
	{
		return application.sources(Application.class);
	}
	public static void main(String[] args) 
	{	
		SpringApplication.run(Application.class, args);
	}
}