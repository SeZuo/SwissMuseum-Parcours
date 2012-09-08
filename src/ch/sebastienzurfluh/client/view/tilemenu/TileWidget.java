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

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.view.menuinterface.MenuWidget;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestClickHandler;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This widget uses {@link TileMenu}s to create a menu.
 * @author Sebastien Zurfluh
 *
 */
public class TileWidget extends VerticalPanel implements MenuWidget {
	private String stylePrimaryName = "tileWidget";
	private TileMenu tileMenu;
	private Model model;
	
	public TileWidget(EventBus pageChangeEventBus, PageRequestClickHandler pageRequestHandler, Model model) {
		this.model = model;
		
		setStyleName(stylePrimaryName);
		
		initialise(pageRequestHandler);
		
		model.allGroupsMenusChangesObservable.subscribeObserver(this);
		model.currentPageDataObservable.subscribeObserver(this);
	}
	
	private void initialise(PageRequestClickHandler pageRequestHandler) {
		tileMenu = new TileMenu("Choisissez votre parcours", pageRequestHandler);
		add(tileMenu);
	}

	@Override
	public void notifyObserver(Observable source) {
		if (source.equals(model.currentPageDataObservable)) {
//			if(model.getCurrentPageData().equals(Data.NONE)) {
//				setVisible(true);
//			} else {
//				setVisible(false);
//			}
		} else if (source.equals(model.allGroupsMenusChangesObservable)) {
			tileMenu.clearTiles();
			for (MenuData menuData : model.getAllGroupMenus()) {
				tileMenu.addTile(menuData);
			}
		}
	}
}