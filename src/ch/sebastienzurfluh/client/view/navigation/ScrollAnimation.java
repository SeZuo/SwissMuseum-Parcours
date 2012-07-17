package ch.sebastienzurfluh.client.view.navigation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Animation for the scrolling effects.
 * Create a new object for each animated panel.
 *
 * @author Sebastien Zurfluh
 *
 */
public class ScrollAnimation extends Animation {
	private static final int DURATION = 400; //ms
	private AbsolutePanel animatedPanel;
	private Widget movingWidget;

	/**
	 * You need a different object per animated panel.
	 * Only one child can be animated this way.
	 *
	 * @param animatedPanel parent of the moving widget
	 * @param movingWidget itself
	 */
	public ScrollAnimation(AbsolutePanel animatedPanel, Widget movingWidget) {
		this.animatedPanel = animatedPanel;
		this.movingWidget = movingWidget;
    }

	int initialPosition, delta;
    public void scroll(int delta) {
    	initialPosition = movingWidget.getAbsoluteLeft();
    	this.delta = delta;
    	run(DURATION);
    }

    @Override
    protected void onUpdate(double progress) {
        int position = (int) (initialPosition - (progress * delta));
        if (position < 0) {
        	position = 0;
        }
        animatedPanel.setWidgetPosition(movingWidget, position, 0);
    }
}
