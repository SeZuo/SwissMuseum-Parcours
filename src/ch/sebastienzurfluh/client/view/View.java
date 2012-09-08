package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.Model.Layout;
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
	private Layout currentLayout;
	private EventBus eventBus;
	private PageRequestEventHandler pageRequestHandler;
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
		this.pageRequestHandler = pageRequestEventHandler;
		this.resourceRequestHandler = resourceRequestHandler;
	}
	
	public void setLayout(Layout layout) {
		switch(layout) {
		case CMS:
			currentLayout = layout;
			break;
		case GROUP:
		case PAGE:
			// GROUP and PAGE use the same layout.
			BrowseView view = new BrowseView(eventBus, pageRequestHandler, resourceRequestHandler, model);
			this.setWidget(view);
			view.afterAttached();
			currentLayout = layout;
			break;
		default:
			break;
		}
	}

	@Override
	public void notifyObserver(Observable source) {
	}
}
