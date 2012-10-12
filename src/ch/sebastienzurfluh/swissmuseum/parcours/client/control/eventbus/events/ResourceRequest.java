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

package ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.events;

import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.Event;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.structure.DataReference;

/**
 * This event is fired when a resource is required.
 *
 * @author Sebastien Zurfluh
 *
 */
public class ResourceRequest extends Event {
	private DataReference requestReference;
	
	/**
	 * Create an event to request a resource.
	 * @param requestReference the unique reference to the wanted resource.
	 */
	public ResourceRequest(DataReference requestReference) {
		this.requestReference = requestReference;
	}

	/**
	 * @return the resource's unique id.
	 */
	public DataReference getResourceReference() {
		return requestReference;
	}

	@Override
	public EventType getType() {
		return EventType.RESOURCE_REQUEST;
	}
}
