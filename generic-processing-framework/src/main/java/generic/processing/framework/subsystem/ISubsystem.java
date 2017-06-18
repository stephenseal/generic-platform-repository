/**
 * 
 */
package generic.processing.framework.subsystem;

/**
 * This forms the blueprint of the basic GenericSystem subsystem.
 * <br>
 * This will enable the creation of the subsystem for GenericSystem 
 * processing usage.
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
 * @author Steve Seal (XBBKJ36)
 *
 */
public interface ISubsystem {
	
	/**
	 * This will "start" up the relevant subsystems that the GenericSystem has been asked to 
	 * start (via an applicationContext file)
	 * 
	 * @throws SubsystemException
	 */
	void initiate() throws SubsystemException;

}
