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
import ch.sebastienzurfluh.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.view.cms.EditWidget;
import ch.sebastienzurfluh.client.view.cms.TreeWidget;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This view lets the user browse the booklet catalogue.
 *
 * @author Sebastien Zurfluh
 */
public class CMSView extends VerticalPanel {
	// Shared handler for page requests
	private PageRequestClickHandler pageRequestHandler;
	private HorizontalPanel mainPanel;
	private TreeWidget pagePanel;
	private TreeWidget groupPanel;
	private EventBus eventBus;
	private Model model;
	private EditWidget page;
	private FooterWidget footer;
	
	
	

	public CMSView(final EventBus eventBus,
			PageRequestEventHandler pageRequestEventHandler,
			ResourceRequestEventHandler resourceRequestHandler,
			Model model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestEventHandler != null;
		
		this.eventBus = eventBus;
		this.model = model;
		
		pagePanel = new TreeWidget(DataType.PAGE, eventBus, model);
		groupPanel = new TreeWidget(DataType.GROUP, eventBus, model);

		// Setup main panel
		mainPanel = new HorizontalPanel();
		
		mainPanel.setStyleName("mainPanel");

		pageRequestHandler = new PageRequestClickHandler(eventBus);

		// create mainPanel before filling it
		add(mainPanel);
		add(new FooterWidget());
	}
	
	/**
	 * Fill main sections and add main sections to main panel.
	 * 
	 * Call this after the panel has been attached.
	 */
	public void afterAttached() {
		
	}
}
