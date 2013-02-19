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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

public class InfoPanel extends FlowPanel {
	private SimplePanel contentPanel = new SimplePanel();
	
	public InfoPanel(String title,
			final Widget returnToIt,
			final AnimationHelper animationHelper) {
		
		HorizontalPanel titleLine = new HorizontalPanel();
		
		Label returnLabel = new Label("Retourner");
		returnLabel.setStyleName("infoPanel-returnButton");
		
		returnLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				animationHelper.goTo(returnToIt, Animation.SLIDE_UP);
			}
		});
		
		titleLine.add(returnLabel);
		
		Label titleLabel = new Label(title);
		titleLabel.setStyleName("infoPanel-title");
		titleLine.add(titleLabel);
		
		
		this.add(titleLine);
		
		setContent(new Label("loading..."));
		this.add(contentPanel);
	}
	
	public void setContent(Widget content) {
		content.setStyleName("infoPanel-content");
		contentPanel.setWidget(content);
	}
}