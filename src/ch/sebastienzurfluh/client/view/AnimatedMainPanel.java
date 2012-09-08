package ch.sebastienzurfluh.client.view;

import com.google.gwt.user.client.ui.Panel;

import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.patterns.Observer;
import ch.sebastienzurfluh.client.view.supportwidgets.SlidingPanel;

public class AnimatedMainPanel extends SlidingPanel implements Observer {
	private Model model;

	public AnimatedMainPanel(
			Model model,
			Panel groupPanel,
			Panel pagePanel) {
		super(model);
		
		this.model = model;
		
		addPanel(groupPanel);
		addPanel(pagePanel);
		
		model.layoutObservable.subscribeObserver(this);
	}
	
	@Override
	public void addPanel(Panel panel) {
		super.addPanel(panel);
		
		panel.setStyleName("animatedPanelView");
	};
	
	

	@Override
	public void notifyObserver(Observable source) {
		System.out.println("AnimatedMainPanel: notified: Layout Change");
		switch(model.getCurrentLayout()) {
		case PAGE:
			moveToNextPanel();
			break;
		case GROUP:
			moveToPreviousPanel();
			break;
		default:
			break;
		}
	}

}
