package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent.EventType;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
//import ch.sebastienzurfluh.swissmuseum.core.client.view.FooterWidget;
import ch.sebastienzurfluh.swissmuseum.core.client.view.eventbushooks.ScrollToPanelOnEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;
//import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.TileWidget;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator.BookletNavigator;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.groupnavigator.GroupNavigator;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

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
	/**
	 * Create and attach the Parcours view to the RootPanel.
	 * 
	 * @param eventBus manages all the events for the view
	 * @param assertPageRequestEventHandlerExists this needs to be set before creating the view
	 * @param assertResourceRequestHandlerExists this handler will be needed to load resources
	 * @param model is of course used to access the data
	 */
	public View(
			EventBus eventBus,
			Model model,
			PageRequestEventHandler assertPageRequestEventHandlerExists,
			ResourceRequestEventHandler assertResourceRequestHandlerExists) {
		assert eventBus != null;
		assert model != null;
		assert assertPageRequestEventHandlerExists != null;
		assert assertResourceRequestHandlerExists != null;
		
		// Initialise the MGWT library (animations and transition effects).
		MGWT.applySettings(MGWTSettings.getAppSetting());

		AnimationHelper animationHelper = new AnimationHelper();
		RootPanel.get().add(animationHelper);
		
		
		PageRequestClickHandler pageRequestHandler = new PageRequestClickHandler(eventBus);

		
		BookletNavigator bookletNavigator = new BookletNavigator(model, eventBus);
		GroupNavigator groupNavigator =
				new GroupNavigator(eventBus, model, pageRequestHandler);
		
		
		animationHelper.goTo(groupNavigator, Animation.FADE);
		
		
		// Switch between BookletNavigator and GroupNavigator when needed.
		new WidgetSwitcher(model, groupNavigator, bookletNavigator, animationHelper);
		
		
		setStyleName("mainPanel");
		
		
//		// Create the return to the top button.
//		Image returnToTheTop = new Image("resources/images/buttons/go-top.png");
//		returnToTheTop.setStyleName("returnToTop");
//		returnToTheTop.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				ScrollToTheTop.getInstance().start();
//			}
//		});
//		RootPanel.get().add(returnToTheTop);
//		
//		footer = new FooterWidget();
//		RootPanel.get().add(footer);

		// Add some global functionalities with low priority
		ScrollToPanelOnEvent.addRule(eventBus, RootPanel.get(), EventType.PAGE_CHANGE_EVENT);
	}
}