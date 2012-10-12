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
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;

/**
 * This object will handle the page change requests.
 * @author Sebastien Zurfluh
 *
 */
public class PageRequestEventHandler implements EventBusListener {
	EventBus eventBus;
	Model model;
	
	/**
	 * Just create the object, it will attach itself to the given event bus.
	 * @param eventBus
	 */
	public PageRequestEventHandler(EventBus eventBus, Model model) {
		this.eventBus = eventBus;
		this.model = model;
		
		eventBus.addListener(this);
		System.out.println("PAGE HANDLER CREATED");
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}

	private DataReference cachedReference = null;
	@Override
	public void notify(Event e) {
		if(e instanceof PageChangeRequest) {
			final PageChangeRequest pageChangeRequest = (PageChangeRequest) e;
			
			
			//TODO Check if the requested page is OK.
			
			// Abort in case the page is already loaded.
			if (pageChangeRequest.getPageReference().equals(cachedReference))
				return;
			cachedReference = pageChangeRequest.getPageReference();
			
			
			if(pageChangeRequest.isForeignPageChangeRequest())
				model.loadForeignPage(pageChangeRequest.getPageReference());
			else
				model.load(pageChangeRequest.getPageReference());
		}
	}

}
