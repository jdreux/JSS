package net.jss.controller.processors;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageRequestProcessor implements JSSProcessor {
	
	private static PageRequestProcessor instance;
	
	public static PageRequestProcessor getInstance(){
		if(instance == null){
			instance = new PageRequestProcessor();
		}
		
		return instance;
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException {
		
		if(request.getRequestURI().endsWith(".jsp")){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		if (!request.getRequestURI().endsWith(".html")) {
			throw new ServletException(
					"Cannot handle requests for non .html. Request was: "
							+ request.getRequestURI());
		}

		String path = request.getServletPath();
		path = path.substring(0, path.length() - ".html".length()) + ".jsp";
		System.out.println("Forwarding request to: " + path);
		RequestDispatcher dispatcher = servletContext
				.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		// POST requests disabled
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

}
