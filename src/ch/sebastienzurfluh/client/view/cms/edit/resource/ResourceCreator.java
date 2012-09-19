package ch.sebastienzurfluh.client.view.cms.edit.resource;

import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.model.CMSModel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ResourceCreator extends PopupPanel {
	public ResourceCreator(
			CMSModel cmsModel, EventBus eventBus, TextArea insertImageInThisTextArea) {
		super(false, true);
		VerticalPanel resourcePickerPanel = new VerticalPanel();
		
		Label title = new Label("Créer une image.");
		title.setStyleName("resourcePicker-title");
		
		//TODO create the panel with data from cmsModel.getAllResources();
		Button cancel = new Button("Annuler", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ResourceCreator.this.hide();
			}
		});
		cancel.setStyleName("resourcePicker-button");
		
		Button ok = new Button("Créer", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ResourceCreator.this.hide();
			}
		});
		ok.setStyleName("resourcePicker-button");
		
		
		FlowPanel buttonLine = new FlowPanel();
		buttonLine.add(ok);
		buttonLine.add(cancel);

		
		resourcePickerPanel.add(title);
		resourcePickerPanel.add(new Label("Please wait"));
		resourcePickerPanel.add(buttonLine);
		
		
		
		
		setWidget(resourcePickerPanel);
		setGlassEnabled(true);
	}
}