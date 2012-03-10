package ch.sebastienzurfluh.client.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.PageType;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.Leaf;
import ch.sebastienzurfluh.client.model.structure.Tree;


/**
 * Facade for the model package.
 *
 * @author Sebastien Zurfluh
 */
public class Model {
	EventBus eventBus;
	
	// Abstract higher node of the tree
	Tree<Data> modelTree = new Tree(0, 0, null, null, null, null, null);
	
	// List of data ordered by id.
	HashMap<Integer, Data> dataById = new HashMap<Integer, Data>();
	
	public Model(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void setModelData(Tree<Data> completeModelTree) {
		this.modelTree = completeModelTree;
	}
	
	private void recursivelyOrderById(Tree<Data> modelTree) {
		while (modelTree != null) {
			dataById.put(modelTree.getId(), modelTree);
			for (Iterator<Data> iterator = modelTree.getChildrenIterator(); iterator.hasNext();) {
				Data data = (Data) iterator.next();
				recursivelyOrderById(data);
			}
		}
	}
	
	private void recursivelyOrderById(Data data) {
		if (data instanceof Leaf) {
			recursivelyOrderById((Leaf) data);
		} else if (data instanceof Tree<?>) {
			recursivelyOrderById((Tree<Data>) data);
		}
	}

	private void recursivelyOrderById(Leaf modelLeaf) {
		dataById.put(modelTree.getId(), modelLeaf);
	}

	/**
	 * Get the parent id of the given page.
	 * @param pageId the id of the page which parent to get.
	 * @return the id of the direct parent of this page.
	 */
	public int getParent(int pageId) {
		Data data = dataById.get(pageId);
		if (data instanceof Leaf) {
			Leaf dataLeaf = (Leaf) data;
			dataLeaf.getParent();
		} else if (data instanceof Tree<?>) {
			Tree<Data> dataTree = (Tree<Data>) data;
			dataTree.getParent();
		}
		return null;
	}
	
	/**
	 * Get the id of the given page's children.
	 * @param pageId the id of the page which children to get.
	 * @return a list of ids of the direct children of this page.
	 */
	public LinkedList<Integer> getChildren(int pageId) {
		return null;
		
	}
	
	/**
	 * Get the parent id of the given page.
	 * @param pageId the id of the page which parent to get.
	 * @return the id of the direct parent of this page.
	 */
	public LinkedList<Integer> getSiblings(int pageId) {
		return null;
	}

	/**
	 * Get the data element corresponding to the given id.
	 * @param id, the id of the element to fetch.
	 * @return the corresponding element.
	 */
	public Data getData(Integer id) {
		return dataById.get(id);
	}

	public PageType getPageTypeOf(int pageId) {
		return null;
	}
}
