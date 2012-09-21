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
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.view.menuinterface.MenuWidget;
import com.google.gwt.user.client.ui.FlowPanel;
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
			Model model) {
		this.model = model;

		setStyleName(STYLE_NAME);
		
		// needs to update when the group changes
		model.allPagesMenusInCurrentGroupObservable.subscribeObserver(this);
		
		pageSlider = new NavigationSlider("Pages", pageChangeEventBus, model);
		add(pageSlider);
	}

	@Override
	public void notifyObserver(Observable source) {
		if(model.getAllPageMenusInCurrentGroup().isEmpty()) {
			setVisible(false);
		} else {
			setVisible(true);
			pageSlider.reloadTiles(model.getAllPageMenusInCurrentGroup());
			pageSlider.setFocusWidget(model.getCurrentPageData().getReference());
		}
	}
}