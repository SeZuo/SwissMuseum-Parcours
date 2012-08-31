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

import java.util.Collection;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.MenuWidget;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This widget uses several {@link TileMenu}s to create a menu.
 * @author Sebastien Zurfluh
 *
 */
public class TileWidget extends VerticalPanel implements MenuWidget {
	private String stylePrimaryName = "tileWidget";
	private TileMenu tileMenu;
	private Model model;
	
	public TileWidget(EventBus pageChangeEventBus, PageRequestHandler pageRequestHandler, Model model) {
		this.model = model;
		
		setStyleName(stylePrimaryName);
		
		initialise(pageRequestHandler);
		
		tileMenu.setVisible(false);
		
		model.allPagesMenusInCurrentGroupObservable.subscribeObserver(this);
	}
	
	private void initialise(PageRequestHandler pageRequestHandler) {
		tileMenu = new TileMenu("Pages", pageRequestHandler);
		add(tileMenu);
	}
	
	public void setFocus(DataReference menuReference) {
		setFocus(menuReference);
	}

	@Override
	public void notifyObserver() {
		tileMenu.clearTiles();
		for (MenuData menuData : model.getAllPageMenusInCurrentGroup()) {
			tileMenu.addTile(menuData);
		}
	}
}