package ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface;

import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface MenuButton extends HasClickHandlers {
	public DataReference getReference();
}