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

package ch.sebastienzurfluh.client.view.navigation;

import java.util.Collection;
import java.util.Observable;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.WidgetLoadedEvent;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.MenuWidget;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
import ch.sebastienzurfluh.client.view.supportwidgets.TextLink;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This widget uses several navigation bars.
 * 
 * It listens to PageChangeEvent to choose wich slider should be displayed. 
 * 
 * @author Sebastien Zurfluh
 */
public class NavigationWidget extends VerticalPanel implements MenuWidget {
	private Model model;

	private NavigationSlider pageSlider;
	
	private SimplePanel bookletLink;
	
	private EventBus pageRequestEventBus;
	
	private final static String STYLE_NAME = "navigationWidget"; 

	public NavigationWidget(
			EventBus pageChangeEventBus,
			PageRequestHandler pageRequestHandler,
			Model model) {
		this.model = model;
		this.pageRequestEventBus = pageChangeEventBus;

		initialise(pageRequestHandler);

		setDefaults();
	}

	private void initialise(PageRequestHandler pageRequestHandler) {
		bookletLink = new SimplePanel();
		bookletLink.setStyleName(STYLE_NAME + "-bookletLink");
		add(bookletLink);
		
		// needs to update when the group changes
		model.currentGroupMenuObservable.addObserver(this);
		
		pageSlider = new NavigationSlider("Pages", pageRequestHandler);
		add(pageSlider);
	}

	private void setDefaults() {
		bookletLink.setVisible(false);
		pageSlider.setVisible(false);
	}

	
		
				break;
			default:
				pageSlider.setFocus(data.getReference());
				break;
			}


			// Change layout according to the new page type.
			switch (pageChangeEvent.getPageType()) {
			case PAGE:
				pageSlider.setVisible(true);
				bookletLink.setVisible(true);
				break;
			case GROUP:
				pageSlider.setVisible(false);
				bookletLink.setVisible(false);
				break;
			default:
				break;
			}
		}
	}

	
	
	
	public void setFocus(DataReference menuReference) {
		assert menuReference.getType() == DataType.PAGE;
		
		pageSlider.setFocus(menuReference);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// Create the booklet link for later
		bookletLink.setWidget(
				new TextLink(
						pageRequestEventBus,
						data.getReference(),
						"Parcours: " + data.getMenuTitle()));
		bookletSlider.setFocus(data.getReference());
	}
}