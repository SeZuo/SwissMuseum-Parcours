package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;

import com.google.gwt.user.client.ui.Label;

/**
 * This widget settles on the bottom of the application's panel.
 * @author Sebastien Zurfluh
 *
 */
public class FooterWidget extends Label {
	public FooterWidget() {
		super("Created by Sébastien Zurfluh for the Swiss Museum");
		
		setStyleName("FooterWidget");
	}
}
