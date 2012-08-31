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

package ch.sebastienzurfluh.client.view.pagewidget;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.PageData;
import ch.sebastienzurfluh.client.patterns.Observer;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Main page widget. It displays the content of a page.
 * 
 * Fires {@link PageChangeRequest} when it has been modified.
 * 
 * @author Sebastien Zurfluh
 *
 */
public class PageWidget extends VerticalPanel implements Observer {
	private HTML title;
	private Label header;
	private HTML content;
	private TextParser parser;
	private Model model;

	private static String primaryStyleName =  "pageWidget";
	public PageWidget(EventBus pageChangeEventBus,
			Model model) {
		this.model = model;
		
		parser = new TextParser(pageChangeEventBus, model);
		
		title = new HTML("");
		title.setStyleName(primaryStyleName + "-title");
		header = new Label("");
		header.setStyleName(primaryStyleName + "-header");
		content = new HTML("");
		content.setStyleName(primaryStyleName + "-content");
		
		this.add(title);
		this.add(header);
		this.add(content);
		
		this.setStyleName(primaryStyleName);
	}

	@Override
	public void notifyObserver() {
		if(model.getCurrentPageData().equals(PageData.NONE)) {
			setVisible(false);
		} else {
			setVisible(true);
			this.title.setHTML(
					"<span class='" + primaryStyleName + "-spanTitle'>"
					+ model.getCurrentPageData().getPageTitle()
					+ "</span>");
			this.header.setText(model.getCurrentPageData().getPageContentHeader());
			this.content.setHTML(parser.parse(model.getCurrentPageData().getPageContentBody()));
		}
	}
}
