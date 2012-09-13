package ch.sebastienzurfluh.client.view.cms.menu;

import java.util.LinkedList;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.Action;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.IntentEvent;
import ch.sebastienzurfluh.client.model.CMSModel;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.patterns.Observer;
import ch.sebastienzurfluh.client.view.menuinterface.MenuButton;
import ch.sebastienzurfluh.client.view.tilemenu.Tile;
import ch.sebastienzurfluh.client.view.tilemenu.Tile.TileMode;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TreeWidget extends VerticalPanel implements Observer {
	protected VerticalPanel entries;
	protected DataType type;
	protected CMSModel model;
	protected ClickHandler pageRequestHandler;
	
	
	private final static Tile NO_TILE = new Tile(MenuData.NONE);
	/**
	 * Points to the currently focused tile.
	 */
	private Tile focusedTile = NO_TILE;
	
	public TreeWidget(
			final DataType type,
			final EventBus eventBus,
			CMSModel model) {
		this.type = type;
		this.model = model;
		
		pageRequestHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(
						new IntentEvent(
								((MenuButton) event.getSource()).getReference(),
								Action.MODIFY));
			}
		};
		
		setStyleName("cms-TreeWidget");
		
		Label titleLabel = new Label(type.toString());
		titleLabel.setStyleName("cms-tileMenu-title");
		add(titleLabel);
		
		entries = new VerticalPanel();
		add(entries);
		
		Tile newTile = new Tile(
				new MenuData(
						DataReference.NONE,
						0,
						"Ajouter.",
						"(Mode édition uniquement)",
						"resources/images/generic_tiles/plus.gif",
						""));
		newTile.setMode(TileMode.DETAILED);
		newTile.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new IntentEvent(new DataReference(type,0), Action.CREATE));
			}
		});
		add(newTile);
		
	}
	
	/**
	 * Store the entries for future retrieval
	 */
	LinkedList<Tile> entryStore = new LinkedList<Tile>();
	
	/**
	 * Add an entry to the tree.
	 * @param tile to add
	 */
	protected void addEntry(Tile tile) {
		entryStore.add(tile);
		
		entries.add(tile);
	}

	@Override
	public void notifyObserver(Observable source) {
	}
	
	/**
	 * Updates the currently focused tile with data from the model.
	 * 
	 * @param focusReference the reference to focus to.
	 */
	protected void updateFocusedTile(DataReference focusReference) { 
			if(focusReference == null) {
				focusedTile.setMenuFocus(false);
				focusedTile = NO_TILE;
				return;
			}
			for(Tile tile : entryStore) {
				if (tile.getReference().equals(focusReference)) {
					focusedTile.setMenuFocus(false);
					tile.setMenuFocus(true);
					focusedTile = tile;
					break;
				}
			}
	}
	
	/**
	 * Create a new tile. It will automatically be added to the current widget.
	 * @param menuData
	 */
	protected void createNewTile(MenuData menuData) {
		Tile tile = new Tile(menuData);
		tile.addClickHandler(pageRequestHandler);
		tile.setMode(TileMode.DETAILED);
		
		
		addEntry(tile);
	}
}
