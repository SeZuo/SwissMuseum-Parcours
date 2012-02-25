package ch.sebastienzurfluh.client.model;

import java.util.Iterator;
import java.util.LinkedList;

public class Tree<A> extends Data {
	LinkedList<A> children = new LinkedList<A>();
	
	public void addChild(A child) {
		children.add(child);
	}
	
	public A getChild(int childNumber) {
		return children.get(childNumber);
	}
	
	public Iterator<A> getChildrenIterator() {
		return children.iterator();
	}
}
