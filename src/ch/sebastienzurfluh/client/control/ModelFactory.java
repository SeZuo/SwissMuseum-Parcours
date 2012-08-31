/*
 * Copyright 2012 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.client.control;

import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.io.CakeConnector;

/**
 * Create test data with this factory.
 * @author Sebastien Zurfluh
 *
 */
public class ModelFactory {
	enum Connector {
		TEST, CAKE;
	}
	
	/*
	 * Create the right model depending of the environment. See the Config class for more details.
	 */
	public static Model createModel() {
		return Config.TEST_MODE ? createModel(Connector.TEST) : createModel(Connector.CAKE);
	}
	
	private static Model createModel(Connector connector) {
		switch (connector) {
			case TEST:
			case CAKE:
				return new Model(new CakeConnector());
			default:
				throw(new Error(""));
		}
	}
}
