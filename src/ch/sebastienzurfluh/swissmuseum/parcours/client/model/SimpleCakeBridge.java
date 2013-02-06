/*
 * Copyright 2012-2013 Sebastien Zurfluh
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

package ch.sebastienzurfluh.swissmuseum.parcours.client.model;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Connects to the Cake backend in order to get the remote DB content. Uses it to update the local
 * DB.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class SimpleCakeBridge {
	private String cakePath;
	private final static String CAKE_SUFFIX = ".json";
	private final static String CAKE_ARGS_SEPARATOR = "/";

	private static SimpleCakeBridge instance;
	public static SimpleCakeBridge getInstance() {
		if (instance == null)
			instance = new SimpleCakeBridge();
		return instance;
	}

	public SimpleCakeBridge() {
		this("http://www.sebastienzurfluh.ch/swissmuseumbooklets/cakePHPv2/index.php");
	}
	
	public SimpleCakeBridge(String cakePath) {
		this.cakePath = cakePath;
	}
	
	/**
	 * I WANT to send a function as a parameter.
	 */
	interface WithResult<ResponseType extends JavaScriptObject> {
		abstract void execute(ResponseType response);
	}
	
	public void syncDBThenCallback(final AsyncCallback<?> asyncCallback) {
		sendRequest("affiliations/listAll", new WithResult<JsArray<Affiliation>> () {
			@Override
			public void execute(JsArray<Affiliation> affiliations) {
				DatabaseHandle.updateAffiliations(affiliations);
				
				syncDBThenCallback2(asyncCallback);
			}
		});
	}
	
	public void syncDBThenCallback2(final AsyncCallback<?> asyncCallback) {
		sendRequest("groups/listAll", new WithResult<JsArray<Group>> () {
			@Override
			public void execute(JsArray<Group> groups) {
				DatabaseHandle.updateGroups(groups);
				
				syncDBThenCallback3(asyncCallback);
			}
		});
	}
	
	public void syncDBThenCallback3(final AsyncCallback<?> asyncCallback) {
		sendRequest("menus/listAll", new WithResult<JsArray<Menu>> () {
			@Override
			public void execute(JsArray<Menu> menus) {
				DatabaseHandle.updateMenus(menus);
				
				syncDBThenCallback4(asyncCallback);
			}
		});
	}
	
	public void syncDBThenCallback4(final AsyncCallback<?> asyncCallback) {
		sendRequest("page_elements/listAll", new WithResult<JsArray<Page>> () {
			@Override
			public void execute(JsArray<Page> pages) {
				DatabaseHandle.updatePages(pages);
				
				syncDBThenCallback5(asyncCallback);
			}
		});
	}
	
	public void syncDBThenCallback5(final AsyncCallback<?> asyncCallback) {
		sendRequest("resources/listAll", new WithResult<JsArray<Resource>> () {
			@Override
			public void execute(JsArray<Resource> resources) {
				DatabaseHandle.updateResources(resources);
				
				asyncCallback.onSuccess(null);
			}
		});
	}
	
	
	
	public <ResponseType extends JavaScriptObject> void sendRequest(
			String requestString, final WithResult<ResponseType> withResult) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				cakePath + CAKE_ARGS_SEPARATOR + requestString + CAKE_SUFFIX);
		
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request httpRequest, Throwable exception) {
					System.out.println("JSON request failure. " + exception.getLocalizedMessage());
					// we do nothing else than log
				}

				@SuppressWarnings("unchecked")
				public void onResponseReceived(Request httpRequest, Response response) {
					if (200 == response.getStatusCode()) {
						System.out.println("Got answer from async request.");

						withResult.execute((ResponseType) evalJson(response.getText().trim()));
					}
				}
			});
		} catch (Exception e) {
			System.out.println("JSON request error. " + e.getLocalizedMessage());
			// we do nothing else than log
		}
	}
	
	/**
	 * Converts json data into java objects.
	 * @param json
	 * @return a java converted
	 */
	private final native JavaScriptObject evalJson(String json) /*-{
		// injection safe
    	return JSON.parse(json);
  	}-*/;
	
	static class Affiliation extends JavaScriptObject {
		protected Affiliation() {}
		
		public final native String getId() /*-{
			return this.affiliations.id;
		}-*/;

		public final native String getPageId() /*-{
			return this.affiliations.page_id;
		}-*/;

		public final native String getGroupId() /*-{
			return this.affiliations.group_id;
		}-*/;

		public final native String getOrder() /*-{
			return this.affiliations.order;
		}-*/;
	}
	
	static class Group extends JavaScriptObject {
		protected Group() {}
		
		public final native String getId() /*-{
			return this.Group.id;
		}-*/;

		public final native String getName() /*-{
			return this.Group.name;
		}-*/;

		public final native String getMenuId() /*-{
			return this.Group.menu_id;
		}-*/;
	}
	
	static class Menu extends JavaScriptObject {
		protected Menu() {}
		
		public final native String getId() /*-{
			return this.Menu.id;
		}-*/;
		
		public final native String getTitle() /*-{
			return this.Menu.title;
		}-*/;

		public final native String getDescription() /*-{
			return this.Menu.description;
		}-*/;

		public final native String getThumbImgURL() /*-{
			return this.Menu.thumb_img_url;
		}-*/;

		public final native String getImgURL() /*-{
			return this.Menu.img_url;
		}-*/;
	}
	
	static class Page extends JavaScriptObject {
		protected Page() {}
		
		public final native String getId() /*-{
			return this.PageElement.id;
		}-*/;
	
		public final native String getTitle() /*-{
			return this.PageElement.title;
		}-*/;
	
		public final native String getSubtitle() /*-{
			return this.PageElement.subtitle;
		}-*/;
	
		public final native String getContent() /*-{
			return this.PageElement.content;
		}-*/;
		
		public final native String getMenuId() /*-{
			return this.PageElement.menu_id;
		}-*/;
	}
	
	static class Resource extends JavaScriptObject {
		protected Resource() {}
		
		public final native String getId() /*-{
			return this.Resource.id;
		}-*/;
		
		public final native String getTitle() /*-{
	    	return this.Resource.title;
		}-*/;

		public final native String getDescription() /*-{
	    	return this.Resource.description;
		}-*/;

		public final native String getURL() /*-{
		   	return this.Resource.url;
		}-*/;
		
		public final native String getType() /*-{
			return this.Resource.type;
		}-*/;
	}
}
