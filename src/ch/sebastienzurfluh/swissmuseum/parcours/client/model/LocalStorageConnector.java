package ch.sebastienzurfluh.swissmuseum.parcours.client.model;

import java.util.Collection;
import java.util.LinkedList;

import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.SQLError;
import com.google.code.gwt.database.client.SQLResultSet;
import com.google.code.gwt.database.client.SQLTransaction;
import com.google.code.gwt.database.client.StatementCallback;
import com.google.code.gwt.database.client.TransactionCallback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.IConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.IOConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.Data;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.ResourceData;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.SimpleCakeBridge.WithResult;

/**
 * This {@link IConnector} use the local storage {@link DatabaseHandle} to retrieve it's data.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class LocalStorageConnector implements IOConnector {
	
	/**
	 * I WANT to send a function as a parameter.
	 */
	interface WithResult<ResponseType> {
		abstract void processResponse(
				SQLResultSet<GenericRow> response,
				AsyncCallback<ResponseType> asyncCallback);
	}
	
	/**
	 * Executes the {@code query} then returns the correct response asyncWithResult.processResponse
	 * @param query in sqlite3
	 * @param asyncWithResult the function to process the response/result.
	 */
	public <ResponseType> void asyncRequest(
			final String query,
			final WithResult<ResponseType> asyncWithResult,
			final AsyncCallback<ResponseType> asyncCallback) {
		DatabaseHandle.getInstance().readTransaction(new TransactionCallback() {
    		public void onTransactionStart(SQLTransaction tx) {
    			tx.executeSql(query, null,
    					new StatementCallback<GenericRow>() {

    				@Override
    				public void onSuccess(
    						SQLTransaction transaction,
    						SQLResultSet<GenericRow> resultSet) {
    					asyncWithResult.processResponse(resultSet, asyncCallback);
    				}

    				@Override
    				public boolean onFailure(
    						SQLTransaction transaction,
    						SQLError error) {
    					System.out.println("SQLError: " + error.getMessage());
    					return false;
    				}
    			});
    		}
    		public void onTransactionFailure(SQLError error) {
    			System.out.println("SQLError: " + error.getMessage());
    		}
    		public void onTransactionSuccess() {
    			// the DB has been updated successfully!
    		}
    	});
	}
	

	@Override
	public void asyncRequestAllGroupMenus(
			final AsyncCallback<Collection<MenuData>> asyncCallback) {
		System.out.println("LocalStorageConnector: asyncRequestAllGroupMenus");
		
		final String query = 
				"SELECT groups.id, menus.*" +
				"FROM menus JOIN groups " +
				"ON menus.id = groups.menu_id";
		
		asyncRequest(query, new WithResult<Collection<MenuData>>() {
					@Override
					public void processResponse(
							SQLResultSet<GenericRow> response,
							AsyncCallback<Collection<MenuData>> asyncCallback) {
						Collection<MenuData> allGroupMenus = new LinkedList<MenuData>();
    					for (GenericRow result : response.getRows()) {
    						allGroupMenus.add(new MenuData(
    								new DataReference(DataType.GROUP, result.getInt("id")),
    								0, result.getString("title"),
    								result.getString("description"),
    								result.getString("thumb_img_url"),
    								result.getString("img_url")));
						}
    					
    					asyncCallback.onSuccess(allGroupMenus);
					}
				}, asyncCallback);
	}

	@Override
	public void asyncRequestGetFirstDataOfGroup(int referenceId,
			AsyncCallback<Data> asyncCallback) {
		System.out.println("LocalStorageConnector: asyncRequestGetFirstDataOfGroup");
		
		final String query = "SELECT " +
				"pages.*, " +
				"menus.title AS menu_title, " +
				"menus.description AS menu_description, " +
				"menus.thumb_img_url, " +
				"menus.img_url, " +
				"\'affiliations.order\' AS \'order\' " +
				"FROM pages " +
				"JOIN menus ON menus.id = pages.menu_id " +
				"JOIN affiliations ON affiliations.page_id = pages.id " +
				"WHERE affiliations.group_id = \'" + referenceId + "\' " +
				"ORDER BY \'affiliations.order\' ASC LIMIT 1";
		
		System.out.println(">" + query);
		
		asyncRequest(query, new WithResult<Data>() {
					@Override
					public void processResponse(
							SQLResultSet<GenericRow> response,
							AsyncCallback<Data> asyncCallback) {
						
						GenericRow genericRow = response.getRows().getItem(0);
						
						System.out.print("Attributes names: ");
						for (String attrNames : response.getRows().getItem(0).getAttributeNames()) {
							System.out.print(attrNames + ", ");
						} System.out.println();
						
						Data firstDataOfGroup = new Data(
								new DataReference(DataType.PAGE, genericRow.getInt("id")),
								genericRow.getInt("order"),
								genericRow.getString("title"),
								genericRow.getString("subtitle"),
								genericRow.getString("content"),
								genericRow.getString("menu_title"),
								genericRow.getString("menu_description"),
								genericRow.getString("thumb_img_url"),
								genericRow.getString("img_url"));
    					
    					asyncCallback.onSuccess(firstDataOfGroup);
						}
					}, asyncCallback);
	}

	@Override
	public void asyncRequestGetData(int referenceId,
			AsyncCallback<Data> asyncCallBack) {
		//TODO
	}

	@Override
	public void asyncRequestGetAllPageMenusFromGroup(int referenceId,
			AsyncCallback<Collection<MenuData>> asyncCallBack) {
		//TODO
	}

	@Override
	public void asyncRequestResourceData(int referenceId,
			AsyncCallback<ResourceData> asyncCallback) {
		//TODO
	}

	@Override
	public void asyncRequestAllResourceData(
			AsyncCallback<Collection<ResourceData>> asyncCallback) {
		//TODO
	}

	@Override
	public void createResource(ResourceData resourceData,
			AsyncCallback<DataReference> asyncCallback) {
	}

	@Override
	public void createPage(Data data, AsyncCallback<DataReference> asyncCallback) {
	}

	@Override
	public void createGroup(MenuData groupData,
			AsyncCallback<DataReference> asyncCallback) {
	}

	@Override
	public void delete(DataReference reference,
			AsyncCallback<Object> asyncCallback) {
	}
}
