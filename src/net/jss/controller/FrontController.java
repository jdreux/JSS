package net.jss.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.catalina.comet.CometEvent;

import net.jss.controller.processors.AjaxProcessor;
import net.jss.controller.processors.ConsoleProcessor;
import net.jss.controller.processors.EventProcessor;
import net.jss.controller.processors.PageRequestProcessor;
import net.jss.controller.processors.JSSProcessor;

/**
 * Front controller that forwards the request to the corresponding request processors.
 * The mappings are:
 * - /sj_ajax/ 		--> AjaxProcessor
 * - /sj_console/ 	--> ConsoleProcessor
 * - /sj_comet/ 	--> EventProcessor
 * - *.html			--> PageRequestProcessor
 */

@WebServlet(name = "Front Controller", loadOnStartup = 1, asyncSupported = true, urlPatterns = { "/sj_ajax/",
		"/sj_console/", "/sj_comet/", "*.html" })
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = -3364415826397739136L;

	public static JSSContext context;

	private boolean isGet;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("Creating new FrontController servlet");
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.isGet = true;
		System.out.println("GET");
		this.serviceRequest(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.isGet = false;
		System.out.println("POST");
		this.serviceRequest(request, response);
	}

	private void serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		FrontController.context = new JSSContext(request, response);

		System.out.println("Front controller: received " + (isGet ? "GET" : "POST") + " request for "
				+ request.getServletPath());

		if (request.getServletPath().equals("/sj_ajax/")) {
			this.process(AjaxProcessor.getInstance());
		} else if (request.getServletPath().equals("/sj_console/")) {
			this.process(ConsoleProcessor.getInstance());
		} else if (request.getServletPath().equals("/sj_comet/")) {
			this.process(EventProcessor.getInstance());
		} else {
			this.process(PageRequestProcessor.getInstance());
		}

	}

	private void process(JSSProcessor processor) throws ServletException, IOException {
		HttpServletRequest request = FrontController.context.getRequest();
		HttpServletResponse response = FrontController.context.getResponse();
		ServletContext servletContext = FrontController.context.getServletContext();
		System.out.println("Request is handled by " + processor.getClass());
		if (isGet) {
			processor.doGet(request, response, servletContext);
		} else {
			processor.doPost(request, response, servletContext);
		}
	}
}
