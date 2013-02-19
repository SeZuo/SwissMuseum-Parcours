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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

public class InstitutInfoPanel extends InfoPanel {
	public InstitutInfoPanel(String title, Widget returnToIt,
			AnimationHelper animationHelper) {
		super(title, returnToIt, animationHelper);
		
		setContent(new HTML(
				"<div style='align:center;width:100%;'><img src='resources/images/generic_tiles/logo_penthes.png'" +
				" width='250px' height='250px' style='margin: 0 1em 0 0;'></div>" +
				"<br><br>L’<b>Institut des Suisses dans le Monde</b> est le nom donné au pôle de" +
				" recherche de la <b>Fondation pour l’Histoire des Suisses dans le Monde</b>.  Il" +
				" entend conduire une politique scientifique qui repose essentiellement sur" +
				" trois axes : la recherche sur les archives conservées à Penthes," +
				" l’intensification des échanges avec des universités et des instituts suisses" +
				" et étrangers et la publication de livres grand public et spécialisés."));
	}

	public InstitutInfoPanel(Widget goBackTo, AnimationHelper animationHelper) {
		this("L'Institut des Suisses dans le Monde", goBackTo, animationHelper);
	}
}