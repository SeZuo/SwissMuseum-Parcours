package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.IntentHandler;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.Model.ViewMode;
import ch.sebastienzurfluh.swissmuseum.parcours.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.parcours.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.animations.ScrollToTheTop;

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