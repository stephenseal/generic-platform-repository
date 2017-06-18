/**
 * 
 */
package generic.processing.framework.subsystem.messaging.jms;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import generic.processing.framework.subsystem.ISubsystem;
import generic.processing.framework.subsystem.SubsystemException;

/**
 * This loads all of the relevant Beans from the Application Context and allows 
 * Spring JMS to do its stuff... 
 * 
 * @author UP779462
 *
 */
@ImportResource("classpath*:applicationSubsystem-jms.xml")
public class MessageHandler implements ISubsystem {

	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(MessageHandler.class);
	
	/**
	 * 
	 */
	private String applicationContextFile;
	


	/**
	 * @param applicationContextFile
	 */
	public MessageHandler(String applicationContextFile) {
		this.applicationContextFile = applicationContextFile;
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.subsystem.ISubsystem#initiate()
	 */
	public void initiate() throws SubsystemException {
		logger.info("Initiating the JMS Messaging Subsystem...");
		ApplicationContext context = new ClassPathXmlApplicationContext(this.applicationContextFile);
	}

}
