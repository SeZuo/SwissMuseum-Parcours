package ch.sebastienzurfluh.client.view.TileMenu;

import ch.sebastienzurfluh.client.view.TileMenu.Tile.TileMode;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

public class ModeSwapButton extends TwoStatesImageButton {
	public ModeSwapButton(String stylePrimaryName, final TileMenu tileMenu) {
		super(new ImageButton("resources/images/to_icon_mode.gif"),
				new ImageButton("resources/images/to_detail_mode.gif"));

		stateOneButton.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				System.out.println("Detail State Button: set icon mode.");
				tileMenu.setMode(TileMode.ICON_ONLY);
			}
		});
		stateTwoButton.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				System.out.println("Icon State Button: set detail mode.");
				tileMenu.setMode(TileMode.DETAILED);
			}
		});
	}
}
