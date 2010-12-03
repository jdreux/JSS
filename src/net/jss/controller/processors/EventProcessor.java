package net.jss.controller.processors;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jss.js.ScriptCore;

public class EventProcessor implements JSSProcessor {

	AsyncContext aCtx;
	Queue<Object> eventBuffer;

	public static EventProcessor getInstance() {
		return new EventProcessor();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		// GET requests disabled for the Comet servlet.
		System.out.println("Initializing AsyncContext");
		aCtx = request.startAsync(request, response);
		// response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		System.out.println("Initializing AsyncContext");
		aCtx = request.startAsync(request, response);
	}

	public synchronized void broadcastServerEvent(Object jsEvent) {
		System.out.println("Event processor: brodcasting event to client: "+jsEvent);
		try {
			if (aCtx == null) {
				// AsyncContext was not initialized properly yet. Keep the event
				// to be broadcasted later on.
				if (eventBuffer == null)
					eventBuffer = new LinkedList<Object>();
				eventBuffer.add(jsEvent);
			} else {
				// We have a valid AsynContext

				if (eventBuffer != null) {
					// Process all events that might be waiting.
					while (eventBuffer.size() > 0) {
						startAsyncTransaction(eventBuffer.remove());
					}
				}
				
				startAsyncTransaction(jsEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startAsyncTransaction(final Object jsEvent) {
		aCtx.start(new Runnable() {
			public void run() {
				ScriptCore sc = ScriptCore.getInstance();
				String json = sc.toJSONString(jsEvent);
				System.out.println("JSON is: " + json);
				try {
					aCtx.getResponse().getWriter().print(json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				aCtx.complete();
			}
		});
	}
}
