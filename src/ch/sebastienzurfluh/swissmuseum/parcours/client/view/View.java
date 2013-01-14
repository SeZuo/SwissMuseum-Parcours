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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent.EventType;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.view.eventbushooks.ScrollToPanelOnEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator.BookletNavigator;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.groupnavigator.GroupNavigator;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

/**
 * Main View object.
 * 
 * This view lets the user browse the booklet catalogue.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class View extends SimplePanel {
	/**
	 * Create and attach the Parcours view to the RootPanel.
	 * 
	 * @param eventBus manages all the events for the view
	 * @param assertPageRequestEventHandlerExists this needs to be set before creating the view
	 * @param assertResourceRequestHandlerExists this handler will be needed to load resources
	 * @param model is of course used to access the data
	 */
	public View(
			EventBus eventBus,
			Model model,
			PageRequestEventHandler assertPageRequestEventHandlerExists,
			ResourceRequestEventHandler assertResourceRequestHandlerExists) {
		assert eventBus != null;
		assert model != null;
		assert assertPageRequestEventHandlerExists != null;
		assert assertResourceRequestHandlerExists != null;
		
		// Initialise the MGWT library (animations and transition effects).
		MGWT.applySettings(MGWTSettings.getAppSetting());
		

		// Load the styles in the right order. This method is used because MGWT keeps overwriting
		// my styles.
		MGWTStyle.getTheme().getMGWTClientBundle().getMainCss().ensureInjected();
		MGWTStyle.injectStyleSheet("SwissMuseumCore.css");
		MGWTStyle.injectStyleSheet("SwissMuseumParcours.css");
		
		setStyleName("mainPanel");

		AnimationHelper animationHelper = new AnimationHelper();
		RootPanel.get().add(animationHelper);
		
		
		PageRequestClickHandler pageRequestHandler = new PageRequestClickHandler(eventBus);

		
		BookletNavigator bookletNavigator = new BookletNavigator(eventBus, model);
		GroupNavigator groupNavigator =
				new GroupNavigator(eventBus, model, pageRequestHandler);
		
		
		animationHelper.goTo(groupNavigator, Animation.FADE);
		
		
		// Switch between BookletNavigator and GroupNavigator when needed.
		new WidgetSwitcher(model, groupNavigator, bookletNavigator, animationHelper);
		
		
//		// Create the return to the top button.
//		Image returnToTheTop = new Image("resources/images/buttons/go-top.png");
//		returnToTheTop.setStyleName("returnToTop");
//		returnToTheTop.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				ScrollToTheTop.getInstance().start();
//			}
//		});
//		RootPanel.get().add(returnToTheTop);
//		
//		footer = new FooterWidget();
//		RootPanel.get().add(footer);

		// Add some global functionalities with low priority
		ScrollToPanelOnEvent.addRule(eventBus, RootPanel.get(), EventType.PAGE_CHANGE_EVENT);
	}
}