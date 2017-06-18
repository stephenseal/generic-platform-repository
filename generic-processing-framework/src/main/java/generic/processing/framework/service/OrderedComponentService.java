/**
 * 
 */
package generic.processing.framework.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import generic.processing.framework.service.component.IServiceComponent;

/**
 * This executes a set of Service Component Beans as specified 
 * and in the order specified
 * 
 * @author UP779462
 *
 */
public class OrderedComponentService implements IService {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(OrderedComponentService.class);
	
	/**
	 * The list of IServiceComponents to execute
	 */
	private List<IServiceComponent> serviceComponentList;
	
	/**
	 * Executes the list of configured ServiceComponents 
	 * contained within Spring configuration (XML or Java etc.)
	 * 
	 * @param Object
	 * @param List<Object>
	 * @throws ServiceException
	 */
	public boolean execute(Object obj, List<Object> responseList) throws ServiceException {
		try {
			long start = System.currentTimeMillis();			
			Iterator<IServiceComponent> iter = this.serviceComponentList.iterator();
			while(iter.hasNext()) {
				IServiceComponent serviceComponent = iter.next();			
				logger.debug("Initiating : " + serviceComponent);
				serviceComponent.process(obj, responseList);
			}
			long end = System.currentTimeMillis();
			logger.info("All ServiceComponents should now be processed in " + (end-start) + "ms");
			return true;
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Setter method for loading up the ServiceComponent list 
	 * to be executed
	 * 
	 * @param serviceComponentList
	 */
	public void setServiceComponentList(List<IServiceComponent> serviceComponentList) {
		this.serviceComponentList = serviceComponentList;
	}

}
