/*
 * Copyright 2012-2013 Sebastien Zurfluh
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

package ch.sebastienzurfluh.swissmuseum.parcours.client.control;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.IOConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.View;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;

/**
 * This class creates the web app and adds it to the given panel.
 * @author Sebastien Zurfluh
 */
public class AppPresenter {
	private Panel parent;
	private EventBus eventBus;
	private Model model;

	public AppPresenter(Panel parent) {
		this.parent = parent;
	}
	
	/**
	 * Does what's needed before the app starts.
	 * Loads and display the application in the panel defined at construction.
	 * 
	 * This method is asynchronuous.
	 */
	public void start() {
		PhoneGapHandle.getInstance().addHandler(new PhoneGapAvailableHandler() {
			@Override
			public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
				System.out.println("AppPresenter: " + "PhoneGap available");
				startAfterPhoneGapLoad();
			}
		});
		
		PhoneGapHandle.getInstance().addHandler(new PhoneGapTimeoutHandler() {
			
			@Override
			public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
				// we do the same if phonegap doesn't work
				System.out.println("AppPresenter: " + "PhoneGap NOT available");
				startAfterPhoneGapLoad();
			}
		});
		
		PhoneGapHandle.getInstance().initializePhoneGap();
	}

	private void startAfterPhoneGapLoad() {
		eventBus = new EventBus();

		AsyncConnectorFactory.createConnector(new ParcoursConfig(), new AsyncCallback<IOConnector>() {
			
			@Override
			public void onSuccess(IOConnector result) {
				model = new Model(result);
				
				PageRequestEventHandler pageRequestHandler = new PageRequestEventHandler(eventBus, model);
				ResourceRequestEventHandler resourceRequestHandler = new ResourceRequestEventHandler(eventBus, model);


				View view = new View(eventBus, model, pageRequestHandler, resourceRequestHandler);

				parent.add(view);

				// Start the app
				eventBus.fireEvent(new PageChangeRequest(DataReference.SUPER));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("The app failed to initialise.");
			}
		});
		
		


		
	}
}