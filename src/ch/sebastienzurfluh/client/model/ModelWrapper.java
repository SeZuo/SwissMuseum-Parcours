package ch.sebastienzurfluh.client.model;

import java.util.Collection;
import ch.sebastienzurfluh.client.model.Model.ViewMode;
import ch.sebastienzurfluh.client.model.io.IOConnector;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;
import ch.sebastienzurfluh.client.patterns.Observable;

/**
 * This class is a wrapper of {@code Model}. It does nothing else than giving a new object with
 * the same model referenced.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class ModelWrapper {
	Model model;
	IOConnector connector;
	
	/**
	 * @return the wrapped model
	 */
	public Model getModel() {
		return model;
	}
	
	public ModelWrapper(Model model) {
		this.model = model;
		this.connector = model.connector;
		
		allGroupsMenusChangesObservable = model.allGroupsMenusChangesObservable;
		
		currentGroupMenuObservable = model.currentGroupMenuObservable;
		currentPageDataObservable = model.currentPageDataObservable;
		
		previousPageMenuObservable = model.previousPageMenuObservable;
		nextPageMenuObservable = model.nextPageMenuObservable;
		
		allPagesMenusInCurrentGroupObservable =
				model.allPagesMenusInCurrentGroupObservable;
		
		allNeededResourcesObservable = model.allNeededResourcesObservable;
		
		viewModeObservable = model.viewModeObservable;
	}
	
	public Observable allGroupsMenusChangesObservable;
	
	public Observable currentGroupMenuObservable;
	public Observable currentPageDataObservable;
	
	public Observable previousPageMenuObservable;
	public Observable nextPageMenuObservable;
	
	public Observable allPagesMenusInCurrentGroupObservable;
	
	public Observable allNeededResourcesObservable;
	
	public Observable viewModeObservable;
	
	/**
	 * This function will modify the Model (synching automatically with the base)
	 * depending on the given reference.
	 * 
	 * @param currentReference is the reference of the data to load
	 */
	public void load(final DataReference currentReference) {
		model.load(currentReference);
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
		model.loadForeignPage(currentReference);
	}
	
	/**
	 * @return all the menus of groups.
	 */
	public Collection<MenuData> getAllGroupMenus() {
		return model.getAllGroupMenus();
	}
	
	/**
	 * @return the current group menu data
	 */
	public MenuData getCurrentGroupMenu() {
		return model.getCurrentGroupMenu();
	}
	
	/**
	 * @return the current page and menu
	 */
	public Data getCurrentPageData() {
		return model.getCurrentPageData();
	}
	
	/**
	 * @return the menu of the previous page.
	 */
	public MenuData getPreviousPageMenu() {
		return model.getPreviousPageMenu();
	}
	
	/**
	 * @return the menu of the next page.
	 */
	public MenuData getNextPageMenu() {
		return model.getNextPageMenu();
	}
	
	/**
	 * @return all the pages of the current group menus.
	 */
	public Collection<MenuData> getAllPageMenusInCurrentGroup() {
		return model.getAllPageMenusInCurrentGroup();
	}

	/**
	 * @return all the resources needed in the current view.
	 */
	public Collection<ResourceData> getAllNeededResources() {
		return model.getAllNeededResources();
	}
	
	/**
	 * Sets the current view mode
	 */
	public void setViewMode(ViewMode viewMode) {
		model.setViewMode(viewMode);
	}
	
	/**
	 * @return Get the current view mode.
	 */
	public ViewMode getCurrentViewMode() {
		return model.getCurrentViewMode();
	}
}
