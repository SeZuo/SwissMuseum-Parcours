package ch.sebastienzurfluh.client.view.menuinterface;

import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.patterns.Observer;

public interface MenuWidget extends Observer {
	/**
	 * Sets a visual distinction on the selected menu.
	 * 
	 * @param menuData, if no item has to be selected the requested reference is SUPER
	 */
	void setFocus(DataReference menuData);
}