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

package ch.sebastienzurfluh.client.view.navigation;

import java.util.Collection;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This widget uses several navigation bars.
 * 
 * It listens to PageChangeEvent to choose wich slider should be displayed. 
 * 
 * @author Sebastien Zurfluh
 *
 */
public class NavigationWidget extends VerticalPanel implements EventBusListener {
	private Model model;

	private NavigationSlider bookletSlider;
	private NavigationSlider chapterSlider;
	private NavigationSlider pageSlider;

	public NavigationWidget(EventBus pageChangeEventBus, PageRequestHandler pageRequestHandler, Model model) {
		this.model = model;

		initialise(pageRequestHandler);

		setDefaults();

		pageChangeEventBus.addListener(this);
	}

	private void initialise(PageRequestHandler pageRequestHandler) {
		bookletSlider = new NavigationSlider("Booklets", pageRequestHandler);
		add(bookletSlider);
		chapterSlider = new NavigationSlider("Chapters", pageRequestHandler);
		add(chapterSlider);
		pageSlider = new NavigationSlider("Pages", pageRequestHandler);
		add(pageSlider);
	}

	private void setDefaults() {
		bookletSlider.setVisible(false);
		chapterSlider.setVisible(false);
		pageSlider.setVisible(false);
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_EVENT;
	}

	@Override
	public void notify(Event e) {
		if(e instanceof PageChangeEvent) {
			PageChangeEvent pageChangeEvent = (PageChangeEvent) e;

			// Reload the tiles as necessary
			Data data = pageChangeEvent.getData();
			switch (pageChangeEvent.getPageType()) {
			case SUPER:
				reloadTiles(bookletSlider, model.getMenus(DataType.BOOKLET));
				break;
			case BOOKLET:
				// list the booklet's chapters
				reloadTilesWithParentFirst(chapterSlider, data.getReference());
				break;
			case CHAPTER:
				// list the chapter's pages
				reloadTilesWithParentFirst(pageSlider, data.getReference());
				break;
			default:
				break;
			}


			// Change layout according to the new page type.
			switch (pageChangeEvent.getPageType()) {
			case PAGE:
				pageSlider.setVisible(true);
				chapterSlider.setVisible(true);
				bookletSlider.setVisible(true);
				break;
			case CHAPTER:
				pageSlider.setVisible(false);
				chapterSlider.setVisible(true);
				bookletSlider.setVisible(true);
				break;
			case BOOKLET:
				pageSlider.setVisible(false);
				chapterSlider.setVisible(false);
				bookletSlider.setVisible(true);
				break;
			case SUPER:
				pageSlider.setVisible(false);
				chapterSlider.setVisible(false);
				bookletSlider.setVisible(false);
				break;
			default:
				break;
			}
		}
	}

	private void reloadTiles (NavigationSlider menu, Collection<MenuData> menus) {
		menu.clearTiles();
		for (MenuData menuData : menus) {
			menu.addTile(menuData);
		}
	}

	private void reloadTilesWithParentFirst(NavigationSlider menu, DataReference parentReference) {
		menu.clearTiles();
		menu.addFirstTile(model.getAssociatedData(parentReference).getMenu());

		for (MenuData menuData : model.getSubMenus(parentReference.getType(), parentReference)) {
			menu.addTile(menuData);
		}
	}
}



















