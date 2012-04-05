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

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestHandler;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.view.Navigation.NavigationWidget;
import ch.sebastienzurfluh.client.view.TileMenu.TileWidget;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Facade for the view.
 *
 * @author Sebastien Zurfluh
 */
public class View extends SimplePanel {
	public View(EventBus eventBus, PageRequestHandler pageRequestHandler, Model model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestHandler != null;
		
		
		// Setup main panel
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setWidth("100%");
		mainPanel.setStyleName("mainPanel");
		
		
		// Create main sections
		NavigationWidget navigation = new NavigationWidget(eventBus);
		HierarchyWidget hierarchy = new HierarchyWidget(eventBus);
		PageWidget page = new PageWidget(eventBus);
		TileWidget tileMenu = new TileWidget(eventBus, model);
		FooterWidget footer = new FooterWidget();
		
		// Add main sections to main panel
		mainPanel.add(navigation);
		mainPanel.add(hierarchy);
		mainPanel.add(page);
		mainPanel.add(tileMenu);
		mainPanel.add(footer);
		
		setWidget(mainPanel);
	}
}
