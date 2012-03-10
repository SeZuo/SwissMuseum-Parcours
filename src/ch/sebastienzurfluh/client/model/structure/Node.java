package ch.sebastienzurfluh.client.model.structure;

import java.util.Iterator;
import java.util.LinkedList;

public class Node<Child, Parent> extends Tree<Parent> {

	public Node(int id, int priorityNumber, String title, String contentHeader,
			String contentBody, String squareImgURL, String rectangleImgURL) {
		super(id, priorityNumber, title, contentHeader, contentBody, squareImgURL,
				rectangleImgURL);
	}
	
	LinkedList<Child> children = new LinkedList<Child>();
	
	public void addChild(Child child) {
		children.add(child);
	}
	
	public Child getChild(int childNumber) {
		return children.get(childNumber);
	}
	
	public Iterator<Child> getChildrenIterator() {
		return children.iterator();
	}

}
