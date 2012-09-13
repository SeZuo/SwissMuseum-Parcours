package ch.sebastienzurfluh.client.control.eventbus;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;

/**
 * Handles user actions (defined in the {@code Action} class.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class ActionHandler implements EventBusListener {

	@Override
	public EventType getEventType() {
		return EventType.ACTION;
	}

	@Override
	public void notify(Event e) {
		//TODO Handle the Action! (save to DB,...)
	}

}
