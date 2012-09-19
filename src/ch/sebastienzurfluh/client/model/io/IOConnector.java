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
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;

/**
 * Handles communication with the ADBMS.
 * @author Sebastien Zurfluh
 *
 */
public interface IOConnector {
	/**
	 * Get all the group menus.
	 * @param asyncCallBack says what to do with the result 
	 */
	void asyncRequestAllGroupMenus(AsyncCallback<Collection<MenuData>> asyncCallBack);

	/**
	 * Request for the first element in an ordered group.
	 * @param referenceId of the group 
	 * @param asyncCallBack says what to do with the result 
	 */
	void asyncRequestGetFirstDataOfGroup(int referenceId, AsyncCallback<Data> asyncCallBack);

	/**
	 * Request for the data of the given page.
	 * @param referenceId of the page 
	 * @param asyncCallBack says what to do with the result
	 */
	void asyncRequestGetData(int referenceId, AsyncCallback<Data> asyncCallBack);

	/**
	 * Request for the menu data of all the elements in the given ordered group.
	 * @param referenceId of the group 
	 * @param asyncCallBack says what to do with the result
	 */
	void asyncRequestGetAllPageMenusFromGroup(int referenceId,
			AsyncCallback<Collection<MenuData>> asyncCallBack);

	/**
	 * Request the resource.
	 * @param referenceId of the resource
	 * @param asyncCallBack says what to do with the result
	 */
	void asyncRequestResourceData(int referenceId,
			AsyncCallback<ResourceData> asyncCallback);

	/**
	 * Request all the resource in the DB.
	 * @param asyncCallBack says what to do with the result
	 */
	void asyncRequestAllResourceData(AsyncCallback<Collection<ResourceData>> asyncCallback);
}
