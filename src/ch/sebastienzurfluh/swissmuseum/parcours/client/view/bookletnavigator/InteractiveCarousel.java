package ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.widget.Carousel;

/** This extended {@link Carousel} sends notifications through {@link EventBus} when a page is 
 * turned.
 *
 * @author Sebastien Zurfluh
 *
 */
public class InteractiveCarousel extends Carousel {
	public InteractiveCarousel(
			final EventBus eventBus, final Model model) {
		super();
		
		addSelectionHandler(new SelectionHandler<Integer>() {
			int previousSelection = -1;
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (previousSelection == event.getSelectedItem()) {
					previousSelection = -1;
					eventBus.fireEvent(new PageChangeRequest(DataReference.SUPER));
					return;
				}
				
				if (previousSelection != -1 && previousSelection < event.getSelectedItem()) {
					previousSelection = event.getSelectedItem();
					eventBus.fireEvent(new PageChangeRequest(model.getNextPageMenu().getReference()));
					return;
				}
				
				if (previousSelection > event.getSelectedItem()) {
					previousSelection = event.getSelectedItem();
					eventBus.fireEvent(new PageChangeRequest(model.getPreviousPageMenu().getReference()));
					return;
				}
				
				previousSelection = event.getSelectedItem();
			}
		});
	}
}