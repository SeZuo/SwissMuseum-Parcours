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

import ch.sebastienzurfluh.client.control.ModelAsyncPlug;
import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.WidgetLoadedEvent;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.MenuWidget;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
import ch.sebastienzurfluh.client.view.supportwidgets.TextLink;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This widget uses several navigation bars.
 * 
 * It listens to PageChangeEvent to choose wich slider should be displayed. 
 * 
 * @author Sebastien Zurfluh
 */
public class NavigationWidget extends VerticalPanel implements MenuWidget {
	private Model model;

	private NavigationSlider bookletSlider;
	private NavigationSlider chapterSlider;
	private NavigationSlider pageSlider;
	
	private SimplePanel bookletLink;
	private SimplePanel chapterLink;
	
	private EventBus pageRequestEventBus;
	
	private final static String STYLE_NAME = "navigationWidget"; 

	public NavigationWidget(EventBus pageChangeEventBus, PageRequestHandler pageRequestHandler, Model model) {
		this.model = model;
		this.pageRequestEventBus = pageChangeEventBus;

		initialise(pageRequestHandler);

		setDefaults();

		pageChangeEventBus.addListener(this);
	}

	private void initialise(PageRequestHandler pageRequestHandler) {
		bookletSlider = new NavigationSlider("Booklets", pageRequestHandler);
		add(bookletSlider);
		bookletLink = new SimplePanel();
		bookletLink.setStyleName(STYLE_NAME + "-bookletLink");
		add(bookletLink);
		chapterSlider = new NavigationSlider("Chapters", pageRequestHandler);
		add(chapterSlider);
		chapterLink = new SimplePanel();
		chapterLink.setStyleName(STYLE_NAME + "-chapterLink");
		add(chapterLink);
		pageSlider = new NavigationSlider("Pages", pageRequestHandler);
		add(pageSlider);
	}

	private void setDefaults() {
		bookletSlider.setVisible(false);
		bookletLink.setVisible(false);
		chapterSlider.setVisible(false);
		chapterLink.setVisible(false);
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
			case GROUP:
				model.getMenus(new ModelAsyncPlug<Collection<MenuData>>() {
					@Override
					public void update(Collection<MenuData> dataList) {
						reloadTiles(bookletSlider, dataList);
					}
				}, DataType.BOOKLET);
				break;
			case BOOKLET:
				// Create the booklet link for later
				bookletLink.setWidget(
						new TextLink(
								pageRequestEventBus,
								data.getReference(),
								"Parcours: " + data.getMenuTitle()));
				// list the booklet's chapters
				reloadTilesWithParentFirstNextLast(chapterSlider, data.getReference());
				bookletSlider.setFocus(data.getReference());
				break;
			case CHAPTER:
				// Create the chapter link for later
				chapterLink.setWidget(
						new TextLink(
								pageRequestEventBus,
								data.getReference(),
								"Chapitre: " + data.getMenuTitle()));
				// list the chapter's pages
				reloadTilesWithParentFirstNextLast(pageSlider, data.getReference());
				chapterSlider.setFocus(data.getReference());
				break;
			default:
				pageSlider.setFocus(data.getReference());
				break;
			}


			// Change layout according to the new page type.
			switch (pageChangeEvent.getPageType()) {
			case PAGE:
				pageSlider.setVisible(true);
				chapterSlider.setVisible(false);
				chapterLink.setVisible(true);
				bookletSlider.setVisible(false);
				bookletLink.setVisible(true);
				break;
			case CHAPTER:
				pageSlider.setVisible(false);
				chapterSlider.setVisible(true);
				chapterLink.setVisible(false);
				bookletSlider.setVisible(false);
				bookletLink.setVisible(true);
				break;
			case BOOKLET:
				pageSlider.setVisible(false);
				chapterSlider.setVisible(false);
				chapterLink.setVisible(false);
				bookletSlider.setVisible(true);
				bookletLink.setVisible(false);
				break;
			case GROUP:
				pageSlider.setVisible(false);
				chapterSlider.setVisible(false);
				chapterLink.setVisible(false);
				bookletSlider.setVisible(false);
				bookletLink.setVisible(false);
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
		notifyFinished();
	}

	private void reloadTilesWithParentFirstNextLast(final NavigationSlider menu, DataReference parentReference) {
		menu.clearTiles();

		model.getAssociatedData(new ModelAsyncPlug<Data>() {
			@Override
			public void update(Data data) {
				menu.addFirstTile(data.getMenu());
			}
		}, parentReference);

		model.getSubMenus(new ModelAsyncPlug<Collection<MenuData>>() {
			@Override
			public void update(Collection<MenuData> dataList) {
				for (MenuData menuData : dataList) {
					menu.addTile(menuData);
					
					notifyFinished();
				}
			}
		}, parentReference);
		
		model.getNextMenu(new ModelAsyncPlug<MenuData>() {
			@Override
			public void update(MenuData menuData) {
				menu.addLastTile(menuData);
			}
		}, parentReference);
	}
	
	
	public void setFocus(DataReference menuReference) {
		switch(menuReference.getType()) {
		case GROUP:
			// remove focus we don't care for this widget
			return;
		case BOOKLET:
			bookletSlider.setFocus(menuReference);
			break;
		case CHAPTER:
			chapterSlider.setFocus(menuReference);
			break;
		case PAGE:
			pageSlider.setFocus(menuReference);
			break;
		default:
		}
	}
	
	private void notifyFinished() {
		// notify the menu finished loading
		pageRequestEventBus.fireEvent(new WidgetLoadedEvent(this));
	}
}



















