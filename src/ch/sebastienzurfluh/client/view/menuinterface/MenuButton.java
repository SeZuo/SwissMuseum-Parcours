package ch.sebastienzurfluh.client.view.menuinterface;

import ch.sebastienzurfluh.client.model.structure.DataReference;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface MenuButton extends HasClickHandlers {
	public DataReference getReference();
}