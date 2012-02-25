package ch.sebastienzurfluh.client.control;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;

import com.google.gwt.user.client.ui.SimplePanel;

public class PageLoader implements EventBusListener {
	// Panels
	private SimplePanel mainPanel;
	
	// Loadable pages
	private SimplePanel recordPage;
	private SimplePanel emptyPanel;
	
	
	/**
	 * Handler who listens to the {@code PageChangeRequest}s and triggers page changes. Fires {@code PageChangeEvents}.
	 * @param mainPanel
	 * @param recordPage
	 */
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
		if (e instanceof PageChangeRequest) {
			switch (((PageChangeRequest) e).getPage()) {
			case RECORDS:
				loadRecordPage();
				break;
			case NONE:
				loadNone();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void notify(Event e) {
		// TODO Auto-generated method stub
		
	}

}
