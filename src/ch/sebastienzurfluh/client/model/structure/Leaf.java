package ch.sebastienzurfluh.client.model.structure;

public class Leaf<Parent> extends Tree<Parent> {
	public Leaf(int id, int priorityNumber, String title, String contentHeader,
			String contentBody, String squareImgURL, String rectangleImgURL) {
		super(id, priorityNumber, title, contentHeader, contentBody, squareImgURL,
				rectangleImgURL);
	}
}
