package ch.sebastienzurfluh.client.control.eventbus.events;

import com.google.gwt.http.client.URL;

import ch.sebastienzurfluh.client.control.eventbus.Event;


public class PageChangeEvent extends Event {
	public enum PageType {
		RESSOURCE,
		PAGE,
		CHAPTER,
		PARCOURS,
		SUPER;
	}
	
	private PageType pageType;
	
	public PageChangeEvent(PageType pageType) {
		this.pageType = pageType;
	}

	public PageType getPageType() {
		return pageType;
	}
	
	public URL getPageURL() {
		return null;
	}

	@Override
	public EventType getType() {
		return EventType.LOAD_PAGE_EVENT;
	}
}
