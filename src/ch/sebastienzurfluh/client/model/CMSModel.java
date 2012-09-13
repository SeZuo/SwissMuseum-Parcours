package ch.sebastienzurfluh.client.model;

import ch.sebastienzurfluh.client.control.eventbus.events.Action;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.patterns.Observable;

/**
 * {@code CMSModel} gives an extended version of the model for the CMSView purposes.
 * It is linked to the original view model in order to switch from one to the other without any
 * overhead.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class CMSModel extends ModelWrapper {

	public CMSModel(Model model) {
		super(model);
	}
	
	private Action currentIntentAction = Action.NONE;
	private DataReference currentIntentReference = DataReference.NONE;
	public Observable currentIntentObservable = new Observable();
	
	/**
	 * <p>Provide the last user intent.</p>
	 * 
	 * <p>CAUTION: because of the asynchronous data loading, this function HAS to be called before
	 * a call to load. The intent will only be taken into account if new data is provided.<br />
	 * Therefore you cannot use this function to switch between edit and browse mode on the current
	 * page.</p>
	 * 
	 * 
	 * @param intentAction
	 * @param reference for the intent.
	 */
	public void setCurrentIntent(Action intentAction, DataReference reference) {
		this.currentIntentAction = intentAction;
		this.currentIntentReference = reference;
		
		currentIntentObservable.notifyObservers();
	}
	
	public Action getCurrentIntentAction() {
		return currentIntentAction;
	}
	
	public DataReference getCurrentIntentReference() {
		return currentIntentReference;
	}
}
