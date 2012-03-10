package ch.sebastienzurfluh.client.model.structure;

public class Booklet extends Node<Chapter, Data> {

	public Booklet(int id, int priorityNumber, String title,
			String contentHeader, String contentBody, String squareImgURL,
			String rectangleImgURL) {
		super(id, priorityNumber, title, contentHeader, contentBody, squareImgURL,
				rectangleImgURL);
	}
	
}
