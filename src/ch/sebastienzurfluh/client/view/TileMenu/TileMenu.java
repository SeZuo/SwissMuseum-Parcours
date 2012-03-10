package ch.sebastienzurfluh.client.view.TileMenu;

import ch.sebastienzurfluh.client.view.TileMenu.Tile.TileMode;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * This widget uses several {@link Tile}s to create a menu.
 * @author Sebastien Zurfluh
 *
 */
public class TileMenu extends VerticalPanel {
	private String stylePrimaryName = "tileMenu";
	private FlowPanel tilePanel;
	
	public TileMenu(String title) {
		setStyleName(stylePrimaryName);
		
		HorizontalPanel firstLine = new HorizontalPanel();
		Label titleLabel = new Label(title);
		titleLabel.setStyleName(stylePrimaryName + "-" + "title");
		firstLine.add(titleLabel);
		Label button = new Label("details button");
		firstLine.add(button);
		add(firstLine);
		
		tilePanel = new FlowPanel();
		tilePanel.setStyleName(stylePrimaryName + "-" + "tileList");
		add(tilePanel);
	}
	
	public void addTile(String squareImgURL, String title, String description, int priorityNumber) {
		tilePanel.insert(new Tile(squareImgURL, title, description), priorityNumber);
	}
	
	public void clearTiles() {
		//TODO test that. It didn't work before... maybe use a new tilePanel.
		tilePanel.clear();
	}
	
	public void setMode(TileMode mode) {
		for (Widget widget : tilePanel) {
			if (widget instanceof Tile) {
				((Tile)widget).setMode(mode);
			}
		}
	}

}
