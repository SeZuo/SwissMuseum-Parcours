package ch.sebastienzurfluh.client;

import ch.sebastienzurfluh.client.control.AppPresenter;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SwissMuseumParcours implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		AppPresenter appController = new AppPresenter();
		
		appController.start();
	}
}
