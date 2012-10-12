package ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget;

/**
 * Immutable token to store page elements.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageToken {
	private boolean isText = false;
	private boolean isResource = false;
	private ResourceWidget resourceWidget;
	private String text;

	public PageToken(ResourceWidget resourceWidget) {
		setResourceWidget(resourceWidget);
	}
	
	public PageToken(String text) {
		setText(text);
	}

	public boolean isText() {
		return isText;
	}
	
	public boolean isResource() {
		return isResource;
	}
	
	private void setResourceWidget(ResourceWidget resourceWidget) {
		this.resourceWidget = resourceWidget;
		this.isResource = true;
	}
	
	private void setText(String text) {
		this.text = text;
		this.isText = true;
	}

	public ResourceWidget getResourceWidget() {
		return resourceWidget;
	}
	
	public String getText() {
		return text;
	}
}
