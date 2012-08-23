package ch.sebastienzurfluh.client.view.menuinterface;

import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.model.structure.DataReference;

public interface MenuWidget extends EventBusListener {
	/**
	 * Sets a visual distinction on the selected menu.
	 * 
	 * @param menuData, if no item has to be selected the requested reference is SUPER
	 */
	void setFocus(DataReference menuData);
}