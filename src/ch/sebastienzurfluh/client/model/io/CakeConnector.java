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

import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;

/**
 * Handles communication with CakePHP which in turn handles the ADBMS.
 * @author Sebastien Zurfluh
 *
 */
public class CakeConnector implements IOConnector {
	public CakeConnector() {
		
	}

	@Override
	public Collection<MenuData> getAllBookletMenus() {
		return null;
	}

	@Override
	public Data getBookletDataOf(int referenceId) {
		return null;
	}

	@Override
	public Data getChapterDataOf(int referenceId) {
		return null;
	}

	@Override
	public Data getPageDataOf(int referenceId) {
		return null;
	}

	@Override
	public ResourceData getRessourceDataOf(int referenceId) {
		return null;
	}

	@Override
	public Collection<MenuData> getSubMenusOfBooklet(int referenceId) {
		return null;
	}

	@Override
	public Collection<MenuData> getSubMenusOfChapter(int referenceId) {
		return null;
	}

	@Override
	public Collection<MenuData> getSubMenusOfPage(int referenceId) {
		return null;
	}

	@Override
	public Data getParentOf(DataReference childReference) {
		return null;
	}
}
