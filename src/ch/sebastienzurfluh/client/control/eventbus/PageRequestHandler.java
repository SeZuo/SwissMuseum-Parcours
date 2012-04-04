package ch.sebastienzurfluh.client.control.eventbus;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;

/**
 * This object will handle the page change requests.
 * @author Sebastien Zurfluh
 *
 */
public class PageRequestHandler implements EventBusListener {
	EventBus eventBus;
	Model model;
	
	/**
	 * Just create the object, it will attach itself to the given event bus.
	 * @param eventBus
	 */
	public PageRequestHandler(EventBus eventBus, Model model) {
		this.eventBus = eventBus;
		this.model = model;
		
		eventBus.addListener(this);
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}

	@Override
	public void notify(Event e) {
		if(e instanceof PageChangeRequest) {
			PageChangeRequest pageChangeRequest = (PageChangeRequest) e;
			
			//TODO Check if the requested page is OK.
			//TODO Collect the necessary data and fire an event.
			
			
		}
	}

}
