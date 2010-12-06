package net.jss.controller.processors;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.jss.controller.FrontController;
import net.jss.js.JSSON;

public class EventProcessor implements JSSProcessor {

	AsyncContext aCtx;
	Queue<Object> eventBuffer;

	public static EventProcessor getInstance() {
		HttpSession session = FrontController.context.getSession();
		EventProcessor instance = (EventProcessor) session.getAttribute(EventProcessor.class.getName());
		if (instance == null) {
			try {
				instance = new EventProcessor();
				session.setAttribute(EventProcessor.class.getName(), instance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				instance = null;
			}
		}
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		// GET requests disabled for the Comet servlet.
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		System.out.println("Initializing AsyncContext");
		aCtx = request.startAsync(request, response);
		flushEvents();
	}

	public synchronized void broadcastServerEvent(Object jsEvent) {
		System.out.println("Event processor: brodcasting event to client: "+jsEvent);
		System.out.println(this);
		try {
			if (aCtx == null) {
				// AsyncContext was not initialized properly yet. Keep the event
				// to be broadcasted later on.
				if (eventBuffer == null)
					eventBuffer = new LinkedList<Object>();
				eventBuffer.add(jsEvent);
				System.out.println("Async Context not initialized. "+eventBuffer.size()+" events in queue.");
			} else {
				// We have a valid AsynContext

				flushEvents();
				
				startAsyncTransaction(jsEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void flushEvents(){
		if (eventBuffer != null) {
			// Process all events that might be waiting.
			while (eventBuffer.size() > 0) {
				startAsyncTransaction(eventBuffer.remove());
			}
		}
	}
	
	private void startAsyncTransaction(final Object jsEvent) {
		System.out.println("Starting async connection");
		aCtx.start(new Runnable() {
			public void run() {
				String json = JSSON.toJSONString(jsEvent);
				System.out.println("JSON is: " + json);
				try {
					aCtx.getResponse().getWriter().print(json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Ending current thread");
				} finally{
					aCtx.complete();
					aCtx = null;
				}
			}
		});
	}
}
