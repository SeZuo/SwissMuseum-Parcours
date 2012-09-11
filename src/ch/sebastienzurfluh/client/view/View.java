package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.Model.ViewMode;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.patterns.Observer;

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Main View object.
 * 
 * Listens to model changes in order to adapt the view layout.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class View extends SimplePanel implements Observer {
	private EventBus eventBus;
	private PageRequestEventHandler pageRequestEventHandler;
	private ResourceRequestEventHandler resourceRequestHandler;
	private Model model;
	
	public View(EventBus eventBus,
			PageRequestEventHandler pageRequestEventHandler,
			ResourceRequestEventHandler resourceRequestHandler,
			Model model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestEventHandler != null;
		
		this.eventBus = eventBus;
		this.model = model;
		this.pageRequestEventHandler = pageRequestEventHandler;
		this.resourceRequestHandler = resourceRequestHandler;
		
		model.viewModeObservable.subscribeObserver(this);
	}
	
	private void setViewMode(ViewMode viewMode) {
		switch(viewMode) {
		case EDIT:
			CMSView cmsView = new CMSView(eventBus, pageRequestEventHandler, resourceRequestHandler, model);
			this.setWidget(cmsView);
			cmsView.afterAttached();
			break;
		case BROWSE:
			BrowseView view = new BrowseView(eventBus, pageRequestEventHandler, resourceRequestHandler, model);
			this.setWidget(view);
			view.afterAttached();
			break;
		default:
			break;
		}
	}

	@Override
	public void notifyObserver(Observable source) {
		setViewMode(model.getCurrentViewMode());
	}
}
