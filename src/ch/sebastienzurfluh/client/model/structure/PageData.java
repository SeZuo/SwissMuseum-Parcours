package ch.sebastienzurfluh.client.model.structure;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;

/**
 * This is an immutable piece of data. It contains all the needed data to display A PAGE.
 *
 * @author Sebastien Zurfluh
 *
 */
public class PageData {
	private DataReference reference;
	private String title, contentHeader, contentBody;
	private DataType pageType;
	
	public PageData(DataReference reference,
			DataType pageType,
			String title,
			String contentHeader,
			String contentBody) {
		setReference(reference);
		setPageType(pageType);
		setTitle(title);
		setContentHeader(contentHeader);
		setContentBody(contentBody);
	}
	
	// ************************ GETTERS AND SETTERS ***************************** //

	public DataReference getReference() {
		return reference;
	}

	private void setReference(DataReference reference) {
		this.reference = reference;
	}

	private void setPageType(DataType pageType) {
		this.pageType = pageType;
	}
	
	public DataType getPageType() {
		return pageType;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getContentHeader() {
		return contentHeader;
	}

	private void setContentHeader(String contentHeader) {
		this.contentHeader = contentHeader;
	}

	public String getContentBody() {
		return contentBody;
	}

	private void setContentBody(String contentBody) {
		this.contentBody = contentBody;
	}
}
