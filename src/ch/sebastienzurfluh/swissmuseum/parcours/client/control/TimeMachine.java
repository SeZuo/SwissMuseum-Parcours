package ch.sebastienzurfluh.swissmuseum.parcours.client.control;

import java.util.LinkedList;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;

/**
 * Singleton that handles ValueChange events (history related).
 *
 * @author Sebastien Zurfluh
 *
 */
public class TimeMachine implements ValueChangeHandler<String> {
	/**
	 * TimeMachine is a singleton. Furthermore, you can't  have an instance of this because you
	 * don't need it anyway. Use {@code TimeMachine.newSnapshot(Command)} instead.
	 */
	private TimeMachine() {
		History.addValueChangeHandler(this);
	}
	
	@SuppressWarnings(value = { "unused" })
	private static TimeMachine instance = new TimeMachine();
	
	
	private static LinkedList<Command> history = new LinkedList<Command>();


	/**
	 * Stores a command that will save the current state.
	 * We'll restore this step if the back button is pressed.
	 * @param commandToReturnToTheCurrentState a command to return to the current state
	 */
	public static void newSnapshot(Command commandToReturnToTheCurrentState) {
		history.add(commandToReturnToTheCurrentState);
		History.newItem(history.size()-1 + "");
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

		int newValue = Integer.parseInt(event.getValue());
		
		System.out.println("TimeMachine: value " + newValue);
		
		if(newValue < history.size()) {
			Command command = history.get(newValue);
			
			// remove future from the list before we go back
			history.subList(newValue, history.size()).clear();
			
			System.out.println("TimeMachine: Executing command to get back");
			
			command.execute();
		}
	}
}
