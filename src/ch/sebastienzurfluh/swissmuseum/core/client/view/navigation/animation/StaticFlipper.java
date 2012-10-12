package ch.sebastienzurfluh.swissmuseum.core.client.view.navigation.animation;

import ch.sebastienzurfluh.swissmuseum.core.client.view.navigation.NavigationSlider;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Animation for the scrolling effects.
 * Create a new object for each animated panel.
 *
 * @author Sebastien Zurfluh
 *
 */
public class StaticFlipper extends Animation  implements NavigationAnimator {
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
	@Deprecated
	public StaticFlipper(AbsolutePanel animatedPanel, Widget movingWidget, final NavigationSlider slider) {
		this.animatedPanel = animatedPanel;
		this.movingWidget = movingWidget;
		this.slider = slider;
		
		FocusPanel outerPrevious = new FocusPanel();
		SimplePanel innerPrevious = new SimplePanel();
		Label previous = new Label("<");
		
		outerPrevious.setStyleName("navigationSlider-button-inner");
		innerPrevious.setStyleName("navigationSlider-button-outer");
		previous.setStyleName("navigationSlider-button-text");
		
		outerPrevious.add(innerPrevious);
		innerPrevious.add(previous);
		
		outerPrevious.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (slider.getCurrentItemRank() == 0)
					return;
				
				// move to the right place
				setFocusWidget(slider.getCurrentItemRank()-1);

//				slider.getItem(slider.getCurrentItemRank()).ignite();
			}
		});
		animatedPanel.add(outerPrevious, 35, 35);
		
		
		FocusPanel outerNext = new FocusPanel();
		SimplePanel innerNext = new SimplePanel();
		Label next = new Label(">");
		
		outerNext.setStyleName("navigationSlider-button-inner");
		innerNext.setStyleName("navigationSlider-button-outer");
		next.setStyleName("navigationSlider-button-text");
		
		outerNext.add(innerNext);
		innerNext.add(next);
		
		next.setStyleName("navigationSlider-button-text");
		outerNext.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (slider.getCurrentItemRank() == slider.getItemCount()-1)
					return;
				
				// move to the right place
				setFocusWidget(slider.getCurrentItemRank()+1);
				
//				slider.getItem(slider.getCurrentItemRank()).ignite();
			}
		});
		animatedPanel.add(outerNext, 535, 35);
    }
	
	/**
	 * Updates settings that do not change often.
	 * 
	 * Use update in the following cases:
	 * - screen width changed
	 * - widget count changed
	 * - widget size changed
	 */
	public void update() {
		itemCount = slider.getItemCount();
		itemWidth = slider.getItem(0).getOffsetWidth();
		widgetWidth = slider.getOffsetWidth();
		
		updated = true;
	}
	/**
	 * The count of menu items.
	 */
	private int itemCount = 0;
	/**
	 * Pixel size of a benu item.
	 */
	private int itemWidth = 0;
	/**
	 * The width of the entire widget
	 */
	private int widgetWidth = 0;
	
	private boolean updated = false;
	/**
	 * Focus the widget at rank {@code number}.
	 * @param number -th widget
	 */
	public void setFocusWidget(int number) {
		if(!updated)
			update();

		slider.getItem(slider.getCurrentItemRank()).setFocus(false);

		slider.setCurrentItem(number);

		int itemPosition = number * itemWidth;
		scrollTo(itemPosition, SLOW);
		
		slider.getItem(number).setFocus(true);
	}

	int positionBeforeAnimation, delta;
    
    /**
     * @param newPos -ition absolute
     * @param duration in ms
     */
    private void scrollTo(int newPos, int duration) {
    	positionBeforeAnimation = movingWidget.getAbsoluteLeft() + animatedPanel.getAbsoluteLeft();
    	
    	// we scroll the opposite way of the direction we want
    	// in order to bring the widget to us.
    	this.delta = - newPos - positionBeforeAnimation;
    	
    	run(duration);
    }

    @Override
    protected void onUpdate(double progress) {
        int position = (int) (positionBeforeAnimation + (progress * delta));
        animatedPanel.setWidgetPosition(movingWidget, position, 0);
    }

	@Override
	public void onTouchStart(TouchStartEvent event) {
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
	}
}
