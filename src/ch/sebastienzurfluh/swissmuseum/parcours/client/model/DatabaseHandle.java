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

import ch.sebastienzurfluh.swissmuseum.parcours.client.control.PhoneGapHandle;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.SimpleCakeBridge.Affiliation;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.SimpleCakeBridge.Group;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.SimpleCakeBridge.Menu;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.SimpleCakeBridge.Page;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.SimpleCakeBridge.Resource;

import com.google.code.gwt.database.client.Database;
import com.google.code.gwt.database.client.DatabaseException;
import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.SQLError;
import com.google.code.gwt.database.client.SQLResultSet;
import com.google.code.gwt.database.client.SQLTransaction;
import com.google.code.gwt.database.client.StatementCallback;
import com.google.code.gwt.database.client.TransactionCallback;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwtphonegap.client.connection.Connection;

/**
 * A singleton of the {@link Database} main object, as you should always use the same object
 * anyway.
 *
 * @author Sebastien Zurfluh
 *
 */
public class DatabaseHandle {
	private static Database instance = Database.openDatabase(
			"swissmuseumbooklets",
			"1.0",
			"Database",
			10000000);
	
	public static Database getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param asyncCallback
	 * @throws DatabaseException
	 */
	public static void sync(final AsyncCallback<?> asyncCallback) {
		DatabaseHandle.getInstance().readTransaction(new TransactionCallback() {
		    public void onTransactionStart(SQLTransaction tx) {
		    	String queries[] = {"CREATE TABLE IF  NOT EXISTS affiliations ("
		        		+ "  id INTEGER PRIMARY KEY,"
		        		+ "  page_id INTEGER NOT NULL,"
		        		+ "  group_id INTEGER NOT NULL,"
		        		+ "  \'order\' INTEGER NOT NULL"
		        		+ ");",
		        		"CREATE TABLE IF NOT EXISTS groups ("
		        		+ "  id INTEGER PRIMARY KEY,"
		        		+ "  name TEXT NOT NULL,"
		        		+ "  menu_id INTEGER NOT NULL"
		        		+ ");",
		        		"CREATE TABLE IF NOT EXISTS menus ("
		        		+ "  id INTEGER PRIMARY KEY,"
		        		+ "  title TEXT NOT NULL,"
		        		+ "  description TEXT NOT NULL,"
		        		+ "  thumb_img_url TEXT NOT NULL,"
		        		+ "  img_url TEXT NOT NULL"
		        		+ ");",
		        		"CREATE TABLE IF NOT EXISTS pages ("
		        		+ "  id INTEGER PRIMARY KEY,"
		        		+ "  title TEXT NOT NULL,"
		        		+ "  subtitle TEXT NOT NULL,"
		        		+ "  content text NOT NULL,"
		        		+ "  menu_id INTEGER NOT NULL"
		        		+ ");",
		        		"CREATE TABLE IF NOT EXISTS resources ("
		        		+ "  id INTEGER PRIMARY KEY,"
		        		+ "  title TEXT NOT NULL,"
		        		+ "  url TEXT NOT NULL,"
		        		+ "  description TEXT NOT NULL,"
		        		+ "  type text  NOT NULL"
		        		+ ");",
		        		"CREATE INDEX IF NOT EXISTS pages_menu_id ON pages (menu_id);",
		        		"CREATE INDEX IF NOT EXISTS groups_menu_id ON groups (menu_id);"};
		        for (String query : queries) {
		        	tx.executeSql(query, null);
				}
		    }
		    public void onTransactionFailure(SQLError error) {
		    	asyncCallback.onFailure(
		    			new DatabaseException(
		    					"SQLError: tables creation: " + error.getMessage()));
		    }

		    public void onTransactionSuccess() {
		    	final boolean isConnected = 
		    			!PhoneGapHandle.getInstance().getConnection().equals(Connection.NONE);

		    	// is the DB usable = at least partially filled ?
		    	DatabaseHandle.getInstance().readTransaction(new TransactionCallback() {
		    		public void onTransactionStart(SQLTransaction tx) {
		    			tx.executeSql("SELECT 1 FROM PAGES LIMIT 1;", null,
		    					new StatementCallback<GenericRow>() {
		    				@Override
		    				public void onSuccess(
		    						SQLTransaction transaction,
		    						SQLResultSet<GenericRow> resultSet) {
		    					if(resultSet.getRows().getLength() == 0
		    							&& !isConnected) {
		    						// The DB is empty and we have no connection to fill it.
		    						asyncCallback.onFailure(
		    								new DatabaseException(
		    										"SQLError: the tables are empty and there is" +
		    										" no internet connection to fill them"));
		    						return;
		    					}
		    					// In all other cases, fill the DB.
		    					SimpleCakeBridge.getInstance().syncDBThenCallback(asyncCallback);
		    				}

		    				@Override
		    				public boolean onFailure(
		    						SQLTransaction transaction,
		    						SQLError error) {
		    					return false;
		    				}

		    			});
		    		}
		    		public void onTransactionFailure(SQLError error) {
		    			asyncCallback.onFailure(
		    					new DatabaseException("Transaction failed: " + error.getMessage()));
		    		}
		    		public void onTransactionSuccess() {
		    			// This doesn't mean a thing as the transaction only checks if there is
		    			// something in the database
		    		}
		    	});
		    }
		});
	}
	
