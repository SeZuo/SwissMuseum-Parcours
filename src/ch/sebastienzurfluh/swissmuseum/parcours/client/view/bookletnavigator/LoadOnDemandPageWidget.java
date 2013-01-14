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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator;

import java.util.LinkedList;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.PageToken;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.TextParser;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class LoadOnDemandPageWidget extends FlowPanel implements Observer {
	private HTML title;
	private Label header;
	private FlowPanel content;
	private TextParser parser;
	private Model model;
	private MenuData menuData;
	
	private static String primaryStyleName =  "pageWidget";
	
	public LoadOnDemandPageWidget(MenuData menuData, EventBus pageChangeEventBus, Model model) {
		this.model = model;
		this.menuData = menuData;
		
		parser = new TextParser(pageChangeEventBus, model);
		
		title = new HTML("");
		
		header = new Label("");
		
		
		content = new FlowPanel();
		
		
		this.add(title);
		this.add(header);
		this.add(content);
		
		
		
		if(model.getCurrentPageData().getReference().equals(menuData.getReference()))
			load();
		else
			postponeLoad();
	}
	
	/** Defer theming until the widget is rendered **/
	@Override
	protected void onLoad() {
		super.onLoad();
		title.setStyleName(primaryStyleName + "-title");
		header.setStyleName(primaryStyleName + "-header");
		content.setStyleName(primaryStyleName + "-content");
		setStyleName(primaryStyleName);
	}
	
	/**
	 * Load data from current page in memory (model).
	 */
	public void load() {
		this.title.setHTML(
				"<span class='" + primaryStyleName + "-spanTitle'>"
				+ model.getCurrentPageData().getPageTitle()
				+ "</span>");
		this.header.setText(" " + model.getCurrentPageData().getPageContentHeader());
		
		this.content.clear();
		LinkedList<PageToken> tokenisedContent = 
				parser.parse(model.getCurrentPageData().getPageContentBody());
		for (PageToken pageToken : tokenisedContent) {
			if (pageToken.isResource()) {
				this.content.add(pageToken.getResourceWidget());
			} else if (pageToken.isText()) {
				this.content.add(new HTML(pageToken.getText()));
			}
		}
	}
	
	/**
	 * Postpone the load until the data is available.
	 */
	public void postponeLoad() {
		model.currentPageDataObservable.subscribeObserver(this);
	}


	@Override
	public void notifyObserver(Observable source) {
		if(model.getCurrentPageData().getReference().equals(menuData.getReference())) {
			load();
			
			// this is the main difference with the dynamic PageWidget.
			// the page will only load once.
			model.currentPageDataObservable.unsubscribeObserver(this);
		}
	}
}