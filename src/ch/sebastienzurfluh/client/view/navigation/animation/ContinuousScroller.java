package ch.sebastienzurfluh.client.view.navigation.animation;

import ch.sebastienzurfluh.client.view.navigation.NavigationSlider;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Animation for the scrolling effects.
 * Create a new object for each animated panel.
 *
 * @author Sebastien Zurfluh
 *
 */
public class ContinuousScroller extends Animation  implements NavigationAnimator {
	private static final int VERY_SLOW = 4000; //ms
	private static final int SLOW = 400; //ms
	private static final int QUICK = 40; //ms
	private AbsolutePanel animatedPanel;
	private Widget movingWidget;
	private NavigationSlider slider;
	
	/**
	 * You need a different object per animated panel.
	 * Only one child can be animated this way.
	 * If the size of the panel changes you have to recreate this widget.
	 *
	 * @param animatedPanel parent of the moving widget
	 * @param movingWidget itself
	 */
	public ContinuousScroller(AbsolutePanel animatedPanel, Widget movingWidget, NavigationSlider slider) {
		this.animatedPanel = animatedPanel;
		this.movingWidget = movingWidget;
		this.slider = slider;
		
    }

	int initialPosition, delta;
    private void scroll(int delta, int duration) {
    	initialPosition = movingWidget.getAbsoluteLeft();
    	this.delta = delta;
    	
    	run(duration);
    }
    
    /**
     * @param pos -ition absolute
     * @param duration in ms
     */
    private void scrollTo(int pos, int duration) {
    	initialPosition = movingWidget.getAbsoluteLeft();
    	this.delta = pos - initialPosition;
    	
    	run(duration);
    }
    
    /**
     * @param duration in ms
     */
    private void scrollLeft(int duration) {
    	scrollTo(0, duration);
    }
    /**
     * @param duration in ms
     */
    private void scrollRight(int duration) {
    	int numberOfItems = slider.getItemCount();
    	int itemSize = movingWidget.getOffsetWidth() / numberOfItems;
	
    	scrollTo(movingWidget.getOffsetWidth() - itemSize, duration);
    }

    @Override
    protected void onUpdate(double progress) {
        int position = (int) (initialPosition - (progress * delta));
        animatedPanel.setWidgetPosition(movingWidget, position, 0);
    }
    
    
	private HandlerRegistration mouseMoveHandler;
	private HandlerRegistration touchMoveHandler;
    
    private int initialXPos;
	private void onStart(int newXPos) {
		// adding and removing the handlers is the best solution to
		// avoid unwanted move events.
		mouseMoveHandler = slider.addMouseMoveHandler(this);
		touchMoveHandler = slider.addTouchMoveHandler(this);
		
		initialXPos = newXPos;
	}
	/**
     * @param newXPos -ition absolute
     */
	private void onMove(int newXPos) {
		if (isNearBorder(newXPos)) {
			// edge scrolling
			if(isLeft(newXPos))
				// move 10 px per update.
				scrollLeft(VERY_SLOW);
			else
				scrollRight(VERY_SLOW);
		} else {
			// normal scrolling
			scroll(initialXPos - newXPos, QUICK);
		}
		initialXPos = newXPos;
	}
	/**
     * @param newXPos -ition absolute
     */
	private boolean isNearBorder(int newXPos) {
		if (newXPos < animatedPanel.getAbsoluteLeft() + 50 || newXPos > animatedPanel.getAbsoluteLeft() + movingWidget.getOffsetWidth() - 50)
			return true;
		return false;
	}
	/**
     * @param newXPos -ition absolute
     */
	private boolean isLeft(int newXPos) {
		if (Math.abs(newXPos - animatedPanel.getAbsoluteLeft()) < Math.abs(animatedPanel.getAbsoluteLeft() + movingWidget.getOffsetWidth() - newXPos))
			return true;
		return false;
	}

	/**
     * @param newXPos -ition absolute
     */
	private void onEnd(int newXPos) {
		// see {@code onStart}
		mouseMoveHandler.removeHandler();
		touchMoveHandler.removeHandler();
		
		// get the widget back into boundaries.
		int leftDelta = animatedPanel.getAbsoluteLeft() - movingWidget.getAbsoluteLeft();
    	if (leftDelta < 0) {
    		scrollTo(0, QUICK);
    	} else {
//    		// note that the panel cannot be pushed out of the other boundary.
//    		
//    		// snap to tile
//    		// the median is the position of the center of the container panel on the moving panel
//    		int median = 0;
//    		
//    		// retrieve the closest item
//    		int numberOfItems = slider.size();
//    		int itemSize = movingWidget.getOffsetWidth() / numberOfItems;
//    		int itemNumber = (int) Math.floor(median / itemSize);
////			NavigationItem item = slider.getWidget(itemNumber);
//    		// move to the right place
//    		int itemPosition = itemNumber * itemSize;
//    		// load the corresponding page
//    		scrollTo(itemPosition, SLOW);
    	}
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		onStart(event.getChangedTouches().get(0).getScreenX());
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		onEnd(event.getChangedTouches().get(0).getScreenX());
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		onMove(event.getChangedTouches().get(0).getScreenX());
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		onMove(event.getScreenX());
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		onEnd(event.getScreenX());
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		onStart(event.getScreenX());
	}

	@Override
	public void setFocusWidget(int number) {
	}
}
