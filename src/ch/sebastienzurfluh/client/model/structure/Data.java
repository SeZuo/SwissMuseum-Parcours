package ch.sebastienzurfluh.client.model.structure;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;

/**
 * This is an immutable piece of data. It contains all the needed data to display a page and a menu.
 *
 * @author Sebastien Zurfluh
 *
 */
public class Data {
	private MenuData menuData;
	private PageData pageData;
	
	public Data(DataReference reference,
			DataType pageType,
			int priorityNumber,
			String pageTitle,
			String pageContentHeader,
			String pageContentBody,
			String menuTitle,
			String menuDescription,
			String squareImgURL,
			String rectangleImgURL) {
		menuData = new MenuData(reference, pageType, priorityNumber, menuTitle, menuDescription, squareImgURL, rectangleImgURL);
		pageData = new PageData(reference, pageType, pageTitle, pageContentHeader, pageContentBody);
	}
	
	public DataReference getReference() {
		return menuData.getReference();
	}
	
	public DataType getPageType() {
		return menuData.getPageType();
	}

	public int getPriorityNumber() {
		return menuData.getPriorityNumber();
	}

	public String getMenuTitle() {
		return menuData.getTitle();
	}
	
	public String getMenuDescription() {
		return menuData.getDescription();
	}
	
	public String getPageTitle() {
		return pageData.getTitle();
	}

	public String getPageContentHeader() {
		return pageData.getContentHeader();
	}
	
	public String getPageContentBody() {
		return pageData.getContentBody();
	}
	
	public String getSquareRectangleImgURL() {
		return menuData.getSquareImgURL();
	}

	public String getRectangleImgURL() {
		return menuData.getRectangleImgURL();
	}
}
