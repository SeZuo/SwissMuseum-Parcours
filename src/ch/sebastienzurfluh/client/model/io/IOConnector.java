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

import ch.sebastienzurfluh.client.control.ModelAsyncPlug;
import ch.sebastienzurfluh.client.model.structure.DataReference;

/**
 * Handles communication with the ADBMS.
 * @author Sebastien Zurfluh
 *
 */
public interface IOConnector {
	void getAllBookletMenus(ModelAsyncPlug asyncPlug);

	void getBookletDataOf(ModelAsyncPlug asyncPlug, int referenceId);

	void getChapterDataOf(ModelAsyncPlug asyncPlug, int referenceId);

	void getPageDataOf(ModelAsyncPlug asyncPlug, int referenceId);

	void getRessourceDataOf(ModelAsyncPlug asyncPlug, int referenceId);

	/**
	 * @param referenceId of the booklet.
	 * @return menus of the booklet's chapters, an empty one if there were none.
	 */
	void getSubMenusOfBooklet(ModelAsyncPlug asyncPlug, int referenceId);

	/**
	 * @param referenceId of the chapter.
	 * @return menus of the chapter's pages, an empty one if there were none.
	 */
	void getSubMenusOfChapter(ModelAsyncPlug asyncPlug, int referenceId);

	/**
	 * @param referenceId of the page.
	 * @return menus of the page's resources, an empty one if there were none.
	 */
	void getSubMenusOfPage(ModelAsyncPlug asyncPlug, int referenceId);

	/**
	 * @param childReference of the parent
	 * @return the parent, null if there were none.
	 */
	void getParentOf(ModelAsyncPlug asyncPlug, DataReference childReference);
	
}
