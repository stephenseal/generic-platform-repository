package generic.processing.framework.subsystem.messaging.jms;

import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import generic.processing.framework.serialisation.ISerialise;
import generic.processing.framework.subsystem.messaging.IMessageProducer;
import generic.processing.framework.subsystem.messaging.MessageSenderTemplate;
import generic.processing.framework.subsystem.messaging.MessagingException;

/**
 * Class for handling the production of JMS messages from within the 
 * Generic Architecture Framework.
 * 
 * @author UP779462
 * 
 */
@Component
public class MessageProducer implements IMessageProducer {

	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(MessageProducer.class);

	/**
     * 
     */
	private JmsTemplate jmsTemplate;

	/**
	 * 
	 */
	public ISerialise serialiser;

	/**
	 * 
	 * @param messageSenderTemplate
	 */
	public void sendMessage(MessageSenderTemplate messageSenderTemplate)
			throws MessagingException {
		logger.info("sendMessage called... : " + serialiser);
		logger.info("messageSenderTemplate : " + messageSenderTemplate);
		if(null != messageSenderTemplate) {
			logger.info("message : " + messageSenderTemplate.getMessage());
		}
		try {
			long start = System.currentTimeMillis();
			String tmpText;
			if (null != serialiser) {
				logger.info("About to serialise message...");
				tmpText = (String) serialiser.serialise(messageSenderTemplate
						.getMessage());
			} else {
				logger.info("About to retrieve message...");
				TextMessage msgToSend = (TextMessage)messageSenderTemplate.getMessage(); 
				tmpText = msgToSend.getText();
			}
			final String text = tmpText;
			logger.info("About to send message...");
			jmsTemplate.send(messageSenderTemplate.getDestination(),
					new MessageCreator() {
						public Message createMessage(Session session)
								throws JMSException {
							TextMessage message = session
									.createTextMessage(text);

							logger.debug("Sending message: " + text);

							return message;
						}
					});
			long end = System.currentTimeMillis();
			logger.info("Message sent to "
					+ messageSenderTemplate.getDestination() + " in "
					+ (end - start) + "ms");
		} catch (Exception e) {
			logger.info("Exception occured : " + e);
		}

	}

	/**
	 * 
	 * @param messageSenderTemplateList
	 * @throws MessagingException
	 */
	public void sendMessages(
			List<MessageSenderTemplate> messageSenderTemplateList)
			throws MessagingException {
		Iterator<MessageSenderTemplate> iter = messageSenderTemplateList
				.iterator();
		while (iter.hasNext()) {
			sendMessage(iter.next());
		}
	}

	/**
	 * 
	 * @param jmsTemplate
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * 
	 * @param serialiser
	 */
	public void setSerialiser(ISerialise serialiser) {
		this.serialiser = serialiser;
	}

}
