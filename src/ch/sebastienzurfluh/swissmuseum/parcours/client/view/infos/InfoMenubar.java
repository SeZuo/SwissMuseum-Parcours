package ch.sebastienzurfluh.swissmuseum.parcours.client.view.infos;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;

public class InfoMenubar extends HorizontalPanel {
	public InfoMenubar(final AnimationHelper animationHelper, final Widget goBackTo) {
		this.setStyleName("infoMenuBar");
		
		final AppInfoPanel appInfo = new AppInfoPanel(goBackTo, animationHelper);
		final InstitutInfoPanel institutInfo = 
				new InstitutInfoPanel("InstitutInfoPanel", goBackTo, animationHelper);
		
		Label benvenon = new Label("Bienvenue au Musée des Suisses dans le Monde");
		benvenon.setStyleName("infoMenuBar-title");
		this.add(benvenon);
		
		MenuBar menu = new MenuBar();
		
		menu.addItem("<img src='resources/images/generic_tiles/logo_parcours.png' class='infoMenuBar-button' />", true,
				new ScheduledCommand() {
			@Override
			public void execute() {
				animationHelper.goTo(appInfo, Animation.SLIDE_UP_REVERSE);
			}
		});
		menu.addItem("<img src='resources/images/generic_tiles/logo_penthes.png' class='infoMenuBar-button' />",
				true, new ScheduledCommand() {
			@Override
			public void execute() {
				animationHelper.goTo(institutInfo, Animation.SLIDE_UP_REVERSE);
			}
		});
		
		this.add(menu);
	}
}
