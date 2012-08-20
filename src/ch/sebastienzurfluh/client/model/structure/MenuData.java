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
 * This is an immutable piece of data. It contains all the needed data to display A MENU.
 *
 * @author Sebastien Zurfluh
 *
 */
public class MenuData {
	private DataReference reference;
	private int priorityNumber;
	private String title, description;
	private String squareImgURL, rectangleImgURL;
	
	public final static MenuData SUPER = new MenuData(
			new DataReference(DataType.SUPER, 0),
			Integer.MAX_VALUE,
			"Menu principal",
			"Retournez au menu principal",
			"resources/images/generic_tiles/super.jpg",
			"resources/images/generic_tiles/super_square.jpg"); 
	
	/**
	 * @param linkReference reference of the page the menu is linking to
	 * @param priorityNumber
	 * @param title
	 * @param description
	 * @param squareImgURL
	 * @param rectangleImgURL
	 */
	public MenuData(DataReference linkReference,
			int priorityNumber,
			String title,
			String description,
			String squareImgURL,
			String rectangleImgURL) {
		setReference(linkReference);
		setPriorityNumber(priorityNumber);
		setTitle(title);
		setDescription(description);
		setSquareImgURL(squareImgURL);
		setRectangleImgURL(rectangleImgURL);
	}
	
	// ************************ GETTERS AND SETTERS ***************************** //

	public DataReference getReference() {
		return reference;
	}

	private void setReference(DataReference reference) {
		this.reference = reference;
	}
	
	public DataType getPageType() {
		return reference.getType();
	}

	public int getPriorityNumber() {
		return priorityNumber;
	}

	private void setPriorityNumber(int priorityNumber) {
		this.priorityNumber = priorityNumber;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	public String getSquareImgURL() {
		return squareImgURL;
	}

	private void setSquareImgURL(String squareImgURL) {
		this.squareImgURL = squareImgURL;
	}

	public String getRectangleImgURL() {
		return rectangleImgURL;
	}

	private void setRectangleImgURL(String rectangleImgURL) {
		this.rectangleImgURL = rectangleImgURL;
	}
}
