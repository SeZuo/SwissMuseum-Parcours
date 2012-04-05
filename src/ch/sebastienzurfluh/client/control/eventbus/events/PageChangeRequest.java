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

package ch.sebastienzurfluh.client.control.eventbus.events;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.model.structure.DataReference;

/**
 * This event is fired after a page has been changed.
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageChangeRequest extends Event {
	DataReference pageId;
	
	public PageChangeRequest(DataReference pageId) {
		this.pageId = pageId;
	}

	public DataReference getPageId() {
		return pageId;
	}

	@Override
	public EventType getType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}
}
