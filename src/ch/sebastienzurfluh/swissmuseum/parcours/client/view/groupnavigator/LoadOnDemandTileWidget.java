package ch.sebastienzurfluh.swissmuseum.parcours.client.view.groupnavigator;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.menuinterface.PageRequestClickHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.TileMenu;

import com.google.gwt.user.client.ui.FlowPanel;

public class LoadOnDemandTileWidget extends FlowPanel implements Observer {
	private String stylePrimaryName = "tileWidget";
	private TileMenu tileMenu;
	private Model model;
	
	
	public LoadOnDemandTileWidget(
			EventBus pageChangeEventBus,
			PageRequestClickHandler pageRequestHandler,
			Model model) {
		this.model = model;
		
		setStyleName(stylePrimaryName);
		
		tileMenu = new TileMenu("Choisissez votre parcours", pageRequestHandler);
		add(tileMenu);
		
		if(model.getAllGroupMenus() != null && !model.getAllGroupMenus().isEmpty())
			load();
		else
			postponeLoad();
	}
	
	/**
	 * Load data from current page in memory (model).
	 */
	public void load() {
		tileMenu.clearTiles();
		for (MenuData menuData : model.getAllGroupMenus()) {
			tileMenu.addTile(menuData);
		}
	}
	
	/**
	 * Postpone the load until the data is available.
	 */
	public void postponeLoad() {
		model.allGroupsMenusChangesObservable.subscribeObserver(this);
	}


	@Override
	public void notifyObserver(Observable source) {
		if(model.getAllGroupMenus() != null && !model.getAllGroupMenus().isEmpty()) {
			load();
			
			// this is the main difference with the dynamic TileWidget.
			// the page will only load once.
			model.allGroupsMenusChangesObservable.unsubscribeObserver(this);
		}
	}
}