/**
 * 
 */
package generic.processing.framework.subsystem.messaging;

import java.util.List;

/**
 * This is the interface that defines the required processing for 
 * sending a message through the Messaging Subsystem
 * <br>
 * This is catering for both single send and bulk sending paradigms
 * 
 * @author UP779462
 *
 */
public interface IMessageProducer {
	
	/**
	 * Sends a single message
	 * 
	 * @param messageSenderTemplate
	 * @throws MessagingException
	 */
	void sendMessage(MessageSenderTemplate messageSenderTemplate) throws MessagingException;
	
	/**
	 * Sends a batch of messages 
	 * 
	 * @param messageSenderTemplateList
	 * @throws MessagingException
	 */
	void sendMessages(List<MessageSenderTemplate> messageSenderTemplateList) throws MessagingException;

}
