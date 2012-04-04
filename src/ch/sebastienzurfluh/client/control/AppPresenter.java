package ch.sebastienzurfluh.client.control;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestHandler;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.view.View;

import com.google.gwt.user.client.ui.Panel;

/**
 * This class creates the web app and adds it to the given panel.
 * @author Sebastien Zurfluh
 *
 */
public class AppPresenter {
	private Panel parent;
	private EventBus eventBus;
	private Model model;
	
	public AppPresenter(Panel parent) {
		this.parent = parent;
	}

	public void start() {
		eventBus = new EventBus();

		model = ModelFactory.createModel(ModelFactory.Connector.TEST);
		
		
		PageRequestHandler pageRequestHandler = new PageRequestHandler(eventBus, model);
		
		View view = new View(eventBus, pageRequestHandler, model);
		
		parent.add(view);
		
		// Start the app
		eventBus.fireEvent(new PageChangeRequest(0));
	}
}