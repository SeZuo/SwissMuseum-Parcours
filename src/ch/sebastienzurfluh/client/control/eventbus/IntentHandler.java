package ch.sebastienzurfluh.client.control.eventbus;

import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.events.IntentEvent;
import ch.sebastienzurfluh.client.model.CMSModel;
import ch.sebastienzurfluh.client.model.structure.DataReference;

/**
 * Handles user intents (defined in {@Action} class).
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class IntentHandler implements EventBusListener {
	private CMSModel cmsModel;
	private EventBus eventBus;

	public IntentHandler(CMSModel cmsModel, EventBus eventBus) {
		this.cmsModel = cmsModel;
		this.eventBus = eventBus;
		
		eventBus.addListener(this);
	}

	@Override
	public EventType getEventType() {
		return EventType.INTENT;
	}

	@Override
	public void notify(Event e) {
		
		
		if (e instanceof IntentEvent) {
			IntentEvent intent = (IntentEvent) e;
			
			System.out.println("IntentHandler: " +
					intent.getAction() + " " + intent.getReference() + "");
			
			cmsModel.setCurrentIntent(intent.getAction(), intent.getReference());
			
			switch(intent.getAction()) {
			case MODIFY:
				System.out.println("IntentHandler: MODIFY loading.");
				if(intent.getReference().equals(DataReference.ALL_RESOURCES))
					cmsModel.loadAllResources();
				else
					cmsModel.load(intent.getReference());
				break;
			default:
				// Only modify needs a specific action as we need to load the corresponding data.
				break;
			}
		}
	}

}
