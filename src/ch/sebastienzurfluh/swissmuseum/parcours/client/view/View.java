package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent.EventType;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.view.AnimatedMainPanel;
import ch.sebastienzurfluh.swissmuseum.core.client.view.FooterWidget;
import ch.sebastienzurfluh.swissmuseum.core.client.view.animations.ScrollToTheTop;
import ch.sebastienzurfluh.swissmuseum.core.client.view.eventbushooks.ScrollToPanelOnEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.TileWidget;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator.BookletNavigator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.widget.Carousel;

/**
 * Main View object.
 * 
 * This view lets the user browse the booklet catalogue.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class View extends SimplePanel {
	// Shared handler for page requests
		private PageRequestClickHandler pageRequestHandler;
		private AnimatedMainPanel mainPanel;
		private FlowPanel pagePanel;
		private FlowPanel groupPanel;
		private EventBus eventBus;
		private Model model;
		private TileWidget tileMenu;
		private FooterWidget footer;
	
	/**
	 * 
	 * @param eventBus manages all the events for the view
	 * @param pageRequestEventHandler this needs to be set before creating the view
	 * @param resourceRequestHandler this handler will be needed to load resources
	 * @param model is of course used to access the data
	 */
	public View(EventBus eventBus,
			PageRequestEventHandler pageRequestEventHandler,
			ResourceRequestEventHandler resourceRequestHandler,
			Model model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestEventHandler != null;
		
		this.eventBus = eventBus;
		this.model = model;
		
		// Initialise the MGWT library (animations and transition effects).
		MGWT.applySettings(MGWTSettings.getAppSetting());
		
		pagePanel = new FlowPanel();
		groupPanel = new FlowPanel();

		// Setup main panel
		mainPanel = new AnimatedMainPanel(model, groupPanel, pagePanel);
		
		mainPanel.setStyleName("mainPanel");
		
		setStyleName("mainPanel");
		

		pageRequestHandler = new PageRequestClickHandler(eventBus);

		// create mainPanel before filling it
		setWidget(mainPanel);
		
		afterAttached();
	}
	
	/**
	 * Fill main sections and add main sections to main panel according to layout
	 * 
	 * Call this after the panel has been attached.
	 */
	public void afterAttached() {
		//TODO: replace with carousel
		BookletNavigator booklet = new BookletNavigator(model, eventBus);
		pagePanel.add(booklet);
		
		
		Image headerImage = new Image("resources/images/fioritures/parcours_entete_2.png");
		headerImage.setStyleName("header");
		groupPanel.add(headerImage);
		
		tileMenu = new TileWidget(eventBus, pageRequestHandler, model);
		groupPanel.add(tileMenu);
		
		// Create the return to the top button.
		Image returnToTheTop = new Image("resources/images/buttons/go-top.png");
		returnToTheTop.setStyleName("returnToTop");
		returnToTheTop.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ScrollToTheTop.getInstance().start();
			}
		});
		groupPanel.add(returnToTheTop);
		
		footer = new FooterWidget();
		groupPanel.add(footer);

		// Add some global functionalities with low priority
		ScrollToPanelOnEvent.addRule(eventBus, booklet, EventType.PAGE_CHANGE_EVENT);
	}
}