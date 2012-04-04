package ch.sebastienzurfluh.client.control.eventbus.events;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.model.structure.Data;

/**
 * This event is fired when a page change has been approved.
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageChangeEvent extends Event {
	private DataType pageType;
	private Data data;
	
	public PageChangeEvent(DataType pageType, Data data) {
		this.pageType = pageType;
		this.data = data;
	}

	@Override
	public EventType getType() {
		return EventType.PAGE_CHANGE_EVENT;
	}
	
	public DataType getPageType() {
		return pageType;
	}
	
	public Data getData() {
		return data;
	}
}
