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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view.pagewidget;

import java.util.LinkedList;

import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.structure.PageData;
import ch.sebastienzurfluh.swissmuseum.parcours.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.parcours.client.patterns.Observer;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlowPanel;


/**
 * Main page widget. It displays the content of a page.
 * 
 * Fires {@link PageChangeRequest} when it has been modified.
 * 
 * @author Sebastien Zurfluh
 *
 */
public class PageWidget extends FlowPanel implements Observer {
	private HTML title;
	private Label header;
	private FlowPanel content;
	private TextParser parser;
	private Model model;

	private static String primaryStyleName =  "pageWidget";
	public PageWidget(EventBus pageChangeEventBus,
			Model model) {
		this.model = model;
		
		model.currentPageDataObservable.subscribeObserver(this);
		
		parser = new TextParser(pageChangeEventBus, model);
		
		title = new HTML("");
		title.setStyleName(primaryStyleName + "-title");
		header = new Label("");
		header.setStyleName(primaryStyleName + "-header");
		
//		Image titleImage = new Image("resources/images/fioritures/line.jpg");
//		titleImage.setStyleName(primaryStyleName + "-title-image");
		
		content = new FlowPanel();
		content.setStyleName(primaryStyleName + "-content");
		
		this.add(title);
		this.add(header);
//		this.add(titleImage);
		this.add(content);
		
		this.setStyleName(primaryStyleName);
	}

	@Override
	public void notifyObserver(Observable source) {
		if(model.getCurrentPageData().equals(PageData.NONE)) {
			setVisible(false);
		} else {
			setVisible(true);
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
	}
}
