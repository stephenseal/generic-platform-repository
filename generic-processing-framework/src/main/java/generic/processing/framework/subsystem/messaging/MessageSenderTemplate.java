/**
 * 
 */
package generic.processing.framework.subsystem.messaging;

import org.apache.log4j.Logger;

import generic.processing.framework.serialisation.ISerialise;

/**
 * The message sending template for use within the messaging 
 * subsystem
 * 
 * @author UP779462
 *
 */
public class MessageSenderTemplate {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(MessageSenderTemplate.class);
	
	/**
	 * The Deserialiser
	 */
	private ISerialise deserialiser;
	
	/**
	 * Destination
	 */
	private String destination;
	
	/**
	 * Message to Send
	 */
	private Object message;
	
	/**
	 * 
	 * @param deserialiser
	 */
	public void setDeserialiser(ISerialise deserialiser) {
		this.deserialiser = deserialiser;
	}
	
	/**
	 * 
	 * @return
	 */
	public ISerialise getDeserialiser() {
		return this.deserialiser;
	}
	
	/**
	 * 
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDestination() {
		return this.destination;
	}
	
	/**
	 * 
	 * @param message
	 */
	public void setMessage(Object message) {
		this.message = message;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getMessage() {
		return this.message;
	}
			


}
