package ch.sebastienzurfluh.client.view.navigation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
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
public class ScrollAnimation extends Animation  implements 
		TouchMoveHandler, TouchEndHandler, TouchStartHandler,
		MouseDownHandler, MouseUpHandler, MouseMoveHandler {
	private static final int SLOW_DURATION = 400; //ms
	private static final int QUICK_DURATION = 40; //ms
	private AbsolutePanel animatedPanel;
	private Widget movingWidget;
	private NavigationSlider slider;

	/**
	 * You need a different object per animated panel.
	 * Only one child can be animated this way.
	 *
	 * @param animatedPanel parent of the moving widget
	 * @param movingWidget itself
	 */
	public ScrollAnimation(AbsolutePanel animatedPanel, Widget movingWidget, NavigationSlider slider) {
		this.animatedPanel = animatedPanel;
		this.movingWidget = movingWidget;
		this.slider = slider;
    }

	int initialPosition, delta;
    private void scroll(int delta, boolean quick) {
    	initialPosition = movingWidget.getAbsoluteLeft();
    	this.delta = delta;
    	
    	if(quick) {
    		run(QUICK_DURATION);
    	} else {
    		run(SLOW_DURATION);
    	}
    }
    
    private void scrollTo(int pos, boolean quick) {
    	initialPosition = movingWidget.getAbsoluteLeft();
    	this.delta = pos - initialPosition;
    	
    	if(quick) {
    		run(QUICK_DURATION);
    	} else {
    		run(SLOW_DURATION);
    	}
    }
    

    @Override
    protected void onUpdate(double progress) {
        int position = (int) (initialPosition - (progress * delta));
        animatedPanel.setWidgetPosition(movingWidget, position, 0);
    }
    
    
	private HandlerRegistration mouseMoveHandler;
	private HandlerRegistration touchMoveHandler;
    
    int initialXPos;
	public void onStart(int newXPos) {
		// adding and removing the handlers is the best solution to
		// avoid unwanted move events.
		mouseMoveHandler = slider.addMouseMoveHandler(this);
		touchMoveHandler = slider.addTouchMoveHandler(this);
		
		initialXPos = newXPos;
	}
	
	
	public void onMove(int newXPos) {
		// the quicker you swipe the bigger the step.
		
		scroll(initialXPos-newXPos, true);
		initialXPos = newXPos;
	}
	
	
	
	public void onEnd(int newXPos) {
		// see {@code onStart}
		mouseMoveHandler.removeHandler();
		touchMoveHandler.removeHandler();
		// get the widget back into boundaries.
		int leftDelta = slider.getAbsoluteLeft() - movingWidget.getAbsoluteLeft();
    	if (leftDelta < 0) {
    		scroll(-leftDelta, false);
    	}
    	// note that the panel cannot be pushed out of the other boundary.
		// snap to tile
    	
    	// the median is the position of the center of the container panel on the moving panel
		int median = 
				slider.getOffsetWidth() / 2
				+ (slider.getAbsoluteLeft() - movingWidget.getAbsoluteLeft());
		
		// retrieve the closest item
		int numberOfItems = slider.size();
		int itemSize = movingWidget.getOffsetWidth() / numberOfItems;
		int itemNumber = (int) Math.floor(median / itemSize);
		NavigationItem item = slider.getWidget(itemNumber);
		// move to the right place
		int itemPosition = itemNumber * itemSize;
		// load the corresponding page
		scrollTo(itemPosition, false);
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		onStart(event.getChangedTouches().get(0).getRelativeX(event.getRelativeElement()));
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		onEnd(event.getChangedTouches().get(0).getRelativeX(event.getRelativeElement()));
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		onMove(event.getChangedTouches().get(0).getRelativeX(event.getRelativeElement()));
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		onMove(event.getRelativeX(event.getRelativeElement()));
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		onEnd(event.getRelativeX(event.getRelativeElement()));
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		onStart(event.getRelativeX(event.getRelativeElement()));
	}
}
