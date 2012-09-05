package ch.sebastienzurfluh.client.view;

import com.google.gwt.user.client.ui.Panel;

import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.Model.Layout;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.patterns.Observer;
import ch.sebastienzurfluh.client.view.supportwidgets.SlidingPanel;

public class AnimatedMainPanel extends SlidingPanel implements Observer {
	private Layout currentLayout;
	private Model model;

	public AnimatedMainPanel(
			Model model,
			Panel groupPanel,
			Panel pagePanel) {
		super(model);
		
		this.currentLayout = model.getLayout();
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
		if(!currentLayout.equals(model.getLayout())) {
			switch(model.getLayout()) {
			case PAGE:
				moveToNextPanel();
				break;
			case GROUP:
				moveToPreviousPanel();
				break;
			}
		}
	}

}
