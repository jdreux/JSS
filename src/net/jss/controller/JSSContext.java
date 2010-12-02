package net.jss.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JSSContext {
	
	private ServletContext sc;
	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public JSSContext(HttpServletRequest req, HttpServletResponse res){
		this.session = req.getSession();
		this.sc = this.session.getServletContext();
		this.request = req;
		this.response = res;
		
	}
	
	public ServletContext getServletContext(){
		return this.sc;
	}
	
	public HttpSession getSession(){
		return this.session;
	}
	
	public HttpServletRequest getRequest(){
		return this.request;
	}
	
	public HttpServletResponse getResponse(){
		return this.response;
	}

}
