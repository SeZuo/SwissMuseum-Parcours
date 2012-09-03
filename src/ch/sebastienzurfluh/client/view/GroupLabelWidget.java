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

package ch.sebastienzurfluh.client.view;

import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.patterns.Observer;

import com.google.gwt.user.client.ui.Label;

/**
 * This widget shows the current group label.
 * @author Sebastien Zurfluh
 *
 */
public class GroupLabelWidget extends Label implements Observer {
	Model model;
	
	public GroupLabelWidget(Model model) {
		this.model = model;
		
		setVisible(false);
		setStyleName("navigationWidget-bookletLink");
		
		model.currentGroupMenuObservable.subscribeObserver(this);
	}

	@Override
	public void notifyObserver() {
		if(model.getCurrentGroupMenu().equals(MenuData.NONE)) {
			setVisible(false);
		} else {
			setVisible(true);
			setText(model.getCurrentGroupMenu().getTitle());
		}
		
	}
}
