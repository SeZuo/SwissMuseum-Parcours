package ch.sebastienzurfluh.client.control.eventbus.events;

import ch.sebastienzurfluh.client.model.structure.DataReference;

public class ActionEvent extends IntentEvent {

	public ActionEvent(DataReference reference, Action action) {
		super(reference, action);
	}
	
	@Override
	public EventType getType() {
		return EventType.ACTION;
	}
}
