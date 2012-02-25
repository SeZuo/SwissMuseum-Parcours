package ch.sebastienzurfluh.client.control;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.view.View;

import com.google.gwt.user.client.ui.RootPanel;

public class AppPresenter {

	public AppPresenter() {
	}

	public void start() {
		EventBus eventBus = new EventBus();

		Model model = new Model(eventBus);

		View view = new View(RootPanel.get(), eventBus);
	}
}