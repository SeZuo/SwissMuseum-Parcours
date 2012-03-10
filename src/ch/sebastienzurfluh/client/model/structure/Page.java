package ch.sebastienzurfluh.client.model.structure;

public class Page extends Node<Resource, Chapter> {

	public Page(int id, int priorityNumber, String title, String contentHeader,
			String contentBody, String squareImgURL, String rectangleImgURL) {
		super(id, priorityNumber, title, contentHeader, contentBody, squareImgURL,
				rectangleImgURL);
	}

}
