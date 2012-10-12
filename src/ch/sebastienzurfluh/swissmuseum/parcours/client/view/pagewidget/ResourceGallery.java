package ch.sebastienzurfluh.swissmuseum.parcours.client.view.pagewidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class ResourceGallery extends PopupPanel implements ClickHandler {
	public ResourceGallery(String string, String titleString, String descriptionString) {
		FlowPanel popup = new FlowPanel();
		
		Image imageClone = new Image();
		imageClone.setStyleName("resourceGallery-image");
		
		setStyleName("resourceGallery");
		
		imageClone.setUrl(string);
		
		setGlassEnabled(true);
		
		popup.add(imageClone);
		
		Label title = new Label(titleString);
		Label details = new Label(descriptionString);
		
		title.setStyleName("resourceGallery-image-title");
		details.setStyleName("resourceGallery-image-details");
		
		popup.add(title);
		popup.add(details);
		
		add(popup);
		
		imageClone.addClickHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		hide();
	}
}
