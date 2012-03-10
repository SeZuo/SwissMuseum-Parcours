package ch.sebastienzurfluh.client.control.eventbus.events;

import com.google.gwt.http.client.URL;

import ch.sebastienzurfluh.client.control.eventbus.Event;

/**
 * This event is fired after a page has been changed.
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageChangeRequest extends Event {
	int pageId;
	
	public PageChangeRequest(int pageId) {
		this.pageId = pageId;
	}

	public int getPageId() {
		return pageId;
	}

	@Override
	public EventType getType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}
}
