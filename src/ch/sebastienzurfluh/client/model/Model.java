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

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.io.IOConnector;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;


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
	public Collection<MenuData> getMenus(DataType type) {
		switch(type) {
		case BOOKLET:
			return connector.getAllBookletMenus();
		case CHAPTER:
		case PAGE:
		case RESOURCE:
		default:
			throw new Error("Impossible switch case in getMenus");
		}
		
		
	}
    
    /**
	  * @return data associated with the given reference of a booklet, chapter, page or resource.
	  */
	public Data getAssociatedData(DataReference reference) {
		switch(reference.getType()) {
		case BOOKLET:
			return connector.getBookletDataOf(reference.getReferenceId());
		case CHAPTER:
			return connector.getChapterDataOf(reference.getReferenceId());
		case PAGE:
			return connector.getPageDataOf(reference.getReferenceId());
		case RESOURCE:
			return connector.getRessourceDataOf(reference.getReferenceId());
		default:
			throw new Error("Impossible default switch case.");
		}
	}
	
	/**
	 * List all the sub-menus associated with a given referenced data object.
	 */
	public Collection<MenuData> getSubMenus(DataType type, DataReference reference) {
		switch(reference.getType()) {
		case BOOKLET:
			return connector.getSubMenusOfBooklet(reference.getReferenceId());
		case CHAPTER:
			return connector.getSubMenusOfChapter(reference.getReferenceId());
		case PAGE:
			return connector.getSubMenusOfPage(reference.getReferenceId());
		case RESOURCE:
		default:
			throw new Error("Impossible switch case in getSubMenus");
		}
	}
	
	/**
	 * @param reference is the child's reference
	 * @return the parent reference
	 */
	public Data getParentOf(DataReference reference) {
		return connector.getParentOf(reference);
	}
}
