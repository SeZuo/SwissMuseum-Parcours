package ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class PageRequestClickHandler implements ClickHandler {
	protected EventBus eventBus;

	public PageRequestClickHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		takeAction(((MenuButton) event.getSource()).getReference());
	}
	
	private void takeAction(DataReference reference) {
		eventBus.fireEvent(new PageChangeRequest(reference));
	}
	
}
