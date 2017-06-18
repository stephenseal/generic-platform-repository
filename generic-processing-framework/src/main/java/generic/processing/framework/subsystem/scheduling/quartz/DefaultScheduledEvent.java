/**
 * 
 */
package generic.processing.framework.subsystem.scheduling.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

/**
 * A Test class for ensuring the Quartz Scheluder runs correctly.
 * 
 * @author UP779462
 *
 */
public class DefaultScheduledEvent implements Job {
	
	/**
	 * 
	 */
	static Logger logger = Logger.getLogger(DefaultScheduledEvent.class);
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	
		logger.info("executing ScheduledEvent : " + this);
		logger.info("Job Detail : " + jobExecutionContext.getJobDetail());
		logger.info("trigger : " + jobExecutionContext.getTrigger());
		Trigger trigger = jobExecutionContext.getTrigger();
		logger.info("trigger Class : " + trigger.getClass());
		logger.info("trigger JobKey : " + trigger.getJobKey());
		logger.info("trigger Key : " + trigger.getKey());

	}
	
}
