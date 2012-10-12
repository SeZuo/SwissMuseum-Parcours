/*
 * Copyright 2012 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu;

import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile.TileMode;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;

public class ModeSwapButton extends FocusPanel implements MouseUpHandler {
	public enum State {
		DETAILED, ICON_ONLY;
	}
	private State currentState;
	
	private Image toIconOnlyButton = new Image("resources/images/icons/to_icon_mode.gif");
	private Image toDetailedButton = new Image("resources/images/icons/to_detail_mode.gif");

	private TileMenu tileMenu;

	public ModeSwapButton(TileMenu tileMenu) {
		this.tileMenu = tileMenu;
		
		setState(State.DETAILED);
		
		addMouseUpHandler(this);
	}
	
	@Override
	public void onMouseUp(MouseUpEvent event) {
		switch(currentState) {
		case DETAILED:
			setState(State.ICON_ONLY);
			tileMenu.setMode(TileMode.ICON_ONLY);
			break;
		case ICON_ONLY:
			setState(State.DETAILED);
			tileMenu.setMode(TileMode.DETAILED);
			break;
		}
	}

	public void setState(State state) {
		switch(state) {
		case DETAILED:
			setWidget(toIconOnlyButton);
			currentState = State.DETAILED;
			break;
		case ICON_ONLY:
			setWidget(toDetailedButton);
			currentState = State.ICON_ONLY;
			break;
		}
		currentState = state;
	}
}
