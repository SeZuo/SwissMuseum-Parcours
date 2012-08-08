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

package ch.sebastienzurfluh.client.model.structure;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;

/**
 * This is an immutable piece of data. It contains all the needed data to display a page and a menu.
 *
 * @author Sebastien Zurfluh
 *
 */
public class Data {
	private MenuData menuData;
	private PageData pageData;
	
	public Data(DataReference reference,
			int priorityNumber,
			String pageTitle,
			String pageContentHeader,
			String pageContentBody,
			String menuTitle,
			String menuDescription,
			String squareImgURL,
			String rectangleImgURL) {
		menuData = new MenuData(reference, priorityNumber, menuTitle, menuDescription, squareImgURL, rectangleImgURL);
		pageData = new PageData(reference, pageTitle, pageContentHeader, pageContentBody);
	}
	
	public MenuData getMenu() {
		return menuData;
	}
	
	public PageData getPage() {
		return pageData;
	}
	
	public DataReference getReference() {
		return menuData.getReference();
	}
	
	public DataType getPageType() {
		return menuData.getPageType();
	}

	public int getPriorityNumber() {
		return menuData.getPriorityNumber();
	}

	public String getMenuTitle() {
		return menuData.getTitle();
	}
	
	public String getMenuDescription() {
		return menuData.getDescription();
	}
	
	public String getPageTitle() {
		return pageData.getTitle();
	}

	public String getPageContentHeader() {
		return pageData.getContentHeader();
	}
	
	public String getPageContentBody() {
		return pageData.getContentBody();
	}
	
	public String getSquareRectangleImgURL() {
		return menuData.getSquareImgURL();
	}

	public String getRectangleImgURL() {
		return menuData.getRectangleImgURL();
	}
}
