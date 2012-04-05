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
import ch.sebastienzurfluh.client.model.structure.MenuData;

/**
 * Handles communication with the ADBMS.
 * @author Sebastien Zurfluh
 *
 */
public interface IOConnector {
	Collection<MenuData> getAllBookletMenus();

	Data getBookletDataOf(int referenceId);

	Data getChapterDataOf(int referenceId);

	Data getPageDataOf(int referenceId);

	Data getRessourceDataOf(int referenceId);

	Collection<MenuData> getSubMenusOfBooklet(int referenceId);

	Collection<MenuData> getSubMenusOfChapter(int referenceId);

	Collection<MenuData> getSubMenusOfPage(int referenceId);
}
