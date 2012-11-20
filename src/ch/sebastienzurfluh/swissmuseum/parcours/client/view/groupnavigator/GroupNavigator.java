package ch.sebastienzurfluh.swissmuseum.parcours.client.view.groupnavigator;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;

import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;

public class GroupNavigator extends LayoutPanel {
	public GroupNavigator(EventBus eventBus, Model model, PageRequestClickHandler pageRequestHandler) {
		Image headerImage = new Image("resources/images/fioritures/parcours_entete_2.png");
		headerImage.setStyleName("header");
		add(headerImage);
		
		LoadOnDemandTileWidget tileMenus =
				new LoadOnDemandTileWidget(eventBus, pageRequestHandler, model);
		add(tileMenus);
	}
}
