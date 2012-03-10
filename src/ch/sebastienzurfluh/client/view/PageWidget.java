package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Main page widget. It displays the content of a page.
 * 
 * Fires {@link PageChangeRequest} when it has been modified.
 * 
 * @author Sebastien Zurfluh
 *
 */
public class PageWidget extends DockPanel implements EventBusListener {
	private EventBus pageChangeEventBus;
	
	public PageWidget(EventBus pageChangeEventBus) {
		this.pageChangeEventBus = pageChangeEventBus;
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}

	@Override
	public void notify(Event e) {
		// TODO Auto-generated method stub
		
	}
}
