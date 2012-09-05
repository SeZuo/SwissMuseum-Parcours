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
import java.util.Iterator;
import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.io.IOConnector;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;
import ch.sebastienzurfluh.client.patterns.Observable;


/**
 * Facade for the model package.
 * 
 * The model is observable but you may also observe a single object.
 * 
 * Model data gives priority to the locally stored data in order to
 * minimise the use of a remote ADBMS. This ADBMS is fetched through
 * IOConnector.
 *
 * @author Sebastien Zurfluh
 */
public class Model extends Observable {
	IOConnector connector;
	
	public Model(IOConnector connector) {
		this.connector = connector;
	}
	
	// What the view needs.
	
	/**
	 * We need this information application wise. If for performance reasons we
	 * judge that it should be discarded, don't forget to check the references.
	 */
	private Collection<MenuData> allGroupMenus;
	public Observable allGroupsMenusChangesObservable = new Observable();
	
	private MenuData currentGroupMenu = MenuData.NONE;
	public Observable currentGroupMenuObservable = new Observable();
	private Data currentData = Data.NONE;
	public Observable currentPageDataObservable = new Observable();
	
	private MenuData previousPageMenu = MenuData.NONE;
	public Observable previousPageMenuObservable = new Observable();
	private MenuData nextPageMenu = MenuData.NONE;
	public Observable nextPageMenuObservable = new Observable();
	
	private Collection<MenuData> allPagesMenusInCurrentGroup;
	public Observable allPagesMenusInCurrentGroupObservable = new Observable();
	
	private Collection<ResourceData> allNeededResources = new LinkedList<ResourceData>();
	public Observable allNeededResourcesObservable = new Observable();
	
	private Layout layout = Layout.GROUP;
	public Observable layoutObservable = new Observable();
	
	/**
	 * Notify all observers of the model and of the selected observable.
	 * @param observable
	 */
	private void notifyAllObservers(Observable observable) {
		observable.notifyObservers();
		this.notifyObservers();
	}
	
	/**
	 * This function will modify the Model (synching automatically with the base)
	 * depending on the given reference.
	 * 
	 * @param currentReference is the reference of the data to load
	 */
	public void load(final DataReference currentReference) {
		switch(currentReference.getType()) {
		case SUPER:
			// group view: only the tile menu will be visible
			if (allGroupMenus == null) {
				connector.asyncRequestAllGroupMenus(new AsyncCallback<Collection<MenuData>>() {
					@Override
					public void onSuccess(Collection<MenuData> allGroupMenus) {
						Model.this.setAllGroupMenus(allGroupMenus);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Model:load Cannot get data from the connector");
						Model.this.load(currentReference);
					};
				});
			}
			setCurrentGroupMenu(MenuData.NONE);
			setCurrentPageData(Data.NONE);
			setPreviousPageMenu(MenuData.NONE);
			setNextPageMenu(MenuData.NONE);
			setAllPageMenusInCurrentGroup(new LinkedList<MenuData>());
			// resources == null
			break;
		case GROUP:
			// page view: the navigation and the page content will be visible
			setCurrentGroupMenu(currentReference);
			setAllPageMenusInCurrentGroup(currentReference);
			
			// a group has been opened we need to load the first page
			// NO break;
		case PAGE:
			setCurrentPageData(currentReference);
			setPreviousPageMenu(currentReference);
			setNextPageMenu(currentReference);
			break;
		case RESOURCE:
			addResource(currentReference);
		default:
			break;
		}
	}
	

	/**
	 * This function will modify the Model (synching automatically with the base)
	 * depending on the given reference.
	 * 
	 * This will load the new page instead of the current one, and set the next and previous
	 * pages to the last visible one.
	 * This way, the user will go back to the previous page by going next or previous.
	 * Note we are not changing group this way.
	 * 
	 * @param currentReference is the reference of the data to load
	 */
	public void loadForeignPage(DataReference currentReference) {
		setPreviousPageMenu(currentData.getMenu());
		setNextPageMenu(currentData.getMenu());
		setCurrentPageData(currentReference);
	}
	
	
	/**
	 * @param allGroupsMenus
	 */
	private void setAllGroupMenus(Collection<MenuData> allGroupMenus) {
		this.allGroupMenus = allGroupMenus;
		notifyAllObservers(allGroupsMenusChangesObservable);
	}
	
	/**
	 * @param currentGroupMenu the current group menu data
	 */
	private void setCurrentGroupMenu(MenuData currentGroupMenu) {
		this.currentGroupMenu = currentGroupMenu;
		notifyAllObservers(currentGroupMenuObservable);
	}
	
	/**
	 * Loads the data from the connector.
	 * @param currentGroupReference
	 */
	private void setCurrentGroupMenu(DataReference currentGroupReference) {
		// we already have all the groups loaded. no need to check the ADBMS
		for (MenuData group : allGroupMenus) {
			if (group.getReference().equals(currentGroupReference)) {
				setCurrentGroupMenu(group);
				return;
			}
		}
	}
	
	/**
	 * @param currentData the current page and menu
	 */
	private void setCurrentPageData(Data currentData) {
		this.currentData = currentData;
		notifyAllObservers(currentPageDataObservable);
	}

