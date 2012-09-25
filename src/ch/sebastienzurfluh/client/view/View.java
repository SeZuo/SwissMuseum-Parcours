package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.IntentHandler;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.client.model.CMSModel;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.Model.ViewMode;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.patterns.Observer;
import ch.sebastienzurfluh.client.view.animations.ScrollToTheTop;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
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
			CMSModel cmsModel = new CMSModel(model);
			// This should be in Control
			IntentHandler intentHandler = new IntentHandler(cmsModel, eventBus);
			CMSView cmsView = 
				new CMSView(
						eventBus,
						pageRequestEventHandler,
						resourceRequestHandler,
						cmsModel);
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