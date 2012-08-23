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

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.MenuList;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
import ch.sebastienzurfluh.client.view.navigation.NavigationItem;
import ch.sebastienzurfluh.client.view.tilemenu.Tile.TileMode;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This widget uses several {@link Tile}s to create a menu.
 * @author Sebastien Zurfluh
 *
 */
public class TileMenu extends VerticalPanel implements MenuList {
	private String stylePrimaryName = "tileMenu";
	private FlowPanel tilePanel;
	private TreeSet<Tile> tileOrderList;
	private ModeSwapButton button;
	private PageRequestHandler pageRequestHandler;
	
	public TileMenu(String title, PageRequestHandler pageRequestHandler) {
		this.pageRequestHandler = pageRequestHandler;
		
		setStyleName(stylePrimaryName);
		
		HorizontalPanel firstLine = new HorizontalPanel();
		Label titleLabel = new Label(title);
		titleLabel.setStyleName(stylePrimaryName + "-" + "title");
		firstLine.add(titleLabel);
		
		button = new ModeSwapButton(stylePrimaryName, this);
		button.setStyleName(stylePrimaryName + "-" + "detailButton");
		
		firstLine.add(button);
		add(firstLine);
		
		tilePanel = new FlowPanel();
		tileOrderList = new TreeSet<Tile>(new Comparator<Tile>() {
			@Override
			public int compare(Tile o1, Tile o2) {
				return ((Integer) o1.getPriority()).compareTo(o2.getPriority());
			}
		});
		
		tilePanel.setStyleName(stylePrimaryName + "-" + "tileList");
		add(tilePanel);
	}
	
	public void addTile(MenuData menuData) {
		Tile tile = new Tile(menuData);
		tile.addClickHandler(pageRequestHandler);
		tile.setMode(currentMode);
		tileOrderList.add(tile);
		// TODO order the tiles in the menu according to their priority number
		tilePanel.add(tile);
	}
	
	public void clearTiles() {
		//TODO test that. It didn't work before... maybe use a new tilePanel.
		tilePanel.clear();
		tileOrderList.clear();
	}
	
	private TileMode currentMode = TileMode.ICON_ONLY;
	public void setMode(TileMode mode) {
		if (mode.equals(currentMode))
			return;
		System.out.println("TileMenu: mode change.");
		currentMode = mode;
		System.out.println("TileMenu: current mode: "+mode);
		if (mode.equals(TileMode.DETAILED)) {
			button.setState(TwoStatesImageButton.State.ONE);
		} else {
			button.setState(TwoStatesImageButton.State.TWO);
		}
		for (Tile tile : tileOrderList) {
			tile.setMode(mode);
		}
	}

	// we only initialise to avoid "if(!null)" on the first run.
	private Tile focusedItem = new Tile(MenuData.SUPER);
	public void setFocus(DataReference menuReference) {
		System.out.println("TileMenu: Focus: DataReference: " + menuReference.toString());
		
		focusedItem.setMenuFocus(false);
		// retrieve the menu in the list.
		int menuRank = 0;
		for (Tile menu : tileOrderList) {
			if (menu.getReference().equals(menuReference.getReferenceId())) {
				
				focusedItem = getTile(menuRank);
				
				System.out.println("TileMenu: Focus: Found the referenced tile " + focusedItem.getReference().toString());
				
				focusedItem.setMenuFocus(true);
				return;
			}
			menuRank++;
		}
	}
	
	/**
	 * @param number the rank of the widget to retrieve
	 * @return the {@code number}th widget or null if there's none at this position.
	 */
	public Tile getTile(int number) {
		int i = 0;
		for (Iterator<Tile> iterator = tileOrderList.iterator(); iterator.hasNext(); i++) {
			if (i == number)
				return  iterator.next();
		}
		return null;
	}
}