	public static void updateAffiliations(final JsArray<Affiliation> affiliations) {
		if (affiliations.get(0) == null)
			return;
		
		// Keep this if the SQLite implementation is updated on the browser.
//		String query = "INSERT OR REPLACE INTO \'affiliations\' VALUES ";
//		for (int i = 0; i < affiliations.length(); i++) {
//			query += "(\'" + affiliations.get(i).getId() +
//					"\',\'" + affiliations.get(i).getPageId() +
//					"\',\'" + affiliations.get(i).getGroupId() +
//					"\',\'" + affiliations.get(i).getOrder() + "\'),";
//		}
//		// remove the last ","
//		query = query.substring(0, query.length()-1) + ";";
		
		String query = "INSERT OR REPLACE INTO \'affiliations\' SELECT ";
		query += "\'" + affiliations.get(0).getId() + "\' AS \'id" +
				"\',\'" + affiliations.get(0).getPageId() + "\' AS \'page_id" +
				"\',\'" + affiliations.get(0).getGroupId() + "\' AS \'group_id" +
				"\',\'" + affiliations.get(0).getOrder() + "\' AS \'order" + "\'";
		for (int i = 1; i < affiliations.length(); i++) {
			query += " UNION SELECT \'" + affiliations.get(i).getId() +
					"\',\'" + affiliations.get(i).getPageId() +
					"\',\'" + affiliations.get(i).getGroupId() +
					"\',\'" + affiliations.get(i).getOrder() + "\'";
		}
		query += ";";
		
		execute(query);
	}
	
	public static void updateGroups(final JsArray<Group> groups) {
		if (groups.get(0) == null)
			return;
		
//		String query = "INSERT OR REPLACE INTO groups VALUES ";
//		for (int i = 0; i < groups.length(); i++) {
//			query += "(\'" + groups.get(i).getId() +
//					"\',\'" + groups.get(i).getName() +
//					"\',\'" + groups.get(i).getMenuId() + "\'),";
//		}
//		// remove the last ","
//		query = query.substring(0, query.length()-1) + ";";
		
		String query = "INSERT OR REPLACE INTO \'groups\' SELECT ";
		query += "\'" + groups.get(0).getId() + "\' AS \'id\'" +
				",\"" + groups.get(0).getName() + "\" AS \'name\'" +
				",\'" + groups.get(0).getMenuId() + "\' AS \'menu_id\'";
		for (int i = 1; i < groups.length(); i++) {
			query += "UNION SELECT \'" + groups.get(i).getId() +
					"\',\'" + groups.get(i).getName() +
					"\',\'" + groups.get(i).getMenuId() + "\'";
		}
		query += ";";
		execute(query);
	}
	
	public static void updateMenus(final JsArray<Menu> menus) {
		if (menus.get(0) == null)
			return;
		
//		String query = "INSERT OR REPLACE INTO menus VALUES ";
//		for (int i = 0; i < menus.length(); i++) {
//			query += "(\'" + menus.get(i).getId() +
//					"\',\'" + menus.get(i).getTitle() +
//					"\',\'" + menus.get(i).getDescription() +
//					"\',\'" + menus.get(i).getThumbImgURL() +
//					"\',\'" + menus.get(i).getImgURL() +"\'),";
//		}
//		// remove the last ","
//		query = query.substring(0, query.length()-1) + ";";
		
		String query = "INSERT OR REPLACE INTO \'menus\' SELECT ";
		query += "\'" + menus.get(0).getId() + "\' AS \'id\'" +
				",\"" + menus.get(0).getTitle() + "\" AS \'title\'" +
				",\"" + menus.get(0).getDescription() + "\" AS \'description\'" +
				",\"" + menus.get(0).getThumbImgURL() + "\" AS \'thumb_img_url\'" +
				",\"" + menus.get(0).getImgURL() + "\" AS \'img_url\'";
		for (int i = 1; i < menus.length(); i++) {
			query += "UNION SELECT \'" + menus.get(i).getId() +
					"\',\"" + menus.get(i).getTitle() +
					"\",\"" + menus.get(i).getDescription() +
					"\",\"" + menus.get(i).getThumbImgURL() +
					"\",\"" + menus.get(i).getImgURL() + "\"";
		}
		query += ";";
		
		execute(query);
	}
	
