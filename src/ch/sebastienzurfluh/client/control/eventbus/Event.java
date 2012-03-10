package ch.sebastienzurfluh.client.control.eventbus;

/**
 * This is the superclass for events using {@link EventBus}
 * @author Sebastien Zurfluh
 *
 */
public abstract class Event {
	
	public enum EventType {
		// Fired when a new page is requested.
		PAGE_CHANGE_REQUEST,
		// Fired when a page change is approved.
		PAGE_CHANGE_EVENT;
	}

	public abstract EventType getType();

}
