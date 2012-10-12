package ch.sebastienzurfluh.swissmuseum.parcours.client.patterns;

/**
 * Observers watch some {@code Observable}, waiting for something to happen.
 *
 * @author Sebastien Zurfluh
 */
public interface Observer {
	/**
	 * Notify the observer something happened to what it was listening to.
	 * 
	 * @param source of the modification.
	 */
	public void notifyObserver(Observable source);
}
