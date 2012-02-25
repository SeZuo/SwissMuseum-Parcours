package ch.sebastienzurfluh.client.control.eventbus;

import java.util.HashMap;
import java.util.LinkedList;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;

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
