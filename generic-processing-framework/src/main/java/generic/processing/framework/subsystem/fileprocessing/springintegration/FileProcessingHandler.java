/**
 * 
 */
package generic.processing.framework.subsystem.fileprocessing.springintegration;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import generic.processing.framework.subsystem.ISubsystem;
import generic.processing.framework.subsystem.SubsystemException;

/**
 * This represents the File Moving/Processor subsystem
 * for the framework
 * 
 * @author UP779462
 *
 */
public class FileProcessingHandler implements ISubsystem {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(FileProcessingHandler.class);
	
	/**
	 * The application context file to "initiate"
	 */
	private String applicationContextFile;
	
	/**
	 * 
	 * @param applicationContextFile
	 */
	public FileProcessingHandler(String applicationContextFile) {
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
