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

import java.util.HashMap;

import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.view.menuinterface.MenuList;
import ch.sebastienzurfluh.client.view.menuinterface.PageRequestHandler;
import ch.sebastienzurfluh.client.view.navigation.ScrollAnimation;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
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
public class NavigationSlider extends FocusPanel implements MenuList, 
		TouchMoveHandler, TouchEndHandler, TouchStartHandler,
		MouseDownHandler, MouseUpHandler, MouseMoveHandler {
	private AbsolutePanel animationPanel;
	private HorizontalPanel tilePanel;
	private HashMap<Integer, NavigationItem> tileOrderList;
	private PageRequestHandler pageRequestHandler;
	private ScrollAnimation scrollAnimation;
	
	public NavigationSlider(String string, PageRequestHandler pageRequestHandler) {
		this.pageRequestHandler = pageRequestHandler;
		
		setStyleName("navigationSlider");
		
		tilePanel = new HorizontalPanel();
		tileOrderList = new HashMap<Integer, NavigationItem>();
		
		animationPanel = new AbsolutePanel();
		animationPanel.add(tilePanel);
		
		add(animationPanel);
		animationPanel.setSize("100%", "100%");
		
		scrollAnimation = new ScrollAnimation(animationPanel, tilePanel);
		
		addTouchStartHandler(this);	
		addTouchEndHandler(this);
		addMouseDownHandler(this);
		addMouseUpHandler(this);
		
		preventBrowserInterference();
	}
	
	//TODO find another way to do that.
	private final static Integer FIRST_TILE_PRIORITY_NUMBER = -1; 
	public void addFirstTile(MenuData menuData) {
		addTileOnPriority(menuData, FIRST_TILE_PRIORITY_NUMBER);
	}
	
	public void addTile(MenuData menuData) {
		//TODO remove the next line after test
		for(int i=0; i<10; i++) {
			addTileOnPriority(menuData, menuData.getPriorityNumber());			
		}
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

	
	
	
	@Override
	public void onTouchStart(TouchStartEvent event) {
		onStart(event.getChangedTouches().get(0).getClientX());
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		onEnd(event.getChangedTouches().get(0).getClientX());
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		onMove(event.getChangedTouches().get(0).getClientX());
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		onMove(event.getClientX());
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		onEnd(event.getClientX());
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		onStart(event.getClientX());
	}
	
	private HandlerRegistration mouseMoveHandler;
	private HandlerRegistration touchMoveHandler;
	private int initialXPos;
	
	private void onStart(int newXPos) {
		// adding and removing the handlers is the best solution to
		// avoid unwanted move events.
		mouseMoveHandler = addMouseMoveHandler(this);
		touchMoveHandler = addTouchMoveHandler(this);
		
		initialXPos = newXPos;
	}
	
	private void onMove(int newXPos) {
		int delta = initialXPos-newXPos;
		movePanel(delta);
	}
	
	private void onEnd(int newXPos) {
		// see {@code onStart}
		mouseMoveHandler.removeHandler();
		touchMoveHandler.removeHandler();
		// select the right tile
		// move to the right place
		// load the corresponding page
	}

	private void movePanel(int delta) {
		scrollAnimation.scroll(delta);
	}
	
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
	
	/*******************************************************************************/
	/***** END *** Prevent browser's mouse/touch events ****************************/
	/*******************************************************************************/
}


