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
import ch.sebastienzurfluh.client.control.eventbus.events.ResourceType;

/**
 * This is an immutable piece of data. It contains all the needed data to display A PAGE.
 *
 * @author Sebastien Zurfluh
 *
 */
public class ResourceData {
	private DataReference reference;
	private String title, details, url;
	private ResourceType resourceType;
	
	/**
	 * @param reference of the resource.
	 * @param resourceType
	 * @param title
	 * @param details
	 * @param url
	 */
	public ResourceData(
			DataReference reference,
			ResourceType resourceType,
			String title,
			String details,
			String url) {
		setReference(reference);
		setResourceType(resourceType);
		setTitle(title);
		setDetails(details);
		setURL(url);
	}
	
	// ************************ GETTERS AND SETTERS ***************************** //

	public DataReference getReference() {
		return reference;
	}

	private void setReference(DataReference reference) {
		this.reference = reference;
	}

	private void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	public ResourceType getResourceType() {
		return resourceType;
	}
	
	public DataType getDataType() {
		return DataType.RESOURCE;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	private void setDetails(String details) {
		this.details = details;
	}

	public String getURL() {
		return url;
	}

	private void setURL(String url) {
		this.url = url;
	}
}
