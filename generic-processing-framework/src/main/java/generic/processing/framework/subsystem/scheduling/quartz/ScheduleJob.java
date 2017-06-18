/**
 * 
 */
package generic.processing.framework.subsystem.scheduling.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import generic.processing.framework.subsystem.ISubsystem;
import generic.processing.framework.subsystem.SubsystemException;
import generic.processing.framework.subsystem.scheduling.ISchedule;
import generic.processing.framework.subsystem.scheduling.SchedulerException;
import generic.processing.framework.subsystem.scheduling.quartz.DefaultScheduledEvent;

/**
 * This is the Quartz Scheduling area of the framework.
 * 
 * This is intended to be used to Schedule Events to kick off some form of processing such as;
 * <ol>
 * 		<li>ParkedTradeMessageProcessing</li>
 * 		<li>LostTradeRecovery</li>
 * 		<li>Swift Payment Processing</li>
 * 		<li>etc...</li>
 * </ol>
 * 
 * @author UP779462
 * 
 */
public class ScheduleJob implements ISchedule, ISubsystem {

	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(ScheduleJob.class);
	
	/**
	 * 
	 */
	//private Class<T> scheduledEventClass = null;

	/**
	 * 
	 */
	public void initiate() throws SubsystemException {
		try {
			logger.info("Initiating Quartz Scheduling subsystem...");
			scheduleJob();
		} catch(Exception e) {
			throw new SubsystemException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * generic.processing.framework.subsystem.scheduling.ISchedule#scheduleJob()
	 */
	public void scheduleJob() throws SchedulerException {
		try {
			
			JobDetail job = JobBuilder.newJob(DefaultScheduledEvent.class)
					.withIdentity("ScheduledEvent").build();			

			/* TO DO :Work out how to inject in a CLASS object for execution... 
			JobDetail job = JobBuilder.newJob(this.scheduledEventClass)
					.withIdentity("ScheduledEvent").build();
			 */					

			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("SimpleJob")
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(10).repeatForever())
					.build();

			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);

		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}
	
	/**
	 * 
	 * @param scheduledEventClass
	 */
	/* TO DO: see above to do section...
	public void setScheduledEventClass(Class<T> scheduledEventClass) {
		this.scheduledEventClass = scheduledEventClass;
	}
	*/
}
