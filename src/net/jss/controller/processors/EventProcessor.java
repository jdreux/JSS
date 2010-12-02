package net.jss.controller.processors;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 import net.jss.js.ScriptCore;

public class EventProcessor implements JSSProcessor {
	
	AsyncContext aCtx;
	
	public static EventProcessor getInstance(){
		return new EventProcessor();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException {
		// GET requests disabled for the Comet servlet.
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		aCtx = request.startAsync(request, response);
	}
	
	public void broadcastServerEvent(final Object jsEvent){
		System.out.println("Event processor: brodcasting event to client: ");
		aCtx.start(new Runnable(){
			public void run(){
				ScriptCore sc = ScriptCore.getInstance();
				String json = sc.toJSONString(jsEvent);
				System.out.println("JSON is: "+json);
                try {
					aCtx.getResponse().getWriter().print(json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
}
