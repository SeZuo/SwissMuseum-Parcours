package ch.sebastienzurfluh.client.model.structure;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;

/**
 * Immutable and unique reference to the data (page or resource).
 * 
 * @author Sebastien Zurfluh
 */
public class DataReference {
	private DataType type;
	private int referenceId;
	
	/**
	 * Creates an unique reference to the data (page or resource).
	 * 
	 * @param type the type of the data to reference
	 * @param referenceId the id of the data to reference
	 */
	public DataReference(DataType type, int referenceId) {
		this.setType(type);
		this.setReferenceId(referenceId);
	}

	/**
	 * @return the referenceId
	 */
	public int getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	private void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the type
	 */
	public DataType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	private void setType(DataType type) {
		this.type = type;
	}
}
