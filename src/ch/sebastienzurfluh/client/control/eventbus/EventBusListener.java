package ch.sebastienzurfluh.client.control.eventbus;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;

public interface EventBusListener {
	EventType getEventType();

	void notify(Event e);

}
