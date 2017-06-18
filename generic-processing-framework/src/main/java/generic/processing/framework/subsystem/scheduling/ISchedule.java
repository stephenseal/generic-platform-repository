/**
 * 
 */
package generic.processing.framework.subsystem.scheduling;

/**
 * Interface for working within the Scheduling subsystem
 * 
 * @author UP779462
 *
 */
public interface ISchedule {
	
	/**
	 * 
	 * @throws SchedulerException
	 */
	void scheduleJob() throws SchedulerException;

}
