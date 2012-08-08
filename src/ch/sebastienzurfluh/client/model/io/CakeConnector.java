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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.sebastienzurfluh.client.control.ModelAsyncPlug;
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
	private final static String CAKE_PATH = "www.sebastienzurfluh.ch/cakePHP/index.php/";
	private final static String CAKE_SUFFIX = ".json";
	private final static String CAKE_SEPARATOR = "/";
	
	private JsonpRequestBuilder jsonp;

	public CakeConnector() {
		jsonp = new JsonpRequestBuilder();
	}

	private void request(Requests request, String arg0) {
		request(request, arg0, "");
	}
	
	
	private void request(Requests request, String arg0, String arg1) {
		String url = CAKE_PATH + request.getURL()
				+ arg0;
		if(!arg1.isEmpty()) {
			url += CAKE_SEPARATOR + arg1;
		}
		url += CAKE_SUFFIX;
		
		jsonp.requestObject(url,
				new AsyncCallback<Feed>() {
			public void onFailure(Throwable throwable) {
				// I don't care.
			}

			public void onSuccess(Feed feed) {
				JsArray<Entry> entries = feed.getEntries();
				for (int i = 0; i < entries.length(); i++) {
					Entry entry = entries.get(i);

				}
			}
		});
	}

	class Feed extends JavaScriptObject {
		protected Feed() {}

		public final native JsArray<Entry> getEntries() /*-{
		     return this.feed.entry;
		   }-*/;
	}

	class Entry extends JavaScriptObject {
		protected Entry() {}

		public final native String getTitle() /*-{
		    return this.title.$t;
		}-*/;

		public final native String getWhere() /*-{
		    return this.gd$where[0].valueString;
		}-*/;

		public final native String getStartTime() /*-{
		    return this.gd$when ? this.gd$when[0].startTime : null;
		}-*/;

		public final native String getEndTime() /*-{
			return this.gd$when ? this.gd$when[0].endTime : null;
		}-*/;
	}


	private enum Requests {
		GETALLBOOKLETMENUS("booklets/listMenus"),
		GETBOOKLETDATAOF("booklets/getData/"),
		GETCHAPTERDATAOF("chapters/getData/"),
		GETPAGEDATAOF("page_elements/getData/"),
		GETRESOURCEDATAOF("resources/getData/"),
		GETSUBMENUOFBOOKLET("chapters/listAttachedToBooklet/"),
		GETSUBMENUOFCHAPTER("page_elements/listAttachedToChapter/"),
		GETSUBMENUOFPAGE("resources/listAttachedToPage/"),
		GETPARENTOF("page_datas/getParentOf/");


		String request;
		Requests(String request) {
			this.request = request;
		}

		public String getURL() {
			return request;
		}
	}


	@Override
	public void getAllBookletMenus(ModelAsyncPlug asyncPlug) {
	}

	@Override
	public void getBookletDataOf(ModelAsyncPlug asyncPlug, int referenceId) {
	}

	@Override
	public void getChapterDataOf(ModelAsyncPlug asyncPlug, int referenceId) {
	}

	@Override
	public void getPageDataOf(ModelAsyncPlug asyncPlug, int referenceId) {
	}

	@Override
	public void getRessourceDataOf(ModelAsyncPlug asyncPlug, int referenceId) {
	}

	@Override
	public void getSubMenusOfBooklet(ModelAsyncPlug asyncPlug, int referenceId) {
	}

	@Override
	public void getSubMenusOfChapter(ModelAsyncPlug asyncPlug, int referenceId) {
	}

	@Override
	public void getSubMenusOfPage(ModelAsyncPlug asyncPlug, int referenceId) {
	}

	@Override
	public void getParentOf(ModelAsyncPlug asyncPlug,
			DataReference childReference) {
	}
}