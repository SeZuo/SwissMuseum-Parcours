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

package ch.sebastienzurfluh.client.view.TileMenu;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This widget is a square icon menu.
 * 
 * Tile has two modes: "icon only mode" and "detailed mode".
 *
 * @author Sebastien Zurfluh
 *
 */
public class Tile extends FocusPanel implements MouseDownHandler, MouseUpHandler {
	private Image tileImage;
	private VerticalPanel details;
	private String stylePrimaryName = "tile";
	
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private EventBus eventBus;
	private DataReference menuReference;
	
	Tile(MenuData menuData, EventBus eventBus) {
		this.eventBus = eventBus;
		this.menuReference = menuData.getReference();

		tileImage = new Image(menuData.getSquareImgURL());
		tileImage.setStyleName(stylePrimaryName + "-" + "tileImage");
		tileImage.setAltText(menuData.getTitle());
		mainPanel.add(tileImage);
		
		details = new VerticalPanel();
		details.setStyleName(stylePrimaryName+ "-" + "tileDetails"); 
		Label titleLabel = new Label(menuData.getTitle());
		titleLabel.setStyleName(stylePrimaryName + "-" + "tileTitle");
		details.add(titleLabel);
		Label descriptionLabel = new Label(menuData.getDescription());
		descriptionLabel.setStyleName(stylePrimaryName + "-" + "tileDescription");
		details.add(descriptionLabel);
		mainPanel.add(details);
		
		setWidget(mainPanel);
		
		// Choose default
		setIconOnlyMode();
	}
	
	private static String iconOnlyStyleDependentName = TileMode.ICON_ONLY.toString();
	private static String detailedStyleDependentName = TileMode.DETAILED.toString();

	private void setIconOnlyMode() {
		System.out.println("Tile: set icon mode");
		setStyleName(stylePrimaryName + "-" + iconOnlyStyleDependentName);
		
		details.setVisible(false);
	}
	
	private void setDetailedMode() {
		System.out.println("Tile: set detailed mode");
		setStyleName(stylePrimaryName + "-" + detailedStyleDependentName);
		
		details.setVisible(true);
	}
	
	public void setMode(TileMode mode) {
		switch (mode) {
		case ICON_ONLY:
			setIconOnlyMode();
			break;
		case DETAILED:
			setDetailedMode();
			break;
		default:
			break;
		}
	}
	
	public enum TileMode {
		ICON_ONLY("iconOnly"), DETAILED("detailed");
		
		private TileMode(String name) {
			this.name = name;
		}
		private final String name;
		public String toString() {
			return name;
		}
	}
	
	private String mouseDownStyleNameExtension =  "-mouseDown"; 
	@Override
	public void onMouseUp(MouseUpEvent event) {
		setStyleName(getStyleName().replace(mouseDownStyleNameExtension, ""));
		
		System.out.println("Firing page change event.");
		eventBus.fireEvent(new PageChangeRequest(this.getReference()));
	}

	private DataReference getReference() {
		return menuReference;
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		setStyleName(getStyleName() + mouseDownStyleNameExtension);
	}
}
