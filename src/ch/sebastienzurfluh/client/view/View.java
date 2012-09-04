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

package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.view.eventbushooks.ScrollToPanelOnEvent;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
import ch.sebastienzurfluh.client.view.navigation.NavigationWidget;
import ch.sebastienzurfluh.client.view.pagewidget.PageWidget;
import ch.sebastienzurfluh.client.view.tilemenu.TileWidget;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Facade for the view.
 *
 * @author Sebastien Zurfluh
 */
public class View extends SimplePanel {
	// Shared handler for page requests
	private PageRequestHandler pageRequestHandler;
	private VerticalPanel mainPanel;
	private EventBus eventBus;
	private Model model;
	

	public View(final EventBus eventBus, PageRequestEventHandler pageRequestEventHandler, ResourceRequestEventHandler resourceRequestHandler, final Model model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestEventHandler != null;
		
		this.eventBus = eventBus;
		this.model = model;

		// Setup main panel
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("mainPanel");

		pageRequestHandler = new PageRequestHandler(eventBus);

		// create mainPanel before filling it
		setWidget(mainPanel);
	}

	/**
	 * Fill main sections and add main sections to main panel.
	 * 
	 * Call init() after the panel has been attached.
	 */
	public void init() {
		NavigationWidget navigation = new NavigationWidget(eventBus, model);
		mainPanel.add(navigation);
		PageWidget page = new PageWidget(eventBus, model);
		mainPanel.add(page);
		TileWidget tileMenu = new TileWidget(eventBus, pageRequestHandler, model);
		mainPanel.add(tileMenu);
		FooterWidget footer = new FooterWidget();
		mainPanel.add(footer);

		// Add some global functionalities with low priority
		ScrollToPanelOnEvent.addRule(eventBus, page, EventType.PAGE_CHANGE_EVENT);
		
	}
}
