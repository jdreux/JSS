package net.jss.controller.processors;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JSSProcessor {
	
	/**
	 * Method to forward GET requests.
	 * @param request
	 * @param response
	 * @param servletContext
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException;
	
	/**
	 * Method to forward POST requests.
	 * @param request
	 * @param response
	 * @param servletContext
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException;

}
