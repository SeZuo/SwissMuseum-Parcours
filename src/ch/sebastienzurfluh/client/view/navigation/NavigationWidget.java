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

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.view.menuinterface.MenuWidget;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
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
	
	private final static String STYLE_NAME = "navigationWidget"; 

	public NavigationWidget(
			EventBus pageChangeEventBus,
			PageRequestHandler pageRequestHandler,
			Model model) {
		this.model = model;

		setStyleName(STYLE_NAME);
		
		initialise(pageRequestHandler);
	}

	private void initialise(PageRequestHandler pageRequestHandler) {
		// needs to update when the group changes
		model.allPagesMenusInCurrentGroupObservable.subscribeObserver(this);
		
		pageSlider = new NavigationSlider("Pages", pageRequestHandler);
		add(pageSlider);
	}
	
	public void setFocus(DataReference menuReference) {
		assert menuReference.getType() == DataType.PAGE;
		
		pageSlider.setFocus(menuReference);
	}

	@Override
	public void notifyObserver() {
		if(model.getAllPageMenusInCurrentGroup().isEmpty()) {
			setVisible(false);
			System.out.println("NavigationWidget told to hide and do nothing.");
		} else {
			System.out.println("NavigationWidget told to show up.");
			setVisible(true);
			pageSlider.reloadTiles(model.getAllPageMenusInCurrentGroup());
			pageSlider.setFocus(model.getCurrentPageData().getReference());
		}
	}
}