package ch.sebastienzurfluh.client.control.eventbus.events;

import java.util.LinkedList;

import com.google.gwt.http.client.URL;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.model.structure.Data;

/**
 * This event is fired when a page change has been approved.
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageChangeEvent extends PageChangeRequest {
	private PageType pageType;
	
	public PageChangeEvent(PageType pageType, int pageId) {
		super(pageId);
		this.pageType = pageType;
	}

	@Override
	public EventType getType() {
		return EventType.PAGE_CHANGE_EVENT;
	}
	
	public PageType getPageType() {
		return pageType;
	}
}
