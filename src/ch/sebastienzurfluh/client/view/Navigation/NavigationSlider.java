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

package ch.sebastienzurfluh.client.view.Navigation;

import java.util.HashMap;

import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.MenuInterface.MenuList;
import ch.sebastienzurfluh.client.view.MenuInterface.PageRequestHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * This widget gives a way to navigate (next/previous) between pages. 
 * @author Sebastien Zurfluh
 */
public class NavigationSlider extends SimplePanel implements MenuList {
	private HorizontalPanel tilePanel;
	private HashMap<Integer, NavigationItem> tileOrderList;
	private PageRequestHandler pageRequestHandler;
	
	public NavigationSlider(String string, PageRequestHandler pageRequestHandler) {
		this.pageRequestHandler = pageRequestHandler;
		
		setStyleName("navigationSlider");
		
		tilePanel = new HorizontalPanel();
		tileOrderList = new HashMap<Integer, NavigationItem>();
		
		setWidget(tilePanel);
	}
	
	//TODO find another way to do that.
	private final static Integer FIRST_TILE_PRIORITY_NUMBER = -1; 
	public void addFirstTile(MenuData menuData) {
		addTileOnPriority(menuData, FIRST_TILE_PRIORITY_NUMBER);
	}
	
	public void addTile(MenuData menuData) {
		addTileOnPriority(menuData, menuData.getPriorityNumber());
	}
	
	private void addTileOnPriority(MenuData menuData, Integer priority) {
		NavigationItem tile = new NavigationItem(menuData);
		tile.addClickHandler(pageRequestHandler);
		tileOrderList.put(priority, tile);
		// TODO order the tiles in the menu according to their priority number
		tilePanel.add(tile);
	}
	
	public void clearTiles() {
		//TODO test that. It didn't work before... maybe use a new tilePanel.
		tilePanel.clear();
		tileOrderList.clear();
	}

}
