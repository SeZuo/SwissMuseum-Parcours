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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view.supportwidgets;

import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.structure.DataReference;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;

public class TextLink extends Label implements ClickHandler {
	private EventBus eventBus;
	private DataReference reference;

	public TextLink(EventBus eventBus, DataReference linkReference, String label) {
		this.eventBus = eventBus;
		this.reference = linkReference;
		
		this.setStyleName("textLink");
		
		this.setText(label);
		
		this.addClickHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		eventBus.fireEvent(new PageChangeRequest(reference));
	}
}
