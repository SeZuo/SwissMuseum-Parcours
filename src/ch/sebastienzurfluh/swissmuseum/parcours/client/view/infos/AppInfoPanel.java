/* Copyright 2012-2013 Sebastien Zurfluh
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

package ch.sebastienzurfluh.swissmuseum.parcours.client.view.infos;

import ch.sebastienzurfluh.swissmuseum.core.client.view.FooterWidget;

import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

public class AppInfoPanel extends InfoPanel {

	public AppInfoPanel(String title, Widget returnToIt,
			AnimationHelper animationHelper) {
		super(title, returnToIt, animationHelper);
		
		setContent(new FooterWidget());
	}

	public AppInfoPanel(Widget goBackTo, AnimationHelper animationHelper) {
		this("A propos de ce programme", goBackTo, animationHelper);
	}
}