package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.view.Navigation.NavigationWidget;
import ch.sebastienzurfluh.client.view.TileMenu.TileMenuWidget;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Facade for the view.
 *
 * @author Sebastien Zurfluh
 */
public class View {
	public View(RootPanel parent,
			EventBus eventBus) {
		assert eventBus != null;
		
		// Setup main panel
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setWidth("100%");
		mainPanel.setStyleName("mainPanel");
		
		parent.add(mainPanel);
		
		
		// Create main sections
		NavigationWidget navigation = new NavigationWidget();
		
		HierarchyWidget hierarchy = new HierarchyWidget();
		
		PageWidget page = new PageWidget();
		
		TileMenuWidget tileMenu = new TileMenuWidget();
		
		FooterWidget footer = new FooterWidget();
		
		// Set up listeners
		eventBus.addListener(navigation);
		eventBus.addListener(hierarchy);
		eventBus.addListener(page);
		eventBus.addListener(tileMenu);
		
		// Add main sections to main panel
		mainPanel.add(navigation);
		mainPanel.add(hierarchy);
		mainPanel.add(page);
		mainPanel.add(tileMenu);
		mainPanel.add(footer);
	}
}
