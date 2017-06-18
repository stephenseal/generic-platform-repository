/**
 * 
 */
package generic.processing.framework.service.chain;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import generic.processing.framework.service.IService;

/**
 * This is a ServiceChain that will enable the adding of data through 
 * the various channel sub-systems that are available;
 * <br>
 * <ul>
 * 		<li>HTTP-RESTful</li>
 * 		<li>HTTP-SOAPful</li>
 * 		<li>JMS Messagingful</li>
 * 		<li>FTP File Transfer</li>
 *  </ul>
 *  * 
 * @author UP779462
 * @version 1.0
 *
 */
public class ListAllDataServiceChain extends AbstractServiceChain implements IServiceChain {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(ListAllDataServiceChain.class);

	/**
	 * 
	 */
	public ListAllDataServiceChain() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This chain will run the following services;
	 * <br>
	 * <ul>
	 * 		<li>ListAllDataService</li>
	 * 		<li>GenericDAO2DTOService</li>
	 * </ul>
	 * 
	 * @see generic.processing.framework.service.chain.IServiceChain#runChain(java.lang.Object, java.util.List)
	 */
	public boolean runChain(Object obj, List<Object> errorList) throws ServiceChainException {
		logger.info(this.getName() + " being called...");
		try {
			long start = System.currentTimeMillis();
			Iterator<IService> iter = serviceChain.iterator();
			while (iter.hasNext()) {
				IService service = iter.next();
				logger.debug("Executing : " + service);
				service.execute(obj, errorList);
				if (this.getStopChainOnError()) {
					if (null != errorList && !errorList.isEmpty()) {
						logger.debug("Business error(s) encountered and requested to stop chain at this point...");
						break;
					}
				}
			}
			long end = System.currentTimeMillis();			
			logger.info("ServiceChain " + this.getName() + " execution now ended in " + (end-start) + "ms!");
			return true;
		} catch (Exception e) {
			throw new ServiceChainException(e);
		}
	}
	
}
