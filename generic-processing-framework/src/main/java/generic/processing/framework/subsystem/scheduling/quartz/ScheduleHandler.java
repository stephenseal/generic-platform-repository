/**
 * 
 */
package generic.processing.framework.subsystem.scheduling.quartz;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import generic.processing.framework.subsystem.ISubsystem;
import generic.processing.framework.subsystem.SubsystemException;

/**
 * This is the handler for the scheduler subsystem.  It allows the
 * implementation of ScheduleJob implementations 
 * 
 * @author UP779462
 *
 */
public class ScheduleHandler implements ISubsystem {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(ScheduleHandler.class);
	
	/**
	 * 
	 */
	private String applicationContextFile;
	
	/**
	 * @param applicationContextFile
	 */
	public ScheduleHandler(String applicationContextFile) {
		this.applicationContextFile = applicationContextFile;
	}
	

	/* (non-Javadoc)
	 * @see generic.processing.framework.subsystem.ISubsystem#initiate()
	 */
	public void initiate() throws SubsystemException {
		logger.info("Initiating the Scheduling Subsystem...");
		ApplicationContext context = new ClassPathXmlApplicationContext(this.applicationContextFile);
	}

}
