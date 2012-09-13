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

package ch.sebastienzurfluh.client.control.eventbus;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.events.ResourceRequest;
import ch.sebastienzurfluh.client.model.Model;

/**
 * This object will handle the resource requests.
 * 
 * @author Sebastien Zurfluh
 *
 */
public class ResourceRequestEventHandler implements EventBusListener {
	EventBus eventBus;
	Model model;
	
	/**
	 * Just create the object, it will attach itself to the given event bus.
	 * @param eventBus
	 */
	public ResourceRequestEventHandler(EventBus eventBus, Model model) {
		this.eventBus = eventBus;
		this.model = model;
		
		eventBus.addListener(this);
		System.out.println("RESOURCE HANDLER CREATED");
	}

	@Override
	public EventType getEventType() {
		return EventType.RESOURCE_REQUEST;
	}

	@Override
	public void notify(Event e) {
		if(e instanceof ResourceRequest) {
			final ResourceRequest resourceRequest = (ResourceRequest) e;
			
			model.load(resourceRequest.getResourceReference());
		}
	}

}
