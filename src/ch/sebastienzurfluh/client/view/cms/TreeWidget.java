package ch.sebastienzurfluh.client.view.cms;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.NewElementEvent;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.patterns.Observer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TreeWidget extends VerticalPanel implements Observer {
	private VerticalPanel entries;
	protected DataType type;
	protected Model model;
	
	public TreeWidget(final DataType type, final EventBus eventBus, Model model) {
		this.type = type;
		this.model = model;
		
		Label titleLabel = new Label(type.toString());
		titleLabel.setStyleName("tileMenu-title");
		add(titleLabel);
		
		entries = new VerticalPanel();
		add(entries);
		
		Label plus = new Label();
		plus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new NewElementEvent(type));
			}
		});
		add(plus);
	}

	@Override
	public void notifyObserver(Observable source) {
	}
}
