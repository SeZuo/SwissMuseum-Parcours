/*
 * Copyright 2012 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.client.model;

import java.util.Collection;

import ch.sebastienzurfluh.client.control.ModelAsyncPlug;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.io.IOConnector;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;


/**
 * Facade for the model package.
 *
 * @author Sebastien Zurfluh
 */
public class Model {
	IOConnector connector;
	
	public Model(IOConnector connector) {
		this.connector = connector;
	}

	/**
	 * List all the menus of the given type.
	 */
	public void getMenus(ModelAsyncPlug<Collection<MenuData>> asyncPlug, DataType type) {
		switch(type) {
		case BOOKLET:
			connector.getAllBookletMenus(asyncPlug);
			break;
		case CHAPTER:
		case PAGE:
		case RESOURCE:
			break;
		default:
			throw new Error("Impossible switch case in getMenus");
		}
	}
	
    /**
	  * Asynchronously returns data associated with the given reference of a booklet,
	  * chapter, page or resource.
	  */
	public void getAssociatedData(ModelAsyncPlug<Data> asyncPlug, DataReference reference) {
		switch(reference.getType()) {
		case BOOKLET:
			connector.getBookletDataOf(asyncPlug, reference.getReferenceId());
			break;
		case CHAPTER:
			connector.getChapterDataOf(asyncPlug, reference.getReferenceId());
			break;
		case PAGE:
			connector.getPageDataOf(asyncPlug, reference.getReferenceId());
			break;
		case RESOURCE:
			break;
		default:
			throw new Error("Impossible default switch case.");
		}
	}
	
	public void getResourceData(ModelAsyncPlug<ResourceData> asyncPlug, DataReference reference) {
		connector.getRessourceDataOf(asyncPlug, reference.getReferenceId());
	}
	
	/**
	 * List all the sub-menus associated with a given referenced data object.
	 */
	public void getSubMenus(ModelAsyncPlug<Collection<MenuData>> asyncPlug, DataType type, DataReference reference) {
		switch(reference.getType()) {
		case BOOKLET:
			connector.getSubMenusOfBooklet(asyncPlug, reference.getReferenceId());
			break;
		case CHAPTER:
			connector.getSubMenusOfChapter(asyncPlug, reference.getReferenceId());
			break;
		case PAGE:
			connector.getSubMenusOfPage(asyncPlug, reference.getReferenceId());
			break;
		case RESOURCE:
		default:
			throw new Error("Impossible switch case in getSubMenus");
		}
	}
	
	/**
	 * Asynchronously returns the parent reference
	 * 
	 * @param reference is the child's reference
	 */
	public void getParentOf(ModelAsyncPlug<Data> asyncPlug, DataReference reference) {
		connector.getParentOf(asyncPlug, reference);
	}
}
