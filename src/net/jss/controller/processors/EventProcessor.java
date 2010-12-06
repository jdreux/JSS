package net.jss.controller.processors;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.jss.controller.FrontController;
import net.jss.js.JSSON;

public class EventProcessor implements JSSProcessor {

	// AsyncContext aCtx;
	ConcurrentLinkedQueue<Object> events;
	ConcurrentLinkedQueue<AsyncContext> asynchronousRequests;

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

	public EventProcessor() {
		this.asynchronousRequests = new ConcurrentLinkedQueue<AsyncContext>();
		this.events = new ConcurrentLinkedQueue<Object>();
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

		System.out.println("Received async request : " + request.getParameterMap().toString());

		System.out.println("Initializing AsyncContext");
		asynchronousRequests.add(request.startAsync(request, response));

		// Now that we have a request, process any events that may be in queue:
		this.processEventQueue();
	}

	public synchronized void broadcastServerEvent(Object jsEvent) {
		System.out.println("Event processor: brodcasting event to client: " + JSSON.toJSONString(jsEvent));
		try {
			this.events.add(jsEvent);
			this.processEventQueue();
			System.out.println("Event queue size is: " + this.events.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to process several events using a single Asynchronous request.
	 * 
	 * @param aCtx
	 */
	private synchronized void processEventQueue() {

		if (this.events.size() > 0 && this.asynchronousRequests.size() > 0) {
			AsyncContext aCtx = this.asynchronousRequests.remove();
			System.out.println("Processing " + this.events.size() + " event(s).");
			String json = "[";
			while(this.events.size()>0){
				json += JSSON.toJSONString(this.events.remove());
				if(this.events.size()>0){
					json+=",";
				}
			}
			json += "]";
			//Object[] allEvents = this.events.toArray(new Object[this.events.size()]);
			
			//String json = JSSON.toJSONString(allEvents);
			// JSSON.toJSONString(allEvents);
			this.events.clear();
			this.startAsyncTransaction(json, aCtx);
		}
	}

	private void startAsyncTransaction(final String json, final AsyncContext aCtx) {
		System.out.println("Starting Async Transaction: " + aCtx.toString());
		aCtx.start(new Runnable() {
			public void run() {
				System.out.println("JSON is: " + json);
				try {
					aCtx.getResponse().getWriter().print(json);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					completeAsyncTransaction(aCtx);
				}
			}
		});
	}

	private void completeAsyncTransaction(AsyncContext aCtx) {
		System.out.println("Completing Async Transaction: " + aCtx.toString());
		aCtx.complete();
		aCtx = null;
	}
}
