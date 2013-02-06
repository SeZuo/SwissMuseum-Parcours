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
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.ResourceType;
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
		abstract void processResponse(SQLResultSet<GenericRow> response);
	}
	
	/**
	 * Executes the {@code query} then returns the correct response asyncWithResult.processResponse
	 * @param query in sqlite3
	 * @param asyncWithResult the function to process the response/result.
	 */
	public <ResponseType> void asyncRequest(
			final String query,
			final WithResult<ResponseType> asyncWithResult) {
		DatabaseHandle.getInstance().readTransaction(new TransactionCallback() {
    		public void onTransactionStart(SQLTransaction tx) {
    			System.out.println("LocalStorageConnector: asyncRequest: started query >" + query);
    			tx.executeSql(query, null,
    					new StatementCallback<GenericRow>() {

    				@Override
    				public void onSuccess(
    						SQLTransaction transaction,
    						SQLResultSet<GenericRow> resultSet) {
    					System.out.println("LocalStorageConnector: asyncRequest: success of query >" + query);
    					asyncWithResult.processResponse(resultSet);
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
		
		System.out.println(">" + query);
		
		asyncRequest(query, new WithResult<Collection<MenuData>>() {
					@Override
					public void processResponse(
							SQLResultSet<GenericRow> response) {
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
				});
	}

	@Override
	public void asyncRequestGetFirstDataOfGroup(int referenceId,
			final AsyncCallback<Data> asyncCallback) {
		System.out.println("LocalStorageConnector: asyncRequestGetFirstDataOfGroup");
		
		final String query = "SELECT " +
				"pages.*, " +
				"menus.title AS menu_title, " +
				"menus.description AS menu_description, " +
				"menus.thumb_img_url, " +
				"menus.img_url, " +
				"affiliations.ordering AS \'order\' " +
				"FROM pages " +
				"JOIN menus ON menus.id = pages.menu_id " +
				"JOIN affiliations ON affiliations.page_id = pages.id " +
				"WHERE affiliations.group_id = \'" + referenceId + "\' " +
				"ORDER BY \'order\' ASC LIMIT 1";
		
		System.out.println(">" + query);
		
		asyncRequest(query, new WithResult<Data>() {
					@Override
					public void processResponse(
							SQLResultSet<GenericRow> response) {
						
						GenericRow genericRow = response.getRows().getItem(0);
						
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
					}});
	}

	@Override
	public void asyncRequestGetData(int referenceId,
			final AsyncCallback<Data> asyncCallback) {
		System.out.println("LocalStorageConnector: asyncRequestGetData");
		
		final String query = "SELECT " +
				"pages.*, " +
				"menus.title AS menu_title, " +
				"menus.description AS menu_description, " +
				"menus.thumb_img_url, " +
				"menus.img_url, " +
				"affiliations.ordering AS \'order\' " +
				"FROM pages " +
				"JOIN menus ON menus.id = pages.menu_id " +
				"JOIN affiliations ON affiliations.page_id = pages.id " +
				"WHERE pages.id = \'" + referenceId + "\' ";
		
		System.out.println(">" + query);
		
		asyncRequest(query, new WithResult<Data>() {
					@Override
					public void processResponse(SQLResultSet<GenericRow> response) {
						
						GenericRow genericRow = response.getRows().getItem(0);
						
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
						}});
	}

	@Override
	public void asyncRequestGetAllPageMenusFromGroup(int referenceId,
			final AsyncCallback<Collection<MenuData>> asyncCallback) {
		System.out.println("LocalStorageConnector: asyncRequestGetAllPageMenusFromGroup");
		
		final String query = "SELECT " +
				"pages.id AS page_id, " +
				"menus.title AS menu_title, " +
				"menus.description AS menu_description, " +
				"menus.thumb_img_url, " +
				"menus.img_url, " +
				"affiliations.ordering AS \'order\' " +
				"FROM pages " +
				"JOIN menus ON menus.id = pages.menu_id " +
				"JOIN affiliations ON affiliations.page_id = pages.id " +
				"WHERE affiliations.group_id = \'" + referenceId + "\' " +
				"ORDER BY \'order\' ASC";
		
		System.out.println(">" + query);
		
		asyncRequest(query, new WithResult<Collection<MenuData>>() {
					@Override
					public void processResponse(SQLResultSet<GenericRow> response) {
						System.out.print("LocalStorageConnector: " +
								"asyncRequestGetAllPageMenusFromGroup: processing response..." );
						
						Collection<MenuData> allGroupMenus = new LinkedList<MenuData>();
						
						System.out.println(" " + response.getRows().getLength() + " rows ");
						
    					for (GenericRow result : response.getRows()) {
    						allGroupMenus.add(new MenuData(
    								new DataReference(
    										DataType.PAGE,
    										result.getInt("page_id")),
    								result.getInt("order"),
    								result.getString("menu_title"),
    								result.getString("menu_description"),
    								result.getString("thumb_img_url"),
    								result.getString("img_url")));
						}
    					
    					System.out.println(
    							"LocalStorageConnector: asyncRequestGetAllPageMenusFromGroup:"
    							+ "response set size: " + allGroupMenus.size());
    					asyncCallback.onSuccess(allGroupMenus);
					}});
	}

	@Override
	public void asyncRequestResourceData(int referenceId,
			final AsyncCallback<ResourceData> asyncCallback) {
		System.out.println("LocalStorageConnector: asyncRequestResourceData");
		
		final String query = "SELECT * " +
				"FROM resources " +
				"WHERE resources.id = \'" + referenceId + "\' LIMIT 1";
		
		System.out.println(">" + query);
		
		asyncRequest(query, new WithResult<ResourceData>() {
					@Override
					public void processResponse(
							SQLResultSet<GenericRow> response) {
						GenericRow genericRow = response.getRows().getItem(0);
						System.out.println("ResourceData of type " +
								ResourceType.fromString(genericRow.getString("type")) + " created.");
						ResourceData resourceData = new ResourceData(
								new DataReference(DataType.RESOURCE, genericRow.getInt("id")),
								ResourceType.fromString(genericRow.getString("type")),
								genericRow.getString("title"),
								genericRow.getString("details"),
								genericRow.getString("url"));
    					
    					asyncCallback.onSuccess(resourceData);
						}});
	}

	@Override
	public void asyncRequestAllResourceData(
			final AsyncCallback<Collection<ResourceData>> asyncCallback) {
		
		System.out.println("LocalStorageConnector: asyncRequestAllResourceData");
		
		final String query = "SELECT * FROM resources ";
		
		System.out.println(">" + query);
		
		asyncRequest(query, new WithResult<Collection<ResourceData>>() {
			@Override
			public void processResponse(SQLResultSet<GenericRow> response) {
				Collection<ResourceData> allResourceData = new LinkedList<ResourceData>();
				for (GenericRow result : response.getRows())
					allResourceData.add(
							new ResourceData(
									new DataReference(DataType.RESOURCE, result.getInt("id")),
									ResourceType.fromString(result.getString("type")),
									result.getString("title"),
									result.getString("details"),
									result.getString("url")));

				asyncCallback.onSuccess(allResourceData);
			}});
	}

	@Override
	public void createResource(ResourceData resourceData,
			AsyncCallback<DataReference> asyncCallback) {
		assert false : this.getClass().toString() + " does not implement OConnector.";
	}

	@Override
	public void createPage(Data data, AsyncCallback<DataReference> asyncCallback) {
		assert false : this.getClass().toString() + " does not implement OConnector.";
	}

	@Override
	public void createGroup(MenuData groupData,
			AsyncCallback<DataReference> asyncCallback) {
		assert false : this.getClass().toString() + " does not implement OConnector.";
	}

	@Override
	public void delete(DataReference reference,
			AsyncCallback<Object> asyncCallback) {
		assert false : this.getClass().toString() + " does not implement OConnector.";
	}
}
