package ch.sebastienzurfluh.client.model.structure;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.gwt.http.client.URL;

public class Tree<Parent> extends Data {
	public Tree(int id, int priorityNumber, String title, String contentHeader,
			String contentBody, String squareImgURL, String rectangleImgURL) {
		super(id, priorityNumber, title, contentHeader, contentBody, squareImgURL,
				rectangleImgURL);
	}
	
	LinkedList<Parent> parents = new LinkedList<Parent>();
	
	public void addParent(Parent child) {
		parents.add(child);
	}
	
	public Parent getParent(int childNumber) {
		return parents.get(childNumber);
	}
	
	public Iterator<Parent> getParentIterator() {
		return parents.iterator();
	}
}
