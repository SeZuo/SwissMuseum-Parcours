package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.Data;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator.BookletNavigator;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.groupnavigator.GroupNavigator;

/**
 * This object watches the changes in the model to adapt the current screen disposition to the data.
 * 
 * @author Sebastien Zurfluh
 */
public class WidgetSwitcher implements Observer {
	private Model model;
	
	private GroupNavigator groupNavigator;
	private BookletNavigator bookletNavigator;
	private AnimationHelper animationHelper;
	

	public WidgetSwitcher(
			Model model,
			GroupNavigator groupNavigator,
			BookletNavigator bookletNavigator,
			AnimationHelper animationHelper) {
		
		this.model = model;
		this.groupNavigator = groupNavigator;
		this.bookletNavigator = bookletNavigator;
		this.animationHelper = animationHelper;
		
		model.currentPageDataObservable.subscribeObserver(this);
	}
	
	private DataType currentView = DataType.NONE;
	@Override
	public void notifyObserver(Observable source) {
		System.out.println("AnimatedMainPanel: notified of page change");
		if (model.getCurrentPageData().equals(Data.NONE)) {
			System.out.println("The new page is not null.");
			// There is no page, we want the group menu.
			if (model.getCurrentPageData().getReference().getType().equals(currentView))
				return;
			currentView = model.getCurrentPageData().getReference().getType();

			animationHelper.goTo(groupNavigator, Animation.SLIDE_REVERSE);
		} else {
			// A page exists! We want the page view.
			if (model.getCurrentPageData().getReference().getType().equals(currentView))
				return;
			currentView = model.getCurrentPageData().getReference().getType();
			
			animationHelper.goTo(bookletNavigator, Animation.SLIDE);
		}
	}

}