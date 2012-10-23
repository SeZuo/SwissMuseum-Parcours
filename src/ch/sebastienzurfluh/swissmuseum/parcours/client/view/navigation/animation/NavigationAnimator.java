package ch.sebastienzurfluh.swissmuseum.parcours.client.view.navigation.animation;

import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartHandler;

/**
 * Interface for the NavigationSlider animations.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public interface NavigationAnimator extends TouchMoveHandler, TouchEndHandler, TouchStartHandler,
MouseDownHandler, MouseUpHandler, MouseMoveHandler {
	/**
	 * Move the scroller to the specified widget
	 * @param number -th widget
	 */
	public void setFocusWidget(int number);
}
