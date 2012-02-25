package ch.sebastienzurfluh.client.control.eventbus;

public abstract class Event {
	
	public enum EventType {
		LOAD_PAGE_REQUEST, LOAD_PAGE_EVENT;
	}

	public abstract EventType getType();

}
