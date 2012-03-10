package ch.sebastienzurfluh.client.model.structure;

/**
 * This is an mutable piece of data. It contains all the needed data to display a page or a menu.
 * @author Sebastien Zurfluh
 *
 */
public class Data {
	private int id, priorityNumber;
	private String title, contentHeader, contentBody;
	private String squareImgURL, rectangleImgURL;
	
	public Data(int id,
			int priorityNumber,
			String title,
			String contentHeader,
			String contentBody,
			String squareImgURL,
			String rectangleImgURL) {
		setId(id);
		setPriorityNumber(priorityNumber);
		setTitle(title);
		setContentHeader(contentHeader);
		setContentBody(contentBody);
		setSquareImgURL(squareImgURL);
		setRectangleImgURL(rectangleImgURL);
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
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
