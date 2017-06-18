/**
 * 
 */
package generic.processing.framework.service.chain;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import generic.processing.framework.service.IService;

/**
 * @author UP779462
 *
 */
public class QueryDataServiceChain extends AbstractServiceChain implements IServiceChain {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(QueryDataServiceChain.class);

	/**
	 * 
	 */
	public QueryDataServiceChain() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.chain.IServiceChain#runChain(java.lang.Object, java.util.List)
	 */
	public boolean runChain(Object obj, List<Object> errorList) throws ServiceChainException {
		try {
			logger.info("Running QueryDataServiceChain...");
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
