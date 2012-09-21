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

package ch.sebastienzurfluh.client.control;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.view.View;

import com.google.gwt.user.client.ui.Panel;

/**
 * This class creates the web app and adds it to the given panel.
 * @author Sebastien Zurfluh
 *
 */
public class AppPresenter {
	private Panel parent;
	private EventBus eventBus;
	private Model model;

	public AppPresenter(Panel parent) {
		this.parent = parent;
	}

	/**
	 * Loads and display the application in the panel defined at construction.
	 */
	public void start() {
		eventBus = new EventBus();

		model = ModelFactory.createModel();

		
		PageRequestEventHandler pageRequestHandler = new PageRequestEventHandler(eventBus, model);
		ResourceRequestEventHandler resourceRequestHandler = new ResourceRequestEventHandler(eventBus, model);
		

		View view = new View(eventBus, pageRequestHandler, resourceRequestHandler, model);

		parent.add(view);
		
		model.setViewMode(Config.START_VIEW_MODE);
		
		// Start the app
		eventBus.fireEvent(new PageChangeRequest(DataReference.SUPER));
	}
}