package ch.sebastienzurfluh.client.view.MenuInterface;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class PageRequestHandler implements ClickHandler {
	private EventBus eventBus;

	public PageRequestHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		eventBus.fireEvent(
				new PageChangeRequest(((MenuButton) event.getSource()).getReference()));
	}
	
}
