/*
 * Copyright 2012-2013 Sebastien Zurfluh
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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view.groupnavigator;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.TileMenu;

import com.google.gwt.user.client.ui.FlowPanel;

public class LoadOnDemandTileWidget extends FlowPanel implements Observer {
	private String stylePrimaryName = "tileWidget";
	private TileMenu tileMenu;
	private Model model;
	
	
	public LoadOnDemandTileWidget(
			EventBus pageChangeEventBus,
			PageRequestClickHandler pageRequestHandler,
			Model model) {
		this.model = model;
		
		setStyleName(stylePrimaryName);
		
		tileMenu = new TileMenu("Choisissez votre parcours", pageRequestHandler);
		add(tileMenu);
		
		if(model.getAllGroupMenus() != null && !model.getAllGroupMenus().isEmpty())
			load();
		else
			postponeLoad();
	}
	
	/**
	 * Load data from current page in memory (model).
	 */
	public void load() {
		tileMenu.clearTiles();
		for (MenuData menuData : model.getAllGroupMenus()) {
			tileMenu.addTile(menuData);
		}
	}
	
	/**
	 * Postpone the load until the data is available.
	 */
	public void postponeLoad() {
		model.allGroupsMenusChangesObservable.subscribeObserver(this);
	}


	@Override
	public void notifyObserver(Observable source) {
		if(model.getAllGroupMenus() != null && !model.getAllGroupMenus().isEmpty()) {
			load();
			
			// this is the main difference with the dynamic TileWidget.
			// the page will only load once.
			model.allGroupsMenusChangesObservable.unsubscribeObserver(this);
		}
	}
}