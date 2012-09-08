package ch.sebastienzurfluh.client.view.menuinterface;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.client.model.structure.DataReference;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class PageRequestClickHandler implements ClickHandler {
	private EventBus eventBus;

	public PageRequestClickHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		requestPage(((MenuButton) event.getSource()).getReference());
	}
	
	public void requestPage(DataReference reference) {
		eventBus.fireEvent(new PageChangeRequest(reference));
	}
	
}