	public static void updatePages(final JsArray<Page> pages) {
		if (pages.get(0) == null)
			return;
		
//		String query = "INSERT OR REPLACE INTO pages VALUES ";
//		for (int i = 0; i < pages.length(); i++) {
//			query += "(\'" + pages.get(i).getId() +
//					"\',\'" + pages.get(i).getTitle() +
//					"\',\'" + pages.get(i).getSubtitle() +
//					"\',\'" + pages.get(i).getContent() +
//					"\',\'" + pages.get(i).getMenuId() + "\'),";
//		}
//		// remove the last ","
//		query = query.substring(0, query.length()-1) + ";";
		
		String query = "INSERT OR REPLACE INTO \'pages\' SELECT ";
		query += "\'" + pages.get(0).getId() + "\' AS \'id\'" +
				",\"" + pages.get(0).getTitle() + "\" AS \'title\'" +
				",\"" + pages.get(0).getSubtitle() + "\" AS \'subtitle\'" +
				",\"" + pages.get(0).getContent() + "\" AS \'content\'" +
				",\'" + pages.get(0).getMenuId() + "\' AS \'menu_id\'";
		for (int i = 1; i < pages.length(); i++) {
			query += " UNION SELECT \'" + pages.get(i).getId() +
					"\',\"" + pages.get(i).getTitle() +
					"\",\"" + pages.get(i).getSubtitle() +
					"\",\"" + pages.get(i).getContent() +
					"\",\'" + pages.get(i).getMenuId() + "\'";
		}
		query += ";";
		
		
		execute(query);
	}
	
	public static void updateResources(final JsArray<Resource> resources) {
		if (resources.get(0) == null)
			return;
		
//		String query = "INSERT OR REPLACE INTO resources VALUES ";
//		for (int i = 0; i < resources.length(); i++) {
//			query += "(\'" + resources.get(i).getId() +
//					"\',\'" + resources.get(i).getTitle() +
//					"\',\'" + resources.get(i).getURL() +
//					"\',\'" + resources.get(i).getDescription() +
//					"\',\'" + resources.get(i).getType() + "\'),";
//		}
//		
//		// remove the last ","
//		query = query.substring(0, query.length()-1) + ";";
		
		String query = "INSERT OR REPLACE INTO \'resources\' SELECT ";
		query += "\'" + resources.get(0).getId() + "\' AS \'id\'" +
				",\"" + resources.get(0).getTitle() + "\" AS \'title\'" +
				",\"" + resources.get(0).getURL() + "\" AS \'url\'" +
				",\"" + resources.get(0).getDescription() + "\" AS \'description\'" +
				",\"" + resources.get(0).getType() + "\" AS \'type\'";
		for (int i = 1; i < resources.length(); i++) {
			query += " UNION SELECT \'" + resources.get(i).getId() +
					"\',\"" + resources.get(i).getTitle() +
					"\",\"" + resources.get(i).getURL() +
					"\",\"" + resources.get(i).getDescription() +
					"\",\"" + resources.get(i).getType() + "\"";
		}
		query += ";";
		
		execute(query);
	}
	
	/**
	 * Executes the given sqlite query
	 * @param query
	 */
	private static void execute(final String query) {
		DatabaseHandle.getInstance().transaction(new TransactionCallback() {
			@Override
			public void onTransactionStart(SQLTransaction transaction) {
//				System.out.println("> " + query.replaceAll("(\\r|\\n)", ""));
				transaction.executeSql(query,
						null);
				System.out.println("DatabaseHandle: execute: ... async");
			}
			
			@Override
			public void onTransactionSuccess() {
				System.out.println("DatabaseHandle: execute: ... success");
			}
			
			@Override
			public void onTransactionFailure(SQLError error) {
				System.out.println("DatabaseHandle: execute: ... failure:" + error.getMessage());
			}
		});
	}
}
