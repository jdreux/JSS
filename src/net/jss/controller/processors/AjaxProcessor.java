package net.jss.controller.processors;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jss.js.JSSON;
import net.jss.js.ScriptCore;

public class AjaxProcessor implements JSSProcessor {
	
	private static AjaxProcessor instance;
	
	public static AjaxProcessor getInstance(){
		if(instance == null){
			instance = new AjaxProcessor();
		}
		
		return instance;
	}
	
	/*
	 * 
	 */
	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {

		System.out.println("Processing POST request: "
				+ request.getParameterMap().toString());
		
		if(!ScriptCore.isInstanceInSession()){
			System.out.println("Instance is not in session. Need to reload the page.");
			
			response.sendError(HttpServletResponse.SC_RESET_CONTENT);
			return;
		}
		
		String fn = "null", ar = "null", caller = "null";
		Map<String, String[]> params = request.getParameterMap();

		fn = params.get("fn")[0];
		ar = params.get("ar")[0];

		// Small algorithm to find the js function's context if necessary.
		if (fn.lastIndexOf(".") > -1) {
			String[] sa = fn.split(".");
			caller = "";
			for (int i = 0; i < sa.length - 1; i++) {
				caller += sa[i];
				if (i < sa.length - 2) {
					caller += ".";
				}
			}
		}

		System.out.println("Received execution ajax request for: " + fn
				+ " with parameters " + ar);
		ScriptCore sc = ScriptCore.getInstance();
		String script = fn + ".apply(" + caller + ", " + ar + ");";
		System.out.println("Executing script: " + script);

		String r = JSSON.toJSONString(sc.executeScript(script));
		System.out.println("The result was "+r);
		response.setContentType("application/json");
		response.getWriter().write(r);
		System.out.println("The result was: " + r);
	}

	@Override
	public void doGet(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {

		// GET requests disabled for the AJAX servlet.
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

}
