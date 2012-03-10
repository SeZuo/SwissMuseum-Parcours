package ch.sebastienzurfluh.client.model.structure;

public class Chapter extends Node<Page, Booklet> {

	public Chapter(int id, int priorityNumber, String title,
			String contentHeader, String contentBody, String squareImgURL,
			String rectangleImgURL) {
		super(id, priorityNumber, title, contentHeader, contentBody, squareImgURL,
				rectangleImgURL);
	}
	
}
