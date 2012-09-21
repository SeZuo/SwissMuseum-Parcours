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
import java.util.LinkedList;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.ResourceType;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;

/**
 * Handles communication with CakePHP which in turn handles the ADBMS.
 * @author Sebastien Zurfluh
 *
 */
public class CakeConnector implements IOConnector {
	private final static String CAKE_PATH = "http://www.sebastienzurfluh.ch/swissmuseumbooklets/cakePHPv2/index.php/";
	private final static String CAKE_SUFFIX = ".json";
	private final static String CAKE_ARGS_SEPARATOR = "/";

	public CakeConnector() {
	}
//	/**
//	 * The queue will prevent multiple identical requests to be sent.
//	 * The request will be processed once, then update all the sources.
//	 */
//	HashMap<Requests, HashMap<Integer, Requests>> requestsQueue
//	= new HashMap<Requests, HashMap<Integer, Requests>>(Requests.values().length);
	private <T> void asyncRequest (
			final Requests request,
			final int referenceId, 
			String args,
			final AsyncCallback<T> callback) {

		final StringBuilder url = new StringBuilder(CAKE_PATH + request.getURL());

		url.append(CAKE_ARGS_SEPARATOR).append(args);
		url.append(CAKE_ARGS_SEPARATOR).append(referenceId);

		url.append(CAKE_SUFFIX);

		System.out.println("Making async request on "+url);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url.toString());

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request httpRequest, Throwable exception) {
					System.out.println("JSON request failure. " + exception.getLocalizedMessage());
					// we do nothing else than log
					callback.onFailure(exception);
				}

				@SuppressWarnings("unchecked")
				public void onResponseReceived(Request httpRequest, Response response) {
					if (200 == response.getStatusCode()) {
						System.out.println("Got answer from async request.");

						JsArray<Entry> entries = evalJson(response.getText().trim());

						// BLACKBOX!!!
						DataType dataType = DataType.PAGE;
						
						switch(request) {
						case GETALLGROUPMENUS:
							dataType = DataType.GROUP;
						case GETALLPAGEMENUSFROMGROUP:
							// menu collection
							LinkedList<MenuData> dataList = new LinkedList<MenuData>();
							for (int i = 0; i < entries.length(); i++) {
								Entry entry = entries.get(i);
								dataList.add(parseMenuData(entry, referenceId, dataType));
							}
							// give callback
							callback.onSuccess((T) dataList);
							break;
						case GETDATA:
						case GETFIRSTDATAOFGROUP:
							// single data
							Data parsedData = parseData(entries.get(0), referenceId, DataType.PAGE);
							callback.onSuccess((T) parsedData);
							break;
						case GETRESOURCE:
							ResourceData parsedData2 =
									parseResourceData(entries.get(0),
											referenceId,
											ResourceType.IMAGE);
							callback.onSuccess((T) parsedData2);
						}
					}
				}
			});
		} catch (Exception e) {
			callback.onFailure(e);
		}
	}

	/**
	 * Converts json data into java objects.
	 * @param json
	 * @return a java converted
	 */
	private final native JsArray<Entry> evalJson(String json) /*-{
		// injection safe
    	return JSON.parse(json);
  	}-*/;


	private Data parseData(Entry entry, int referenceId, DataType expectedDataType) {
		return new Data(
				getDataReference(expectedDataType, entry),
				Integer.parseInt(entry.getMenuPriorityNumber()), 
				entry.getPageTitle(),
				entry.getPageContentHeader(),
				entry.getPageContentBody(),
				entry.getMenuTitle(),
				entry.getMenuDescription(),
				entry.getMenuTileImgURL(),
				entry.getMenuSliderImgURL());
	}

	private MenuData parseMenuData(Entry entry, int referenceId, DataType expectedDataType) {
		return new MenuData(
				getDataReference(expectedDataType, entry),
				Integer.parseInt(entry.getMenuPriorityNumber()),
				entry.getMenuTitle(),
				entry.getMenuDescription(),
				entry.getMenuTileImgURL(),
				entry.getMenuSliderImgURL());
	}

	private ResourceData parseResourceData(Entry entry, int referenceId, ResourceType expectedResourceType) {
		return new ResourceData(
				new DataReference(DataType.RESOURCE, referenceId),
				expectedResourceType,
				entry.getResourceTitles(),
				entry.getResourceDetails(),
				entry.getResourceURL());
	}

	private DataReference getDataReference(DataType type, Entry entry) {
		switch(type) {
		case GROUP:
			return new DataReference(type, Integer.parseInt(entry.getGroupReference()));
		case PAGE:
			return new DataReference(type, Integer.parseInt(entry.getPageReference()));
		case RESOURCE:
			return new DataReference(type, Integer.parseInt(entry.getResourceReference()));
		default:
			return DataReference.NONE;
		}
	}

