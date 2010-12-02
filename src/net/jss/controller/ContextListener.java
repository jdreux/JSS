package net.jss.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.jss.persistence.PersistenceProvider;

@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Context is being initialized");
		try{
			PersistenceProvider.createPersistenceManager();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Context is being destroyed");
		PersistenceProvider.closePersistenceManager();
	}
}
