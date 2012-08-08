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
import java.util.HashMap;
import java.util.LinkedList;

import ch.sebastienzurfluh.client.control.ModelAsyncPlug;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.ResourceType;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;

public class TestConnector implements IOConnector {
	private String testSquareURLBooklet = "resources/images/pix_light_yellow.gif";
	private String testSquareURLChapter = "resources/images/pix_light_blue.gif";
	private String testSquareURLPage = "resources/images/pix_light_green.gif";
	private String testRectURLBooklet = "resources/images/pix_light_yellow.gif";
	private String testRectURLChapter = "resources/images/pix_light_blue.gif";
	private String testRectURLPage = "resources/images/pix_light_green.gif";
	private String testInlineImageURL = "resources/images/pix_light_violet.gif";
	
	
	HashMap<DataReference, Data> dataMap = new HashMap<DataReference, Data>();
	HashMap<DataReference, ResourceData> resourceDataMap = new HashMap<DataReference, ResourceData>();
	
	public TestConnector() {

		// Create the tutorial booklet
		DataReference reference = new DataReference(DataType.BOOKLET, 1);
		dataMap.put(reference, new Data(
			reference,
			1,
			"Tutorial",
			"This booklet explains how to use the application.",
			"To coninue to the next step of this tutorial, choose the first image in the " +
					"\"Chapter\" menu below. [img]1[/img] I'm putting some more text to see if this works.",
			"Tutorial",
			"This booklet explains how to use the application.",
			testSquareURLBooklet,
			testRectURLBooklet));
		
		
		// Create the first chapter
		DataReference reference2 = new DataReference(DataType.CHAPTER, 1);
		dataMap.put(reference2, new Data(
				reference2,
				1,
				"Step 1",
				"You've just used the \"tile menu\". Use this menu to choose which object you want to see.",
				"Now, take a look at the beginning of the page. You can see another line appeared. This is"
						+ " the navigation menu. You can slide with you're finger along any of the lines to go to"
						+ " the next or previous item on the list. <br>"
						+ "Give it a go and slide your finger from right to left on the last line of the navigation"
						+ "menu.",
				"Step 1",
				"Learn how to use the navigation bars.",
				testSquareURLChapter,
				testRectURLChapter));
		
		// Create the second chapter
		DataReference reference3 = new DataReference(DataType.CHAPTER, 2);
		dataMap.put(reference3, new Data(
				reference3,
				2,
				"Step 2",
				"You've just changed chapter!",
				"You can use this navigation menu like you would turn a page of a book, except that depending on the"
						+ " line you choose to slide, you'll flip to the next booklet, the next chapter, or the next"
						+ " page!<br>"
						+ "You can see in the text bar between this text and the navigation menu, that there is a "
						+ "reminder of your"
						+ "position in the booklet. To continue, click this <link>link</link>",
				"Step 2",
				"Learn how to follow text links.",
				testSquareURLChapter,
				testRectURLChapter));
		
		// Create the third chapter
		DataReference reference4 = new DataReference(DataType.PAGE, 1);
		dataMap.put(reference4, new Data(
				reference4,
				1,
				"Step 3",
				"This is a page.",
				"The link you've just clicked sent you here. This page belongs to the same chapter you were in. <br>" +
						"Sometimes, this is not the case and a link in the page will send you to another chapter or" +
						" another booklet. Don't worry you can always go back, using the back button on your device " +
						"[img]1[/img] Go to the next page by either using the navigation menu (above) or the tile menu (below).",
				"Step 3",
				"Try it by yourself.",
				testSquareURLPage,
				testRectURLPage));
		
		// Create some resources
		DataReference reference5 = new DataReference(DataType.RESOURCE, 1);
		resourceDataMap.put(reference5, new ResourceData(
				reference5,
				ResourceType.IMAGE,
				"Resource One",
				"This is a page. Images tend to have a lot of stupid details.",
				testInlineImageURL));
	}

	@Override
	public void getBookletDataOf(ModelAsyncPlug asyncPlug, int referenceId) {
		asyncPlug.update(getDataOf(DataType.BOOKLET, referenceId));
	}
	
	@Override
	public void getChapterDataOf(ModelAsyncPlug asyncPlug, int referenceId) {
		asyncPlug.update(getDataOf(DataType.CHAPTER, referenceId));
	}

	@Override
	public void getPageDataOf(ModelAsyncPlug asyncPlug, int referenceId) {
		asyncPlug.update(getDataOf(DataType.PAGE, referenceId));
	}

	@Override
	public void getRessourceDataOf(ModelAsyncPlug asyncPlug, int referenceId) {		
		asyncPlug.update(resourceDataMap.get(new DataReference(DataType.RESOURCE, referenceId)));
	}
	
	private Data getDataOf(DataType type, int referenceId) {
		return dataMap.get(new DataReference(type, referenceId));
	}

	@Override
	public void getSubMenusOfBooklet(ModelAsyncPlug asyncPlug, int referenceId) {
		LinkedList<MenuData> menus = new LinkedList<MenuData>();
		if (referenceId == 1) {
			menus.add(dataMap.get(new DataReference(DataType.CHAPTER, 1)).getMenu());
			menus.add(dataMap.get(new DataReference(DataType.CHAPTER, 2)).getMenu());
		}
		
		asyncPlug.update(menus);
	}

	@Override
	public void getSubMenusOfChapter(ModelAsyncPlug asyncPlug, int referenceId) {
		LinkedList<MenuData> menus = new LinkedList<MenuData>();
		if (referenceId == 2) {
			menus.add(dataMap.get(new DataReference(DataType.PAGE, 1)).getMenu());
		}
		
		asyncPlug.update(menus);
	}

	@Override
	public void getSubMenusOfPage(ModelAsyncPlug asyncPlug, int referenceId) {
		asyncPlug.update(new LinkedList<MenuData>());
	}

	@Override
	public void getAllBookletMenus(ModelAsyncPlug asyncPlug) {
		LinkedList<MenuData> booklets = new LinkedList<MenuData>();
		booklets.add(dataMap.get(new DataReference(DataType.BOOKLET, 1)).getMenu());
		asyncPlug.update(booklets);
	}

	@Override
	public void getParentOf(ModelAsyncPlug asyncPlug, DataReference childReference) {
		switch(childReference.getType()) {
		case RESOURCE:
		case PAGE:
			switch(childReference.getReferenceId()) {
			case 1:
				asyncPlug.update(getDataOf(DataType.CHAPTER, 2));
				return;
			}
			break;
		case CHAPTER:
			switch(childReference.getReferenceId()) {
			case 1:
			case 2:
				asyncPlug.update(getDataOf(DataType.BOOKLET, 1));
				return;
			}
			break;
		case BOOKLET:
			break;
		case SUPER:
			break;
		default:
			break;
		}
	}

}
