package net.jss.controller.processors;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JSSProcessor {

	public void doGet(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException;

}
