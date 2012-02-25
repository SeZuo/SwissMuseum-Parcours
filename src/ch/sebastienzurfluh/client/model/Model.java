package ch.sebastienzurfluh.client.model;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;


/**
 * Facade for the model package.
 *
 * @author Sebastien Zurfluh
 */
public class Model {
	EventBus eventBus;
	
	// Abstract higher node of the tree
	Tree modelTree = new Tree();
	
	public Model(EventBus eventBus) {
		// Populate the model?
		
		this.eventBus = eventBus;
	}

}
