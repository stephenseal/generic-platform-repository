/**
 * 
 */
package generic.processing.framework.subsystem.test;

import org.apache.log4j.Logger;

import generic.processing.framework.subsystem.ISubsystem;
import generic.processing.framework.subsystem.SubsystemException;

/**
 * Dummy Subsystem to enable the testing of the <code>GenericSystem</code>
 * to ensure that it locates and "starts" this subsystem
 * 
 * @author UP779462
 *
 */
public class DummySubsystem implements ISubsystem {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(DummySubsystem.class);

	/* (non-Javadoc)
	 * @see generic.processing.framework.subsystem.ISubsystem#initiate()
	 */
	public void initiate() throws SubsystemException {
		logger.info("DummySubsystem is initiated...");
	}

}
