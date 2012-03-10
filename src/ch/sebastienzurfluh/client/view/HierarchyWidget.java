package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Hierarchy widget gives a way to go up in the booklet's hierarchy. For instance, when looking at a page, the user
 * can see "Booklet > Chapter > Page", and choose to go back to the chapter or the booklet.
 *
 * @author Sebastien Zurfluh
 *
 */
public class HierarchyWidget extends HorizontalPanel implements EventBusListener {
	private EventBus pageChangeEventBus;
	
	public HierarchyWidget(EventBus pageChangeEventBus) {
		this.pageChangeEventBus = pageChangeEventBus;
		
		pageChangeEventBus.addListener(this);
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
