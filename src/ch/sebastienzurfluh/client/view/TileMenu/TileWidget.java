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

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.TileMenu.Tile.TileMode;

import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This widget uses several {@link TileMenu}s to create a menu.
 * @author Sebastien Zurfluh
 *
 */
public class TileWidget extends VerticalPanel implements EventBusListener {
	private String stylePrimaryName = "tileWidget";
	private TileMenu bookletMenu;
	private TileMenu chapterMenu;
	private TileMenu pageMenu;
	private Model model;
	
	public TileWidget(EventBus pageChangeEventBus, Model model) {
		this.model = model;
		
		setStyleName(stylePrimaryName);
		
		pageChangeEventBus.addListener(this);
		
		// Create a new TileMenu for each Data Level. Booklet, Chapter, Page, Resource.
		bookletMenu = new TileMenu("Booklets");
		add(bookletMenu);
		chapterMenu = new TileMenu("Chapters");
		add(chapterMenu);
		pageMenu = new TileMenu("Pages");
		add(pageMenu);
		
		// Default settings
		chapterMenu.setVisible(false);
		pageMenu.setVisible(false);
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_EVENT;
	}

	@Override
	public void notify(Event e) {
		if(e instanceof PageChangeEvent) {
			PageChangeEvent pageChangeEvent = (PageChangeEvent) e;
			
			// Change layout according to the new page type.
			switch (pageChangeEvent.getPageType()) {
			case PAGE:
				pageMenu.setVisible(true);
				pageMenu.setMode(TileMode.ICON_ONLY);
				chapterMenu.setVisible(true);
				chapterMenu.setMode(TileMode.ICON_ONLY);
				bookletMenu.setMode(TileMode.ICON_ONLY);
				break;
			case CHAPTER:
				pageMenu.setVisible(false);
				chapterMenu.setVisible(true);
				bookletMenu.setMode(TileMode.ICON_ONLY);
				break;
			case SUPER:
				pageMenu.setVisible(false);
				chapterMenu.setVisible(false);
				bookletMenu.setMode(TileMode.DETAILED);
				break;
			case BOOKLET:
				pageMenu.setVisible(false);
				chapterMenu.setVisible(true);
				bookletMenu.setMode(TileMode.ICON_ONLY);
				break;
			default:
				break;
			}
			
			// Reload the tiles as necessary
			Data data = pageChangeEvent.getData();
			switch (pageChangeEvent.getPageType()) {
			case BOOKLET:
				// list the booklet's chapters
				reloadTiles(chapterMenu, data.getReference());
				break;
			case CHAPTER:
				// list the chapter's pages
				reloadTiles(pageMenu, data.getReference());
				break;
			default:
				break;
			}
		}
	}
	
	private void reloadTiles (TileMenu menu, DataReference parentReference) {
		menu.clearTiles();
		for (MenuData menuData : model.getSubMenus(parentReference.getType(), parentReference)) {
			menu.addTile(menuData.getSquareImgURL(), menuData.getTitle(), menuData.getDescription(), menuData.getPriorityNumber());
		}
	}
}
