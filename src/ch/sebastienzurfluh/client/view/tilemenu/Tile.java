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

package ch.sebastienzurfluh.client.view.tilemenu;

import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.MenuButton;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * This widget is a square icon menu.
 * 
 * Tile has two modes: "icon only mode" and "detailed mode".
 *
 * @author Sebastien Zurfluh
 *
 */
public class Tile extends FocusPanel implements MenuButton {
	private Image tileImage;
	private FlowPanel details;
	private String stylePrimaryName = "tile";
	
	private HorizontalPanel tilePanel = new HorizontalPanel();
	private DataReference menuReference;
	private int priority;
	
	public Tile(MenuData menuData) {
		this.menuReference = menuData.getReference();
		this.priority = menuData.getPriorityNumber();

		tileImage = new Image(menuData.getSquareImgURL());
		tileImage.setStyleName(stylePrimaryName + "-" + "tileImage");
		tileImage.setAltText(menuData.getTitle());
		tilePanel.add(tileImage);
		
		details = new FlowPanel();
		details.setStyleName(stylePrimaryName+ "-" + "tileDetails"); 
		Label titleLabel = new Label(menuData.getTitle());
		titleLabel.setStyleName(stylePrimaryName + "-" + "tileTitle");
		details.add(titleLabel);
		Label descriptionLabel = new Label(menuData.getDescription());
		descriptionLabel.setStyleName(stylePrimaryName + "-" + "tileDescription");
		details.add(descriptionLabel);
		tilePanel.add(details);
		
		setWidget(tilePanel);
		
		// Choose default
		setIconOnlyMode();
	}
	
	public Tile(MenuData menuData, TileMode mode) {
		this(menuData);
		
		setMode(mode);
	}
	
	private static String iconOnlyStyleDependentName = TileMode.ICON_ONLY.toString();
	private static String detailedStyleDependentName = TileMode.DETAILED.toString();

	private void setIconOnlyMode() {
		System.out.println("Tile: " + this.toString() + " set icon mode");
		setStyleName(stylePrimaryName + "-" + iconOnlyStyleDependentName);
		
		details.setVisible(false);
	}
	
	private void setDetailedMode() {
		System.out.println("Tile: " + this.toString() + " set detailed mode");
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

	public DataReference getReference() {
		return menuReference;
	}
	
	public int getPriority() {
		return this.priority;
	}

	public void setMenuFocus(boolean b) {
		if (b) {
			tileImage.setStyleName(tileImage.getStyleName() + "-focus");
		} else {
			tileImage.setStyleName(tileImage.getStyleName().replace("-focus", ""));
		}
	}
	
	/**
	 * A human-readable representation of the {@code Tile}
	 */
	@Override
	public String toString() {
		return "{Tile: "+menuReference.toString()+"}";
	}
}
