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

import java.util.Stack;

import ch.sebastienzurfluh.client.control.ModelAsyncPlug;
import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.view.supportwidgets.TextLink;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Hierarchy widget gives a way to go up in the booklet's hierarchy. For instance, when looking at a page, the user
 * can see "Booklet > Chapter > Page", and choose to go back to the chapter or the booklet.
 *
 * @author Sebastien Zurfluh
 *
 */
public class HierarchyWidget extends SimplePanel implements EventBusListener {
	private EventBus eventBus;
	
	private FlowPanel historyPanel;
	
	private String separator = " > ";
	private Model model;
	
	public HierarchyWidget(EventBus eventBus, Model model) {
		this.model = model;
		this.eventBus = eventBus;
		
		this.setStyleName("hierarchyWidget");
		
		eventBus.addListener(this);
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_EVENT;
	}

	@Override
	public void notify(Event e) {
		if(e instanceof PageChangeEvent) {
			PageChangeEvent pageChangeEvent = (PageChangeEvent) e;
			
			if(pageChangeEvent.getPageType() == DataType.SUPER) {
				this.setVisible(false);
			} else {
				this.setVisible(true);
			}
			
			Data currentPage = pageChangeEvent.getData();
			int depth = 0;
			historyPanel = new FlowPanel();
			
			
			switch (pageChangeEvent.getPageType()) {
			case PAGE:
				depth++;
			case CHAPTER:
				depth++;
			case BOOKLET:
				depth++;
			case SUPER:
			default:
			}
			
			final Stack<TextLink> stack = new Stack<TextLink>();
			recursiveParentSearchAndFill(stack, currentPage, depth);
			
			
			setWidget(historyPanel);
		}
	}
	
	/**
	 * Search the model asynchronously in order to find all parents, then fill the history panel accordingly.
	 * @param stack
	 * @param page
	 * @param depth
	 */
	private void recursiveParentSearchAndFill(final Stack<TextLink> stack, final Data page, final int depth) {
		if (depth == 0) {
			printParentHood(stack);
		} else {
			model.getParentOf(new ModelAsyncPlug<Data>() {
				@Override
				public void update(Data data) {
					stack.push(new TextLink(eventBus, page.getReference(), page.getPageTitle()));
					recursiveParentSearchAndFill(stack, data, depth-1);
				}
			}, page.getReference());
		}
	}
	
	private void printParentHood(Stack<TextLink> stack) {
		while(!stack.isEmpty()) {
			Label label = new Label(separator);
			label.setStyleName("hierarchyWidget-separator");
			historyPanel.add(label);
			historyPanel.add(stack.pop());
		}
		if (historyPanel.getWidgetCount() == 0) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
			historyPanel.remove(0);
		}
	}
}
