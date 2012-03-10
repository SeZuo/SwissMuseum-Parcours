package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.PageRequestHandler;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.view.Navigation.NavigationWidget;
import ch.sebastienzurfluh.client.view.TileMenu.TileWidget;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Facade for the view.
 *
 * @author Sebastien Zurfluh
 */
public class View extends SimplePanel {
	public View(EventBus eventBus, PageRequestHandler pageRequestHandler, Model model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestHandler != null;
		
		
		// Setup main panel
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setWidth("100%");
		mainPanel.setStyleName("mainPanel");
		
		
		// Create main sections
		NavigationWidget navigation = new NavigationWidget(eventBus);
		HierarchyWidget hierarchy = new HierarchyWidget(eventBus);
		PageWidget page = new PageWidget(eventBus);
		TileWidget tileMenu = new TileWidget(eventBus, model);
		FooterWidget footer = new FooterWidget();
		
		// Add main sections to main panel
		mainPanel.add(navigation);
		mainPanel.add(hierarchy);
		mainPanel.add(page);
		mainPanel.add(tileMenu);
		mainPanel.add(footer);
		
		setWidget(mainPanel);
	}
}
