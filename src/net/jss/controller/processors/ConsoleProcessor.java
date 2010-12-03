package net.jss.controller.processors;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 import net.jss.js.ScriptCore;

public class ConsoleProcessor implements JSSProcessor {

	private static ConsoleProcessor instance;
	
	public static ConsoleProcessor getInstance(){
		if(instance == null){
			instance = new ConsoleProcessor();
		}
		
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = servletContext
				.getRequestDispatcher("/jss/sj_console.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		System.out.println("Processing console request");
		if (request.getParameter("jscode") != null) {
			String code = request.getParameter("jscode");
			System.out.println("Code to be executed is: "+code);
			ScriptCore sc = ScriptCore.getInstance();

			Object o = sc.executeScript(code);
			if (o == null) {
				response.getOutputStream().write("null".getBytes());
			} else {
				response.getOutputStream().write(o.toString().getBytes());
			}
		}
	}
}
