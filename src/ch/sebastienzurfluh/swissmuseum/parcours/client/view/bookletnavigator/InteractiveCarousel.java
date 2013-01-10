package ch.sebastienzurfluh.swissmuseum.parcours.client.view.bookletnavigator;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.googlecode.mgwt.ui.client.widget.Carousel;

/** This extended {@link Carousel} sends notifications through {@link EventBus} when a page is 
 * turned.
 *
 * @author Sebastien Zurfluh
 *
 */
public class InteractiveCarousel extends Carousel {
	public InteractiveCarousel(
			final EventBus eventBus) {
		super();
		
		addSelectionHandler(new SelectionHandler<Integer>() {
			int previousSelection = -1;
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == previousSelection) {
					previousSelection = -1;
					// go back to main screen
					eventBus.fireEvent(new PageChangeRequest(DataReference.SUPER));
				} else {
					previousSelection = event.getSelectedItem();
				}
			}
		});
	}
}