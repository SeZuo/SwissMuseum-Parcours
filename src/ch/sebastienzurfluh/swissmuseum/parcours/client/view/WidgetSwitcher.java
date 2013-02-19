/*
 * Copyright 2012-2013 Sebastien Zurfluh
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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.Data;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator.BookletNavigator;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.groupnavigator.GroupNavigator;

/**
 * This object watches the changes in the model to adapt the current screen disposition to the data.
 * 
 * @author Sebastien Zurfluh
 */
public class WidgetSwitcher implements Observer {
	private Model model;
	
	private GroupNavigator groupNavigator;
	private BookletNavigator bookletNavigator;
	private AnimationHelper animationHelper;


	public WidgetSwitcher(
			Model model,
			GroupNavigator groupNavigator,
			BookletNavigator bookletNavigator,
			AnimationHelper animationHelper) {
		
		this.model = model;
		this.groupNavigator = groupNavigator;
		this.bookletNavigator = bookletNavigator;
		this.animationHelper = animationHelper;
		
		model.currentPageDataObservable.subscribeObserver(this);
	}
	
	private DataType currentView = DataType.NONE;
	@Override
	public void notifyObserver(Observable source) {
		System.out.println("AnimatedMainPanel: notified of page change");
		if (model.getCurrentPageData().equals(Data.NONE)) {
			System.out.println("The new page is not null.");
			// There is no page, we want the group menu.
			if (model.getCurrentPageData().getReference().getType().equals(currentView))
				return;
			currentView = model.getCurrentPageData().getReference().getType();

			animationHelper.goTo(groupNavigator, Animation.SLIDE_REVERSE);
		} else {
			System.out.println("We will proceed with page view.");
			// A page exists! We want the page view.
			if (model.getCurrentPageData().getReference().getType().equals(currentView))
				return;
			currentView = model.getCurrentPageData().getReference().getType();
			
			animationHelper.goTo(bookletNavigator, Animation.SLIDE);
		}
	}

}