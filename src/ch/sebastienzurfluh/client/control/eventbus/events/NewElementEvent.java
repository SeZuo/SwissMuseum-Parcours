package ch.sebastienzurfluh.client.control.eventbus.events;

import ch.sebastienzurfluh.client.control.eventbus.Event;

public class NewElementEvent extends Event {

	public NewElementEvent(DataType type) {
	}

	@Override
	public EventType getType() {
		return EventType.NEW_ELEMENT;
	}
}
