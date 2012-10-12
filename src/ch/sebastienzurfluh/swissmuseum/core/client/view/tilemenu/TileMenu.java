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

package ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu;

import java.util.LinkedList;

import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.MenuList;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile.TileMode;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlowPanel;


/**
 * This widget uses several {@link Tile}s to create a menu.
 * 
 * It has a detailed and an icon-only modes. Default is detailed.
 * 
 * @author Sebastien Zurfluh
 *
 */
public class TileMenu extends FlowPanel implements MenuList {
	private String stylePrimaryName = "tileMenu";
	private FlowPanel tilePanel;
	private LinkedList<Tile> tileOrderList;
	private ModeSwapButton button;
	private PageRequestClickHandler pageRequestHandler;
	
	public TileMenu(String title, PageRequestClickHandler pageRequestHandler) {
		this.pageRequestHandler = pageRequestHandler;
		
		setStyleName(stylePrimaryName);
		
		
		HorizontalPanel firstLine = new HorizontalPanel();
		
		button = new ModeSwapButton(this);
		button.setStyleName(stylePrimaryName + "-" + "detailButton");
		firstLine.add(button);
		
		Label titleLabel = new Label(title);
		titleLabel.setStyleName(stylePrimaryName + "-" + "title");
		firstLine.add(titleLabel);
				
		add(firstLine);
		
		
		tilePanel = new FlowPanel();
		tileOrderList = new LinkedList<Tile>();
		
		tilePanel.setStyleName(stylePrimaryName + "-" + "tileList");
		add(tilePanel);
	}
	
	public void addTile(MenuData menuData) {
		Tile tile = new Tile(menuData);
		tile.addClickHandler(pageRequestHandler);
		tile.setMode(currentMode);
		System.out.println("TileMenu: addTile: adding tile with title: "+tile.getTitle());
		tileOrderList.add(tile);
		System.out.println("TileMenu: addTile: retrieving tile in list:"+tileOrderList.get(tileOrderList.size()-1));
		System.out.println("TileMenu: addTile: Size after add is "+tileOrderList.size());
		
		tilePanel.add(tile);
	}
	
	public void clearTiles() {
		//TODO test that. It didn't work before... maybe use a new tilePanel.
		tilePanel.clear();
		tileOrderList.clear();
	}
	
	private TileMode currentMode = TileMode.DETAILED;
	public void setMode(TileMode mode) {
		if (mode.equals(currentMode))
			return;
		System.out.println("TileMenu: mode change.");
		currentMode = mode;
		System.out.println("TileMenu: current mode: "+mode);
		if (mode.equals(TileMode.DETAILED)) {
			button.setState(ModeSwapButton.State.DETAILED);
		} else {
			button.setState(ModeSwapButton.State.ICON_ONLY);
		}
		for (Tile tile : tileOrderList) {
			tile.setMode(mode);
		}
	}
}
