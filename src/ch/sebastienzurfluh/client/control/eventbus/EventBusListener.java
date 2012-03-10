package ch.sebastienzurfluh.client.control.eventbus;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;

/**
 * Superclass for {@link EventBus} listeners.
 * @author Sebastien Zurfluh
 *
 */
public interface EventBusListener {
	/**
	 * Get the listened {@link EventType}.
	 * @return the {@link EventType} this is listening to.
	 */
	EventType getEventType();

	/**
	 * Call this function when an {@link Event} {@code e} has occurred.
	 * @param e the notified {@link Event}.
	 */
	void notify(Event e);

}
