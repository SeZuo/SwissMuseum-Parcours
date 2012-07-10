package ch.sebastienzurfluh.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;

/**
 * This class listens to the page changes in order to scroll
 * automatically to the right position on load.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class ScrollToPanelOnEvent implements EventBusListener {
	private Panel panel;
	private EventType eventType;
	
	private ScrollToPanelOnEvent(EventBus eventBus, Panel panel, EventType eventType) {
		this.panel = panel;
		this.eventType = eventType;
		
		eventBus.addListener(this);
	}
	
	@Override
	public EventType getEventType() {
		return eventType;
	}

	@Override
	public void notify(Event e) {
		Window.scrollTo(0 , panel.getElement().getAbsoluteTop());
	}

	public static ScrollToPanelOnEvent addRule(EventBus eventBus, Panel panel, EventType eventType) {
		return new ScrollToPanelOnEvent(eventBus, panel, eventType);
	}

}
