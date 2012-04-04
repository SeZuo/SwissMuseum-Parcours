package ch.sebastienzurfluh.client.view.TileMenu;

import java.util.LinkedList;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.Event.EventType;
import ch.sebastienzurfluh.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeEvent;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.view.TileMenu.Tile.TileMode;

import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This widget uses several {@link TileMenu}s to create a menu.
 * @author Sebastien Zurfluh
 *
 */
public class TileWidget extends VerticalPanel implements EventBusListener {
	private String stylePrimaryName = "tileWidget";
	private TileMenu bookletMenu;
	private TileMenu chapterMenu;
	private TileMenu pageMenu;
	private TileMenu resourceMenu;
	private Model model;
	
	public TileWidget(EventBus pageChangeEventBus, Model model) {
		this.model = model;
		
		setStyleName(stylePrimaryName);
		
		pageChangeEventBus.addListener(this);
		
		// Create a new TileMenu for each Data Level. Booklet, Chapter, Page, Resource.
		bookletMenu = new TileMenu("Booklets");
		add(bookletMenu);
		chapterMenu = new TileMenu("Chapters");
		add(chapterMenu);
		pageMenu = new TileMenu("Pages");
		add(pageMenu);
		resourceMenu = new TileMenu("Resources");
		add(resourceMenu);
		
		// Default settings
		chapterMenu.setVisible(false);
		pageMenu.setVisible(false);
		resourceMenu.setVisible(false);
	}

	@Override
	public EventType getEventType() {
		return EventType.PAGE_CHANGE_EVENT;
	}

	@Override
	public void notify(Event e) {
		if(e instanceof PageChangeEvent) {
			PageChangeEvent pageChangeEvent = (PageChangeEvent) e;
			
			// Change layout according to the new page type.
			switch (pageChangeEvent.getPageType()) {
			case RESSOURCE:
				resourceMenu.setVisible(true);
				resourceMenu.setMode(TileMode.ICON_ONLY);
				pageMenu.setVisible(true);
				pageMenu.setMode(TileMode.ICON_ONLY);
				chapterMenu.setVisible(true);
				chapterMenu.setMode(TileMode.ICON_ONLY);
				bookletMenu.setVisible(true);
				bookletMenu.setMode(TileMode.ICON_ONLY);
				break;
			case PAGE:
				resourceMenu.setVisible(false);
				pageMenu.setVisible(true);
				pageMenu.setMode(TileMode.ICON_ONLY);
				chapterMenu.setVisible(true);
				chapterMenu.setMode(TileMode.ICON_ONLY);
				bookletMenu.setVisible(true);
				bookletMenu.setMode(TileMode.ICON_ONLY);
				break;
			case CHAPTER:
				resourceMenu.setVisible(false);
				pageMenu.setVisible(false);
				chapterMenu.setVisible(true);
				bookletMenu.setVisible(true);
				bookletMenu.setMode(TileMode.ICON_ONLY);
				break;
			case SUPER:
				resourceMenu.setVisible(false);
				pageMenu.setVisible(false);
				chapterMenu.setVisible(false);
				bookletMenu.setVisible(true);
				bookletMenu.setMode(TileMode.DETAILED);
				break;
			case BOOKLET:
				resourceMenu.setVisible(false);
				pageMenu.setVisible(false);
				chapterMenu.setVisible(false);
				bookletMenu.setVisible(true);
				bookletMenu.setMode(TileMode.ICON_ONLY);
				break;
			default:
				break;
			}
			
			// Reload the tiles as necessary
			Data data = pageChangeEvent.getData();
			data.getParentIterator();
			switch (pageChangeEvent.getPageType()) {
			case RESSOURCE:
				loadTiles(resourceMenu, data);
				data = data.getParent();
			case PAGE:
				loadTiles(pageMenu, dataId);
				dataId = model.getParent(dataId);
			case CHAPTER:
				loadTiles(chapterMenu, dataId);
				dataId = model.getParent(dataId);
			case BOOKLET:
				loadTiles(bookletMenu, dataId);
				dataId = model.getParent(dataId);
			case SUPER:
			default:
				break;
			}
		}
	}
	
	private void loadTiles(TileMenu menu, int pageId) {
		// Delete old tiles.
		menu.clearTiles();
		
		// Add new tiles.
		LinkedList<Integer> siblingsIds = model.getSiblings(pageId);
		for (Integer id : siblingsIds) {
			Data data = model.getData(id);
			menu.addTile(data.getSquareImgURL(), data.getTitle(), data.getDescription(), data.getPriorityNumber());
		}
	}

}
