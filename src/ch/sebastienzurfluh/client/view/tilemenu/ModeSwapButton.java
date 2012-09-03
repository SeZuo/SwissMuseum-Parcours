package ch.sebastienzurfluh.client.view.tilemenu;

import ch.sebastienzurfluh.client.view.tilemenu.Tile.TileMode;

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
