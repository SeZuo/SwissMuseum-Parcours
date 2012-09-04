package ch.sebastienzurfluh.client.view.pagewidget;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class PageToken {
	private boolean isText = false;
	private boolean isImage = false;
	private Image image;
	private String text;

	public PageToken(ResourceWidget resourceWidget) {
		setImage(image);
	}
	
	public PageToken(String text) {
		setText(text);
	}

	public boolean isText() {
		return isText;
	}
	
	public boolean isImage() {
		return isImage;
	}
	
	public void setImage(Image image) {
		this.image = image;
		this.isImage = true;
	}
	
	public void setText(String text) {
		this.text = text;
		this.isText = true;
	}

	public Image getImage() {
		return image;
	}
	
	public String getText() {
		return text;
	}
}
