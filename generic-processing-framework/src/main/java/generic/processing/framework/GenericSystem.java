/**
 * 
 */
package generic.processing.framework;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import generic.processing.framework.subsystem.ISubsystem;
import generic.processing.framework.subsystem.SubsystemException;

/**
 * This is the stand-alone Generic System Starter route. 
 * <br>
 * With this you can start any configured SubSystem 
 * @see generic.processing.framework.subsystem.ISubsystem
 * <br>
 * Currently there are the following allowable subsystems present
 * within this implementation
 * <br>
 * <ul>
 * 		<li>Messaging @see generic.processing,framework.subsystem.messaging.jms.MessageHandler</li>
 * 		<li>HTTP @see generic.processing,framework.subsystem.http.tomcat.HttpHandler
 * </ul>
 * <br>
 * Future subsystems will include File Transfer to complete the 
 * available set provided for by this implementation 
 * 
 * @author UP779462
 * 
 */
//@ComponentScan( basePackageClasses = generic.processing.framework.subsystem.http.controller.GenericRestController.class )
public class GenericSystem {

	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(GenericSystem.class);	

	/**
	 * This will load any configured ISubsystem interface objects.
	 * <br> 
	 * This should be whatever is present in the passed in ApplicationContext file
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			logger.info("Starting GenericSystem...");
			String applicationContext = null;
			if(null != System.getProperty("applicationContext")) {
				applicationContext = System.getProperty("applicationContext");
			} else {
				logger.info("Propert 'applicationContext' is not supplied");
				System.exit(-1);
			}
			logger.info("Using ApplicationContext : " + applicationContext);
			ClassPathXmlApplicationContext appContext = 
				new ClassPathXmlApplicationContext(applicationContext);
		    Map<String, ISubsystem> subsystemMap = appContext.getBeansOfType(ISubsystem.class);
	        for (ISubsystem subsystem : subsystemMap.values()) {
	        	logger.info("Initiating : " + subsystem);
	            subsystem.initiate();
	        }
			logger.info("All Subsystems should now be initiated!");	        
		} catch (SubsystemException se) {
			logger.info(se.getMessage());
			System.exit(-2);
		} catch (Exception e) {
			logger.info(e.getMessage());			
			System.exit(-3);
		}
	}
}
