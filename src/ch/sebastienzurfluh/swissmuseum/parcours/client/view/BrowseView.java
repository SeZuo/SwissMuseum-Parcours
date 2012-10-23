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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent.EventType;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.view.AnimatedMainPanel;
import ch.sebastienzurfluh.swissmuseum.core.client.view.FooterWidget;
import ch.sebastienzurfluh.swissmuseum.core.client.view.animations.ScrollToTheTop;
import ch.sebastienzurfluh.swissmuseum.core.client.view.eventbushooks.ScrollToPanelOnEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.navigation.NavigationWidget;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.PageWidget;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.TileWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * This view lets the user browse the booklet catalogue.
 *
 * @author Sebastien Zurfluh
 */
public class BrowseView extends SimplePanel {
	// Shared handler for page requests
	private PageRequestClickHandler pageRequestHandler;
	private AnimatedMainPanel mainPanel;
	private FlowPanel pagePanel;
	private FlowPanel groupPanel;
	private EventBus eventBus;
	private Model model;
	private TileWidget tileMenu;
	private PageWidget page;
	private NavigationWidget navigation;
	private FooterWidget footer;
	
	

	public BrowseView(final EventBus eventBus,
			PageRequestEventHandler pageRequestEventHandler,
			ResourceRequestEventHandler resourceRequestHandler,
			Model model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestEventHandler != null;
		assert resourceRequestHandler != null;
		
		this.eventBus = eventBus;
		this.model = model;
		
		pagePanel = new FlowPanel();
		groupPanel = new FlowPanel();

		// Setup main panel
		mainPanel = new AnimatedMainPanel(model, groupPanel, pagePanel);
		
		mainPanel.setStyleName("mainPanel");
		
		setStyleName("mainPanel");
		

		pageRequestHandler = new PageRequestClickHandler(eventBus);

		// create mainPanel before filling it
		setWidget(mainPanel);
	}
	
	/**
	 * Fill main sections and add main sections to main panel according to layout
	 * 
	 * Call this after the panel has been attached.
	 */
	public void afterAttached() {
		navigation = new NavigationWidget(eventBus, model);
		pagePanel.add(navigation);
		
		page = new PageWidget(eventBus, model);
		pagePanel.add(page);
		
		
		Image headerImage = new Image("resources/images/fioritures/parcours_entete_2.png");
		headerImage.setStyleName("header");
		groupPanel.add(headerImage);
		
		tileMenu = new TileWidget(eventBus, pageRequestHandler, model);
		groupPanel.add(tileMenu);
		
		// Create the return to the top button.
		Image returnToTheTop = new Image("resources/images/buttons/go-top.png");
		returnToTheTop.setStyleName("returnToTop");
		returnToTheTop.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ScrollToTheTop.getInstance().start();
			}
		});
		groupPanel.add(returnToTheTop);
		
		footer = new FooterWidget();
		groupPanel.add(footer);

		// Add some global functionalities with low priority
		ScrollToPanelOnEvent.addRule(eventBus, page, EventType.PAGE_CHANGE_EVENT);
	}
}
