package ch.sebastienzurfluh.client.view.cms;

import com.google.gwt.event.dom.client.ClickHandler;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.patterns.Observable;

public class PageTreeWidget extends TreeWidget {

	public PageTreeWidget(
			DataType type,
			EventBus eventBus,
			Model model,
			ClickHandler pageRequestHandler) {
		super(type, eventBus, model, pageRequestHandler);
		
		model.allPagesMenusInCurrentGroupObservable.subscribeObserver(this);
		model.currentPageDataObservable.subscribeObserver(this);
	}
	
	
	
	@Override
	public void notifyObserver(Observable source) {
		super.notifyObserver(source);
		
		if (source.equals(model.allPagesMenusInCurrentGroupObservable)) {
			// reload all the group pages
			entries.clear();
			if(model.getAllPageMenusInCurrentGroup() == null) {
				this.setVisible(false);
			} else {
				this.setVisible(true);
				for(MenuData menuData : model.getAllPageMenusInCurrentGroup()) {
					createNewTile(menuData);
				}
			}
		} else if (source.equals(model.currentPageDataObservable)) {
			// set the current focus on the right object
			if(model.getCurrentPageData() == null) {
				this.setVisible(false);
			} else {
				this.setVisible(true);
				updateFocusedTile();
			}
		}
	}
}
