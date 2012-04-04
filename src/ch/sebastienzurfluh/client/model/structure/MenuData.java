package ch.sebastienzurfluh.client.model.structure;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;

/**
 * This is an immutable piece of data. It contains all the needed data to display A MENU.
 *
 * @author Sebastien Zurfluh
 *
 */
public class MenuData {
	private DataReference reference;
	private int priorityNumber;
	private String title, description;
	private String squareImgURL, rectangleImgURL;
	private DataType pageType;
	
	public MenuData(DataReference reference,
			DataType pageType,
			int priorityNumber,
			String title,
			String description,
			String squareImgURL,
			String rectangleImgURL) {
		setReference(reference);
		setPageType(pageType);
		setPriorityNumber(priorityNumber);
		setTitle(title);
		setDescription(description);
		setSquareImgURL(squareImgURL);
		setRectangleImgURL(rectangleImgURL);
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

	public int getPriorityNumber() {
		return priorityNumber;
	}

	private void setPriorityNumber(int priorityNumber) {
		this.priorityNumber = priorityNumber;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	public String getSquareImgURL() {
		return squareImgURL;
	}

	private void setSquareImgURL(String squareImgURL) {
		this.squareImgURL = squareImgURL;
	}

	public String getRectangleImgURL() {
		return rectangleImgURL;
	}

	private void setRectangleImgURL(String rectangleImgURL) {
		this.rectangleImgURL = rectangleImgURL;
	}
}
