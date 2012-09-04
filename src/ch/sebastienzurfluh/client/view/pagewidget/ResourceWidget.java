package ch.sebastienzurfluh.client.view.pagewidget;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.ResourceRequest;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.ResourceData;
import ch.sebastienzurfluh.client.patterns.Observable;
import ch.sebastienzurfluh.client.patterns.Observer;

public class ResourceWidget extends VerticalPanel implements Observer {
	private Model model;
	private DataReference reference;
	private EventBus eventBus;
	
	
	private Image image = new Image("resources/images/icons/loading.gif");
	private Label title = new Label();
	private Label details = new Label();
	
	
	// Styles
	private String primaryStyle = "pageWidget-image";
	private String containerExtension = "-container";
	private String titleExtension = "-title";
	private String detailsExtension = "-details";
	private String imageExtension = "-image";
	
	public ResourceWidget(DataReference reference, EventBus eventBus, Model model) {
		this.model = model;
		this.reference = reference;
		this.eventBus = eventBus;
		
		setStyleName(primaryStyle+containerExtension);
		image.setStyleName(primaryStyle+imageExtension);
		title.setStyleName(primaryStyle+titleExtension);
		details.setStyleName(primaryStyle+detailsExtension);
		
		this.add(image);
		this.add(title);
		this.add(details);
		
		
		model.allNeededResourcesObservable.subscribeObserver(this);
		
		// request data AFTER WE START LISTENING
		eventBus.fireEvent(new ResourceRequest(reference));
	}

	@Override
	public void notifyObserver(Observable source) {
		for (ResourceData resource : model.getAllNeededResources()) {
			if (resource.getReference().equals(this.getReference())) {
				image.setAltText(resource.getTitle());
				image.setUrl(resource.getURL());
				title.setText(resource.getTitle());
				details.setText(resource.getDetails());
				model.allNeededResourcesObservable.unsubscribeObserver(this);
				return;
			}
		}
	}

	private DataReference getReference() {
		return reference;
	}
	
}
