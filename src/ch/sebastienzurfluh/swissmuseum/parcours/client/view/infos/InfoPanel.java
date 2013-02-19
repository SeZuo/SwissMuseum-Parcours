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
		contentPanel.setWidget(content);
	}
}