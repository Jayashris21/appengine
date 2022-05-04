package com.example.objectify;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

public class Listener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ObjectifyService.begin();
		ObjectifyService.register(User.class);
	    ObjectifyService.register(Address.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}

}
