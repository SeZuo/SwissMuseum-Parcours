package ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus;

import com.google.gwt.user.client.Command;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent.EventType;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.TimeMachine;

/**
 * Instantiate this if you want history support.
 * 
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageRequestHistory implements EventBusListener {

	protected EventBus eventBus;

	public PageRequestHistory(EventBus eventBus, Model model) {
		System.out.println("PAGE REQUEST HISTORY CREATED");
		
		this.eventBus = eventBus;
		
		eventBus.addListener(this);
	}
	
	private PageChangeRequest cachedGroupRequest;
	
	@Override
	public void notify(AbstractEvent e) {
		System.out.println("PageRequestHistory: notified of PAGE_CHANGE_REQUEST");
		
		if(e instanceof PageChangeRequest) {
			final PageChangeRequest pageChangeRequest = (PageChangeRequest) e;
			
			// avoid storing group requests, they are followed by the first page request and
			// therefore duplicate it's functionnality, but do not forget to store it in order to
			// resend the group request before requesting a page in the group
			if (pageChangeRequest.getPageReference().getType().equals(DataType.GROUP)) {
				cachedGroupRequest = pageChangeRequest;
				return;
			}
			
			System.out.println("PageRequestHistory: storing:" + pageChangeRequest.getPageReference());
			
			// store the request for history use
			TimeMachine.newSnapshot(new Command() {
				@Override
				public void execute() {
					if (pageChangeRequest.getPageReference().getType().equals(DataType.PAGE))
						eventBus.fireEvent(cachedGroupRequest);
					eventBus.fireEvent(pageChangeRequest);
				}
			});
		}
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_REQUEST;
	}

}
