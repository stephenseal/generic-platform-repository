/**
 * 
 */
package generic.processing.framework.service.component;

import java.util.List;

import org.apache.log4j.Logger;

import generic.processing.framework.service.component.IServiceComponent;
import generic.processing.framework.service.component.ServiceComponentException;

/**
 * This is a test class for use in running the overall generic architecture
 * 
 * @author UP779462
 *
 */
public class DummyServiceComponent implements IServiceComponent {
	
	static Logger logger = Logger.getLogger(DummyServiceComponent.class);

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.component.IServiceComponent#process(java.lang.Object, java.util.List)
	 */
	public boolean process(Object obj, List<Object> errorList)
			throws ServiceComponentException {
		logger.info("processing ServiceComponent...");
		return true;
	}

}
