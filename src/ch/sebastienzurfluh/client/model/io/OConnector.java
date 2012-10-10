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

package ch.sebastienzurfluh.client.model.io;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;

/**
 * Handles communication with the ADBMS.
 * @author Sebastien Zurfluh
 *
 */
public interface OConnector {
	/**
	 * Creates a new resource.
	 * @param resourceData, don't bother about the reference id, it will be overridden.
	 * @param asyncCallBack says what to do when the action is completed. Called with the reference
	 * of the newly created resource.
	 */
	void createResource(ResourceData resourceData,
			AsyncCallback<DataReference> asyncCallback);
	
	/**
	 * Creates a new page.
	 * @param data all the required data to create a new page.
	 * @param asyncCallBack says what to do when the action is completed. Called with the reference
	 * of the newly created page.
	 */
	void createPage(Data data,
			AsyncCallback<DataReference> asyncCallback);
	
	/**
	 * Creates a new group.
	 * @param groupData all the required data to create a new group (only the menu is needed).
	 * @param asyncCallBack says what to do when the action is completed. Called with the reference
	 * of the newly created group.
	 */
	void createGroup(MenuData groupData,
			AsyncCallback<DataReference> asyncCallback);	
	
	
	/**
	 * Deletes recursively the object specified in the reference.
	 * @param reference of the object to delete
	 * @param asyncCallBack says what to do when the action is completed with no return type. In
	 * this case, there is no async callback argument.
	 */
	void delete(DataReference reference,
			AsyncCallback<Object> asyncCallback);
}
