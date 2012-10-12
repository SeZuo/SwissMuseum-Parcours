package ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus;

import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.events.Action;
import ch.sebastienzurfluh.swissmuseum.parcours.client.control.eventbus.events.ActionEvent;

/**
 * Handles user actions (defined in the {@code Action} class).
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class ActionHandler implements EventBusListener {

	@Override
	public EventType getEventType() {
		return EventType.ACTION;
	}

	@Override
	public void notify(Event e) {
		if(! (e instanceof ActionEvent))
			return;
		
		ActionEvent action = (ActionEvent) e;
		
		switch(action.getAction()) {
		case CREATE:
			//TODO 1) Commit directly the new item to the DB
			//TODO 2) Add the new element to the current model in edit mode
			break;
		case REMOVE:
			//TODO 1) Commit instantly
			break;
		case MODIFY:
			//TODO 1) Commit instantly
			break;
		case NONE:
			break;
		default:
			break;
		}
		//TODO Handle the Action! (save to DB,...)
	}

}
