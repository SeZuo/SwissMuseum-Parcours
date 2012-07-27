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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Observer;
import java.util.TreeSet;

import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.MenuList;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
import ch.sebastienzurfluh.client.view.navigation.animation.AnimatorFactory;
import ch.sebastienzurfluh.client.view.navigation.animation.AnimatorFactory.AnimatorType;
import ch.sebastienzurfluh.client.view.navigation.animation.NavigationAnimator;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This widget gives a way to navigate (next/previous) between pages. 
 * @author Sebastien Zurfluh
 */
public class NavigationSlider extends FocusPanel implements MenuList {
	private AbsolutePanel animationPanel;
	private HorizontalPanel tilePanel;
	private TreeSet<NavigationItem> tileOrderList;
	
	private PageRequestHandler pageRequestHandler;
	private NavigationAnimator animatedScroller;
	
	public NavigationSlider(String string, PageRequestHandler pageRequestHandler) {
		this.pageRequestHandler = pageRequestHandler;
		
		setStyleName("navigationSlider");
		
		tilePanel = new HorizontalPanel();
		tileOrderList = new TreeSet<NavigationItem>(new Comparator<NavigationItem>() {
			@Override
			public int compare(NavigationItem o1, NavigationItem o2) {
				return ((Integer) o1.getPriority()).compareTo(o2.getPriority());
			}
		});
		
		animationPanel = new AbsolutePanel();
		animationPanel.add(tilePanel);
		
		add(animationPanel);
		animationPanel.setSize("100%", "100%");
		
		animatedScroller = AnimatorFactory.createAnimator(
				AnimatorType.STATIC,
				animationPanel, tilePanel, this);
		
		animatedScroller = AnimatorFactory.createAnimator(
				AnimatorType.SWIPE,
				animationPanel, tilePanel, this);
		
		addTouchStartHandler(animatedScroller);	
		addTouchEndHandler(animatedScroller);
		addMouseDownHandler(animatedScroller);
		addMouseUpHandler(animatedScroller);
		
		preventBrowserInterference();
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
		tileOrderList.add(tile);
		// TODO order the tiles in the menu according to their priority number
		tilePanel.add(tile);
	}
	
	public void clearTiles() {
		tilePanel.clear();
		tileOrderList.clear();
	}

	/*******************************************************************************/	
	/************* Centralised widget selection ************************************/
	/*******************************************************************************/
	
	private int currentItemNumber = 0;
	/**
	 * @return the number of menu items in the widget
	 */
	public int getItemCount() {
		return tileOrderList.size();
	}
	
	public void setCurrentItem(int number) {
		currentItemNumber = number;
	}
	public int getCurrentItemNumber() {
		return currentItemNumber;
	}
	
	/*******************************************************************************/	
	/******** END - Centralised widget selection ***********************************/
	/*******************************************************************************/
	
	
	/*******************************************************************************/	
	/************* Prevent browser's mouse/touch events ****************************/
	/*******************************************************************************/
	
	private HandlerRegistration handlerRegistration = null;
	
	private Event.NativePreviewHandler preventDefaultMouseEvents = 
			new Event.NativePreviewHandler() {
		@Override
		public void onPreviewNativeEvent(NativePreviewEvent event) {
			if (event.getTypeInt() == Event.ONTOUCHSTART 
					|| event.getTypeInt() == Event.ONTOUCHMOVE 
					|| event.getTypeInt() == Event.ONMOUSEDOWN
					|| event.getTypeInt() == Event.ONMOUSEMOVE
					) {
				event.getNativeEvent().preventDefault(); 
			}
		}		
	};
	
	/**
	 * The browser steals the mouse events from GWT. It tries to select text
	 * or copy/paste by drag and drop.
	 * If you don't want the widget to interfere on this widget call this.
	 */
	private void preventBrowserInterference() {
		addDomHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				handlerRegistration = Event.addNativePreviewHandler(preventDefaultMouseEvents);
			}
		}, MouseOverEvent.getType());

		addDomHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (handlerRegistration != null) {
					handlerRegistration.removeHandler();
				}
			}
		}, MouseOutEvent.getType());
	}

	
	/**
	 * 
	 * @param number the rank of the widget to retrieve
	 * @return the {@code number}th widget or null if there's none at this position.
	 */
	public NavigationItem getWidget(int number) {
		int i = 0;
		for (Iterator<NavigationItem> iterator = tileOrderList.iterator(); iterator.hasNext(); i++) {
			if (i == number)
				return  iterator.next();
		}
		return null;
	}
	
	/*******************************************************************************/
	/***** END *** Prevent browser's mouse/touch events ****************************/
	/*******************************************************************************/
}


