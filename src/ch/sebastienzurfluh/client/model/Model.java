package ch.sebastienzurfluh.client.model;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.io.IOConnector;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;


/**
 * Facade for the model package.
 *
 * @author Sebastien Zurfluh
 */
public class Model {
	public Model(IOConnector ioConnector) {
	}

	/**
	 * List all the menus of the given type.
	 */
	public MenuData getMenus(DataType type) {
		return null;
		
	}
    
    /**
	  * Get the data associated with the given reference of a booklet, chapter, page or resource.
	  */
	public Data getAssociatedData(DataReference reference) {
		return modelTree;
		
	}
	
	/**
	 * List all the chapter data menus associated with a given booklet.
	 */
	public MenuData getMenus(DataType type, DataReference reference) {
		return null;
		
	}
	
	/**
	 * List all the booklets data menus.
	 */
	
	/**
	 * List all the page data menus associated with a given chapter.
	 */
}
