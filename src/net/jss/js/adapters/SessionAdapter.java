package net.jss.js.adapters;

import javax.servlet.http.HttpSession;

 import net.jss.controller.FrontController;

public class SessionAdapter {
	
	
	public static HttpSession getInstance() throws Exception{
		HttpSession session = FrontController.context.getSession();
		
		if(session == null){
			throw new Exception("Could not get a valid HTTP Session.");
		}
		
		return session;
	}
}
