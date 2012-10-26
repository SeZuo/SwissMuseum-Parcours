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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view.navigation;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.MenuButton;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.PageWidget;

/**
 * This is an item of the {@link NavigationSlider}.
 * @author Sebastien Zurfluh
 *
 */
public class NavigationItem extends FlowPanel implements MenuButton {
	private Image tileImage;
	private DataReference menuReference;
	private String stylePrimaryName = "navigationItem";
	private int priority;

	/**
	 * @param menuData
	 */
	public NavigationItem(MenuData menuData) {
		this.menuReference = menuData.getReference();
		this.priority = menuData.getPriorityNumber();

		tileImage = new Image(menuData.getRectangleImgURL());
		tileImage.setStyleName(stylePrimaryName + "-" + "image");
		tileImage.setAltText(menuData.getTitle());

		add(tileImage);
	}

	public DataReference getReference() {
		return menuReference;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return null;
	}
}


