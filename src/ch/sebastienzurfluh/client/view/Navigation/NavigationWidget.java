package ch.sebastienzurfluh.client.view.Navigation;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This widget uses several navigation bars.
 * 
 * It listens to PageChangeEvent to choose wich slider should be displayed. 
 * 
 * @author Sebastien Zurfluh
 *
 */
public class NavigationWidget extends VerticalPanel implements EventBusListener {
	private EventBus pageChangeEventBus;
	
	public NavigationWidget(EventBus pageChangeEventBus) {
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
