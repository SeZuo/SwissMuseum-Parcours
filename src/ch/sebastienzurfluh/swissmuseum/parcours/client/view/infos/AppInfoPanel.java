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