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

package ch.sebastienzurfluh.swissmuseum.core.client.view.animations;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Window;

/**
 * This singleton scrolls the page to the top when ordered to.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class ScrollToTheTop extends Animation {
	private int destination = 0;
	private int origin = 0;
	
	private static ScrollToTheTop instance;
	
	private ScrollToTheTop() {
		origin = Window.getScrollTop();
	}
	
	/**
	 * @return the unique instance of this animation
	 */
	public static ScrollToTheTop getInstance() {
		if (instance == null) {
			instance = new ScrollToTheTop();
		}
		return instance;
	}
	
	/**
	 * Starts the animation.
	 */
	public void start() {
		run(400);
	}
	
	@Override
	protected void onUpdate(double progress) {
		Window.scrollTo(0 , (int) (origin + (destination - origin) * progress));
	}

}