package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.client.model.structure.DataReference;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;

public class TextLink extends Label implements ClickHandler {
	private EventBus eventBus;
	private DataReference reference;

	public TextLink(EventBus eventBus, DataReference linkReference, String label) {
		this.eventBus = eventBus;
		this.reference = linkReference;
		
		this.setStyleName("textLink");
		
		this.setText(label);
		
		this.addClickHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		eventBus.fireEvent(new PageChangeRequest(reference));
	}
}
