package ch.sebastienzurfluh.client.control.eventbus.events;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.model.Page;


public class PageChangeRequest extends Event {
	private Page page;
	
	public PageChangeRequest(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	@Override
	public EventType getType() {
		return EventType.LOAD_PAGE_REQUEST;
	}
}
