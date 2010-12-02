package net.jss.js.adapters;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter {

	private List<Object> callbacks;

	public static EventsAdapter getInstance() {
		return new EventsAdapter();
	}

	private EventsAdapter() {
		this.callbacks = new ArrayList();
	}
	
	

}