	/**
	 * Loads the data from the connector.
	 * @param currentReference
	 */
	private void setCurrentPageData(final DataReference currentReference) {
		if (currentReference.getType().equals(DataType.GROUP)) {
			// if a group is referenced, use the first page of the group
			connector.asyncRequestGetFirstDataOfGroup(
					currentReference.getReferenceId(),
					new AsyncCallback<Data>() {
						@Override
						public void onSuccess(Data data) {
							Model.this.setCurrentPageData(data);
						};

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Model:load Cannot get data from the connector");
						};
					});
		} else {
			// else use the given page reference
			connector.asyncRequestGetData(
					currentReference.getReferenceId(),
					new AsyncCallback<Data>() {
						@Override
						public void onSuccess(Data data) {
							Model.this.setCurrentPageData(data);
						}

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Model:load Cannot get data from the connector");
						};
					});
		}
	}
	
	/**
	 * @param previousPageMenu the menu of the previous page.
	 */
	private void setPreviousPageMenu(MenuData previousPageMenu) {
		this.previousPageMenu = previousPageMenu;
		notifyAllObservers(previousPageMenuObservable);
	}
	
	/**
	 * Loads the data from the connector.
	 * @param currentPageReference of the visible page and not the reference of the next page
	 */
	private void setPreviousPageMenu(DataReference currentPageReference) {
		// that menu has good chance to be in the list
		if(currentPageReference.getType().equals(DataType.GROUP) ||
				currentPageReference.equals(
						allPagesMenusInCurrentGroup.iterator().next().getReference())) {
			// the previous page was the super menu
			setPreviousPageMenu(MenuData.SUPER);
		} else {
			MenuData previous = MenuData.NONE;
			int count = 0;
			for (MenuData menuData : allPagesMenusInCurrentGroup) {
				if(menuData.getReference().equals(currentPageReference))
					break;
				previous = menuData;
				count++;
			}
			// if the item is not in the list, the last menu is selected
			// this should not happen.
			if(count == allPagesMenusInCurrentGroup.size())
				return;
			
			setPreviousPageMenu(previous);
		}
	}
	
	/**
	 * @param nextPageMenu the menu of the next page.
	 */
	private void setNextPageMenu(MenuData nextPageMenu) {
		this.nextPageMenu = nextPageMenu;
		notifyAllObservers(nextPageMenuObservable);
	}
	
	/**
	 * Find the next page data from the current group's MenuData list.
	 * @param currentPageReference of the visible page and not the reference of the next page
	 */
	private void setNextPageMenu(DataReference currentPageReference) {
		for (Iterator<MenuData> iterator = allPagesMenusInCurrentGroup.iterator();
				iterator.hasNext();) {
			MenuData menuData = iterator.next();
			if(menuData.getReference().equals(currentPageReference))
				if(iterator.hasNext())
					setNextPageMenu(iterator.next());
				else
					setNextPageMenu(MenuData.SUPER);
		}
	}

	
	
	/**
	 * @param allPagesMenusInCurrentGroup all the pages of the current group menus.
	 */
	public void setAllPageMenusInCurrentGroup(Collection<MenuData> allPagesMenusInCurrentGroup) {
		this.allPagesMenusInCurrentGroup = allPagesMenusInCurrentGroup;
		notifyAllObservers(allPagesMenusInCurrentGroupObservable);
	}
	

	/**
	 * Loads the data from the connector.
	 * @param currentGroupReference of the visible page and not the reference of the next page
	 */
	private void setAllPageMenusInCurrentGroup(final DataReference currentGroupReference) {
		connector.asyncRequestGetAllPageMenusFromGroup(
				currentGroupReference.getReferenceId(),
				new AsyncCallback<Collection<MenuData>>() {
					@Override
					public void onSuccess(Collection<MenuData> menuDataList) {
						Model.this.setAllPageMenusInCurrentGroup(menuDataList);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Model:setAllPageMenusInCurrentGroup" +
								" Cannot get data from the connector");
					};
				});
	}
	
	/**
	 * Adds the given resource from the connector.
	 * @param resource data of the resource to load.
	 */
	private void addResource(ResourceData resource) {
		this.allNeededResources.add(resource);
		
		allNeededResourcesObservable.notifyObservers();
	}
	
	/**
	 * Loads the given resource from the connector.
	 * @param resourceReference of the resource to load.
	 */
	private void addResource(DataReference resourceReference) {
		connector.asyncRequestResourceData(
				resourceReference.getReferenceId(),
				new AsyncCallback<ResourceData>() {
					@Override
					public void onSuccess(ResourceData resourceData) {
						Model.this.addResource(resourceData);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Model:asyncRequestResourceData" +
								" Cannot get data from the connector");
					};
				});
	}
	
	/**
	 * @return all the menus of groups.
	 */
	public Collection<MenuData> getAllGroupMenus() {
		return allGroupMenus;
	}
	
	/**
	 * @return the current group menu data
	 */
	public MenuData getCurrentGroupMenu() {
		return currentGroupMenu;
	}
	
	/**
	 * @return the current page and menu
	 */
	public Data getCurrentPageData() {
		return currentData;
	}
	
	/**
	 * @return the menu of the previous page.
	 */
	public MenuData getPreviousPageMenu() {
		return previousPageMenu;
	}
	
	/**
	 * @return the menu of the next page.
	 */
	public MenuData getNextPageMenu() {
		return nextPageMenu;
	}
	
	/**
	 * @return all the pages of the current group menus.
	 */
	public Collection<MenuData> getAllPageMenusInCurrentGroup() {
		return allPagesMenusInCurrentGroup;
	}

	/**
	 * @return all the resources needed in the current view.
	 */
	public Collection<ResourceData> getAllNeededResources() {
		return allNeededResources;
	}
	
	/**
	 * Layout indicates the view how to display it's content
	 */
	public enum Layout {
		PAGE, GROUP;
	}
	
	/**
	 * Sets the current layout
	 */
	public void setLayout(Layout layout) {
		this.layout = layout;
		
		notifyAllObservers(layoutObservable);
	}
	
	/**
	 * Get the current layout
	 */
	public Layout getLayout() {
		return layout;
	}
}
