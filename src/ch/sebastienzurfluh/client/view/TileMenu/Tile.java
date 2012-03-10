package ch.sebastienzurfluh.client.view.TileMenu;

import ch.sebastienzurfluh.client.model.structure.Config;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This widget is a square icon menu.
 * 
 * Tile has two modes: "icon only mode" and "detailed mode".
 *
 * @author Sebastien Zurfluh
 *
 */
public class Tile extends HorizontalPanel {
	private Image tileImage;
	private VerticalPanel details;
	private String stylePrimaryName = "tile";
	
	Tile(String squareImgURL, String title, String description) {
		setStylePrimaryName(stylePrimaryName);
		
		tileImage = new Image(squareImgURL);
		tileImage.setStyleName(stylePrimaryName + "-" + "tileImage");
		tileImage.setAltText(title);
		add(tileImage);
		
		details = new VerticalPanel();
		Label titleLabel = new Label(title);
		titleLabel.setStyleName(stylePrimaryName + "-" + "tileTitle");
		details.add(titleLabel);
		Label descriptionLabel = new Label(description);
		descriptionLabel.setStyleName(stylePrimaryName + "-" + "tileDescription");
		details.add(descriptionLabel);
		add(details);
		
		
		// default mode is icon only.
		setIconOnlyMode();
	}
	
	private static String iconOnlyStyleDependentName = TileMode.ICON_ONLY.toString();
	private static String detailedStyleDependentName = TileMode.DETAILED.toString();

	private void setIconOnlyMode() {
		setStyleDependentName(detailedStyleDependentName, false);
		setStyleDependentName(iconOnlyStyleDependentName, true);
		
		details.setVisible(false);
	}
	
	private void setDetailedMode() {
		setStyleDependentName(iconOnlyStyleDependentName, false);
		setStyleDependentName(detailedStyleDependentName, true);
		
		details.setVisible(true);
	}
	
	public void setMode(TileMode mode) {
		switch (mode) {
		case ICON_ONLY:
			setIconOnlyMode();
			break;
		case DETAILED:
			setDetailedMode();
			break;
		default:
			break;
		}
	}
	
	public enum TileMode {
		ICON_ONLY("iconOnly"), DETAILED("detailed");
		
		private TileMode(String name) {
			this.name = name;
		}
		private final String name;
		public String toString() {
			return name;
		}
	}
	
	

}
