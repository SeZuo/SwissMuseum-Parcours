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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view.navigation.animation;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.parcours.client.view.navigation.NavigationSlider;

/**
 * Create test data with this factory.
 * @author Sebastien Zurfluh
 *
 */
public class AnimatorFactory {
	public enum AnimatorType {
		CONTINUOUS, SWIPE, STATIC;
	}
	
	public static NavigationAnimator createAnimator(AnimatorType type,
			AbsolutePanel animatedPanel, Widget movingWidget, NavigationSlider parentSlider,
			EventBus pageRequestBus) {
		switch (type) {
			case CONTINUOUS:
				return new ContinuousScroller(animatedPanel, movingWidget, parentSlider);
			case SWIPE:
				return new SwipeScroller(animatedPanel, movingWidget, parentSlider, pageRequestBus);
			case STATIC:
				return new StaticFlipper(animatedPanel, movingWidget, parentSlider);
			default:
				throw(new Error("AnimatorFactory: animation wrong name"));
		}
	}
}