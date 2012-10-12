package ch.sebastienzurfluh.swissmuseum.parcours.client.view.cms.menu;

import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.parcours.client.patterns.Observable;

public class PageTreeWidget extends TreeWidget {

	public PageTreeWidget(
			DataType type,
			EventBus eventBus,
			CMSModel model) {
		super(type, eventBus, model);
		
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
				updateFocusedTile(model.getCurrentPageData().getReference());
			}
		}
	}
}
