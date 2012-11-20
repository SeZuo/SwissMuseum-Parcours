package ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.widget.Carousel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.pagewidget.LoadOnDemandPageWidget;

/**
 * The BookletNavigator listens to the changes in the set of pages in the current booklet and
 * display those in a magasine-like manner.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class BookletNavigator extends SimplePanel implements Observer {
	private Model model;
	private EventBus eventBus;
	private Carousel carousel;

	public BookletNavigator(Model model, EventBus eventBus) {
		this.model = model;
		this.eventBus = eventBus;
		
		carousel = new Carousel();
		add(carousel);
		  
		model.allPagesMenusInCurrentGroupObservable.subscribeObserver(this);
	}

	@Override
	public void notifyObserver(Observable source) {
		for(MenuData menuData : model.getAllPageMenusInCurrentGroup()) {
			ScrollPanel  scrollable = GWT.create(ScrollPanel.class);
			LoadOnDemandPageWidget page = new LoadOnDemandPageWidget(menuData, eventBus, model);
			scrollable.add(page);
			
			carousel.add(scrollable);
		}
	}
}
