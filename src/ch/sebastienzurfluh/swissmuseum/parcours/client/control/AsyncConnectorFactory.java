package ch.sebastienzurfluh.swissmuseum.parcours.client.control;

import com.google.code.gwt.database.client.Database;
import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.sebastienzurfluh.swissmuseum.core.client.control.ConnectorFactory.Connector;
import ch.sebastienzurfluh.swissmuseum.core.client.control.DefaultConfig;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.CakeConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.IOConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.LocalCakeConnector;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.DatabaseHandle;
import ch.sebastienzurfluh.swissmuseum.parcours.client.model.LocalStorageConnector;

public class AsyncConnectorFactory {
	
	/**
	 * Create the right model depending of the environment. See the Config class for more details.
	 * 
	 * @param config
	 * @param asyncWithConnector only success is used, there is no place for error.
	 */
	public static void createConnector(
			DefaultConfig config,
			final AsyncCallback<IOConnector> asyncWithConnector) {
		
		if (config.isTestMode()) {
			asyncWithConnector.onSuccess(createConnector(Connector.TEST));
			return;
		}
		
		if(config.isForcedRemoteDB()) {
			asyncWithConnector.onSuccess(createConnector(Connector.CAKE));
			return;
		}
		
		if (Database.isSupported())
			DatabaseHandle.sync(new AsyncCallback<Object>() {
				@Override
				public void onFailure(Throwable caught) {
					System.out.println("AsyncConnectorFactory: sync failed: " + caught.getMessage());
					asyncWithConnector.onSuccess(createConnector(Connector.CAKE));
				}
				
				@Override
				public void onSuccess(Object nil) {
					asyncWithConnector.onSuccess(createConnector(Connector.LOCAL_STORAGE));
				}
			});
		else
			asyncWithConnector.onSuccess(createConnector(Connector.CAKE));
	}
	
	private static IOConnector createConnector(Connector connector) {
		System.out.print("AsyncConnectorFactory: ");
		switch (connector) {
			case TEST:
				System.out.println("TEST Connector provided.");
				return new LocalCakeConnector();
			case CAKE:
				System.out.println("CAKE Connector provided.");
				return new CakeConnector();
			case LOCAL_STORAGE:
				System.out.println("LOCAL_STORAGE Connector provided.");
				return (IOConnector) new LocalStorageConnector();
			default:
				throw(new Error(""));
		}
	}
}
