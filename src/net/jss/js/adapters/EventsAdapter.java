package net.jss.js.adapters;

import java.util.ArrayList;
import java.util.List;

import net.jss.controller.processors.EventProcessor;

public class EventsAdapter {

	private List<Object> callbacks;

	public static EventsAdapter getInstance() {
		return new EventsAdapter();
	}

	private EventsAdapter() {
		this.callbacks = new ArrayList();
	}
	
	public void fireEvent(Object jsEvent){
		EventProcessor.getInstance().broadcastServerEvent(jsEvent);
	}
	

}
