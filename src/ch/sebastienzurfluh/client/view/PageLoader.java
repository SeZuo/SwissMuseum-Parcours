package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;

import com.google.gwt.user.client.ui.SimplePanel;

public class PageLoader implements EventBusListener {
	// Panels
	private SimplePanel mainPanel;
	
	// Loadable pages
	private SimplePanel recordPage;
	private SimplePanel emptyPanel;
	
	
	
	public PageLoader(SimplePanel mainPanel,
			SimplePanel recordPage) {
		this.mainPanel = mainPanel;
		this.recordPage = recordPage;
		this.emptyPanel = new SimplePanel();
	}
	
	public void loadRecordPage() {
		mainPanel.setWidget(recordPage);
	}
	
	public void loadNone() {
		mainPanel.setWidget(emptyPanel);
	}

	@Override
	public EventType getEventType() {
		return EventType.LOAD_PAGE_REQUEST;
	}

	@Override
	public void notify(Event e) {
		if (e instanceof PageChangeEvent) {
			switch (((PageChangeEvent) e).getPage()) {
			case NONE:
				loadNone();
				break;
			default:
				break;
			}
		}
	}

}
