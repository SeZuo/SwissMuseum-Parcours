/*
 * Copyright 2012 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.Event.EventType;

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
