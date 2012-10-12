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
 * This event is fired after a page has been changed.
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageChangeRequest extends Event {
	private DataReference pageId;
	private boolean isforeignPageChangeRequest = false;
	
	/**
	 * Create an event to notify of a page change request.
	 * @param pageId the unique reference to the wanted page.
	 */
	public PageChangeRequest(DataReference pageId) {
		this.pageId = pageId;
	}
	
	/**
	 * Create an event to notify of a page change request.
	 * @param pageId the unique reference to the wanted page.
	 * @param foreignPage set to true if the requested page is foreign to the context
	 */
	public PageChangeRequest(DataReference pageId, boolean foreignPage) {
		this.pageId = pageId;
		this.isforeignPageChangeRequest = foreignPage;
	}

	/**
	 * @return the page's unique id.
	 */
	public DataReference getPageReference() {
		return pageId;
	}
	
	/**
	 * @return true if the requested page is foreign to the context
	 */
	public boolean isForeignPageChangeRequest() {
		return isforeignPageChangeRequest;
	}

	@Override
	public EventType getType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}
}
