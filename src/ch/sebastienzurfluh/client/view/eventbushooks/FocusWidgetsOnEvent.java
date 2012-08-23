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

package ch.sebastienzurfluh.client.view.eventbushooks;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;
import ch.sebastienzurfluh.client.control.eventbus.events.WidgetLoadedEvent;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;

/**
 * This class listens to the page changes in order to scroll
 * automatically to the right position on load.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class FocusWidgetsOnEvent implements EventBusListener {
	private DataReference dataReference;
	private EventType eventType;

	private FocusWidgetsOnEvent(EventBus eventBus, EventType eventType) {
		this.eventType = eventType;
		this.dataReference = MenuData.SUPER.getReference();
		
		eventBus.addListener(this);
		
		//TODO Get rid of this awful hack by modifying the event system to allow multiple event listening.
		// hack!
		this.eventType = EventType.PAGE_CHANGE_EVENT;
		eventBus.addListener(this);
		this.eventType = eventType;
		// end of hack!
	}
	
	@Override
	public EventType getEventType() {
		return eventType;
	}

	@Override
	public void notify(Event e) {
		if (e.getType() == EventType.WIDGET_LOADED_EVENT) {
			WidgetLoadedEvent event = (WidgetLoadedEvent) e;
			event.getWidget().setFocus(dataReference);
		} else if (e.getType() == EventType.PAGE_CHANGE_EVENT) {
			PageChangeEvent pageChangeEvent = ((PageChangeEvent) e);
			dataReference = pageChangeEvent.getData() == null ?
					MenuData.SUPER.getReference() : pageChangeEvent.getData().getReference();
		}
	}

	public static FocusWidgetsOnEvent addRule(EventBus eventBus, EventType eventType) {
		return new FocusWidgetsOnEvent(eventBus, eventType);
	}
}
