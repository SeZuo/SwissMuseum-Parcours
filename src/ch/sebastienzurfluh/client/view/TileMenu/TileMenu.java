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

import ch.sebastienzurfluh.client.view.TileMenu.Tile.TileMode;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * This widget uses several {@link Tile}s to create a menu.
 * @author Sebastien Zurfluh
 *
 */
public class TileMenu extends VerticalPanel {
	private String stylePrimaryName = "tileMenu";
	private FlowPanel tilePanel;
	
	public TileMenu(String title) {
		setStyleName(stylePrimaryName);
		
		HorizontalPanel firstLine = new HorizontalPanel();
		Label titleLabel = new Label(title);
		titleLabel.setStyleName(stylePrimaryName + "-" + "title");
		firstLine.add(titleLabel);
		Label button = new Label("details button");
		firstLine.add(button);
		add(firstLine);
		
		tilePanel = new FlowPanel();
		tilePanel.setStyleName(stylePrimaryName + "-" + "tileList");
		add(tilePanel);
	}
	
	public void addTile(String squareImgURL, String title, String description, int priorityNumber) {
		tilePanel.insert(new Tile(squareImgURL, title, description), priorityNumber);
	}
	
	public void clearTiles() {
		//TODO test that. It didn't work before... maybe use a new tilePanel.
		tilePanel.clear();
	}
	
	public void setMode(TileMode mode) {
		for (Widget widget : tilePanel) {
			if (widget instanceof Tile) {
				((Tile)widget).setMode(mode);
			}
		}
	}

}
