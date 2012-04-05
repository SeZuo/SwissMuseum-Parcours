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

package ch.sebastienzurfluh.client.view.Navigation;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This widget uses several navigation bars.
 * 
 * It listens to PageChangeEvent to choose wich slider should be displayed. 
 * 
 * @author Sebastien Zurfluh
 *
 */
public class NavigationWidget extends VerticalPanel implements EventBusListener {
	private EventBus pageChangeEventBus;
	
	public NavigationWidget(EventBus pageChangeEventBus) {
		this.pageChangeEventBus = pageChangeEventBus;
		
		pageChangeEventBus.addListener(this);
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}

	@Override
	public void notify(Event e) {
		// TODO Auto-generated method stub
		
	}

}
