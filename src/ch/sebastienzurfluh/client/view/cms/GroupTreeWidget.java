package ch.sebastienzurfluh.client.view.cms;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.patterns.Observable;

public class GroupTreeWidget extends TreeWidget {

	public GroupTreeWidget(DataType type, EventBus eventBus, Model model) {
		super(type, eventBus, model);
		
		model.allGroupsMenusChangesObservable.subscribeObserver(this);
	}
	
	@Override
	public void notifyObserver(Observable source) {
		super.notifyObserver(source);
		
		model.allGroupsMenusChangesObservable.subscribeObserver(this);
		if (source.equals(model.currentGroupMenuObservable)) {
			
		} else if (source.equals(model.currentPageDataObservable)) {
			
		}
	}

}