//	/**
//	 * Caches
//	 */
//	private Cache<Integer, Collection<MenuData>> menuDataCache =
//			new SimpleCache<Integer, Collection<MenuData>>();
//
//	private Cache<Integer, Data> dataCache =
//			new SimpleCache<Integer, Data>();
//
//	private Cache<DataReference, Data> referencedDataCache =
//			new SimpleCache<DataReference, Data>();
//
//	private Cache<Integer, ResourceData> resourceDataCache =
//			new SimpleCache<Integer, ResourceData>();


	/**
	 *  list all possible requests and their command (url) in cake
	 */
	private enum Requests {
		GETALLGROUPMENUS("menus/listAllGroupMenus"),
		GETALLPAGEMENUSFROMGROUP("menus/listAllPageMenusFromGroup"),
		GETFIRSTDATAOFGROUP("page_elements/getFirstPageDataFromGroup"),
		GETDATA("page_elements/getData"),
		GETRESOURCE("resources/get");

		String request;
		Requests(String request) {
			this.request = request;
		}

		public String getURL() {
			return request;
		}
	}

	@Override
	public void asyncRequestAllGroupMenus(
			AsyncCallback<Collection<MenuData>> asyncCallback) {
		asyncRequest(Requests.GETALLGROUPMENUS, 0, "", asyncCallback);
	}

	@Override
	public void asyncRequestGetFirstDataOfGroup(int referenceId,
			AsyncCallback<Data> asyncCallback) {
		asyncRequest(Requests.GETFIRSTDATAOFGROUP, referenceId, "", asyncCallback);
	}

	@Override
	public void asyncRequestGetData(int referenceId,
			AsyncCallback<Data> asyncCallback) {
		asyncRequest(Requests.GETDATA, referenceId, "", asyncCallback);
	}

	@Override
	public void asyncRequestGetAllPageMenusFromGroup(int referenceId,
			AsyncCallback<Collection<MenuData>> asyncCallback) {
		asyncRequest(Requests.GETALLPAGEMENUSFROMGROUP, referenceId, "", asyncCallback);
	}
	@Override
	public void asyncRequestResourceData(int referenceId,
			AsyncCallback<ResourceData> asyncCallback) {
		asyncRequest(Requests.GETRESOURCE, referenceId, "", asyncCallback);
	}
	@Override
	public void asyncRequestAllResourceData(
			AsyncCallback<Collection<ResourceData>> asyncCallback) {
		//TODO
	}
}

class Entry extends JavaScriptObject {
	protected Entry() {}



	// Groups
	
	public final native String getGroupReference() /*-{
		return this.groups.id;
	}-*/;
	
	public final native String getGroupName() /*-{
		return this.groups.name;
	}-*/;



	// Page
	
	public final native String getPageReference() /*-{
		return this.pages.id;
	}-*/;
	
	public final native String getPageTitle() /*-{
		return this.pages.title;
	}-*/;

	public final native String getPageContentHeader() /*-{
		return this.pages.subtitle;
	}-*/;

	public final native String getPageContentBody() /*-{
		return this.pages.content;
	}-*/;

	// Menu

	public final native String getMenuPriorityNumber() /*-{
		return this.affiliations == null ? "0" : this.affiliations.order;
	}-*/;

	public final native String getMenuTitle() /*-{
		return this.menus.title;
	}-*/;

	public final native String getMenuDescription() /*-{
		return this.menus.description;
	}-*/;

	public final native String getMenuTileImgURL() /*-{
		return this.menus.thumb_img_url;
	}-*/;

	public final native String getMenuSliderImgURL() /*-{
		return this.menus.img_url;
	}-*/;

	// Resource
	
	public final native String getResourceReference() /*-{
		return this.resources.id;
	}-*/;
	
	public final native String getResourceTitles() /*-{
    	return this.resources.title;
	}-*/;

	public final native String getResourceDetails() /*-{
    	return this.resources.description;
	}-*/;

	public final native String getResourceURL() /*-{
	   	return this.resources.url;
	}-*/;
}