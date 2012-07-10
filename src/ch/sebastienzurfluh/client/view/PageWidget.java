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

package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;

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
public class PageWidget extends VerticalPanel implements EventBusListener {
	private Label title;
	private Label header;
	private HTML content;

	private static String primaryStyleName =  "pageWidget";
	public PageWidget(EventBus pageChangeEventBus) {
		pageChangeEventBus.addListener(this);
		
		title = new Label("");
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
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_EVENT;
	}

	@Override
	public void notify(Event e) {
		if (e instanceof PageChangeEvent) {
			PageChangeEvent pageChangeEvent = (PageChangeEvent) e;
			switch(pageChangeEvent.getPageType()) {
			case RESOURCE:
			case PAGE:
			case CHAPTER:
			case BOOKLET:
				this.setVisible(true);
				this.title.setText(pageChangeEvent.getData().getPageTitle());
				this.header.setText(pageChangeEvent.getData().getPageContentHeader());
				this.content.setHTML(pageChangeEvent.getData().getPageContentBody());
				break;
			case SUPER:
			default:
				this.setVisible(false);
				break;
			}
		}
	}
}
