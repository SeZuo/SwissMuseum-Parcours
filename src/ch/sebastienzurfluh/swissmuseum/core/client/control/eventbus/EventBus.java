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

import java.util.HashMap;
import java.util.LinkedList;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.Event.EventType;

/**
 * Manages to notify the listeners of a specific {@link Event}, when this {@link Event} is fired.
 *
 * @author Sebastien Zurfluh
 *
 */
public class EventBus {
	HashMap<EventType, LinkedList<EventBusListener>> listeners = 
			new HashMap<EventType, LinkedList<EventBusListener>>(EventType.values().length);
	
	public EventBus() {
		for (EventType eventType : EventType.values()) {
			listeners.put(eventType, new LinkedList<EventBusListener>());
		}
	}
    
	public void addListener(EventBusListener listener) {
		listeners.get(listener.getEventType()).add(listener);
	}
    
    public void fireEvent(Event e) {
    	for (EventBusListener listener : listeners.get(e.getType())) {
			listener.notify(e);
		}
    }

	public boolean hasListener(EventBusListener listener) {
		return listeners.containsKey(listener.getEventType()) ?
				listeners.get(listener.getEventType()).contains(listener)
				: false;
	}
}
