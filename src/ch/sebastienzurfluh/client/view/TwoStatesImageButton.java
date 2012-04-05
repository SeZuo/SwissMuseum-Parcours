package ch.sebastienzurfluh.client.view;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.FocusPanel;

public class TwoStatesImageButton extends FocusPanel implements MouseUpHandler {
	public enum State {
		ONE, TWO;
	}
	State currentState;
	
	ImageButton stateOneButton, stateTwoButton;

	public TwoStatesImageButton(ImageButton stateOneButton, ImageButton stateTwoButton) {
		this.stateOneButton = stateOneButton;
		this.stateTwoButton = stateTwoButton;
		
		setState(State.ONE);
		
		addMouseUpHandler(this);
	}
	
	@Override
	public void onMouseUp(MouseUpEvent event) {
		switch(currentState) {
		case ONE:
			setState(State.TWO);
			break;
		case TWO:
			setState(State.ONE);
			break;
		}
	}

	public void setState(State state) {
		switch(state) {
		case ONE:
			setWidget(stateOneButton);
			currentState = State.ONE;
			break;
		case TWO:
			setWidget(stateTwoButton);
			currentState = State.TWO;
			break;
		}
	}
}
