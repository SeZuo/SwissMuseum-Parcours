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
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.view.menuinterface.MenuWidget;

/**
 * This event is fired when a page change has been approved.
 *
 * @author Sebastien Zurfluh
 *
 */
public class WidgetLoadedEvent extends Event {
	private MenuWidget widget;
	
	/**
	 * This event is used to notify that something finished loading.
	 * For now we only notify menus.
	 * It is used by autonomous widgets or part of the code to
	 * adapt to the change.
	 * @param pageType
	 * @param data
	 */
	public WidgetLoadedEvent(MenuWidget menu) {
		this.widget = menu;
	}

	@Override
	public EventType getType() {
		return EventType.WIDGET_LOADED_EVENT;
	}
	
	/**
	 * @return the loaded widget.
	 */
	public MenuWidget getWidget() {
		return widget;
	}
}
