/**
 * 
 */
package generic.processing.framework.service.chain;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import generic.processing.framework.service.IService;

/**
 * This is a Default Service Chain executor.  I do not foresee 
 * any need to provide any other implementation to call 
 * ServiceChain implementation other than with this.
 * <br>
 * However, having worked in this industry for many 
 * many years nothing would or has surprised me to date.
 * 
 * @author UP779462
 * 
 */
public class DefaultServiceChainExecutor extends AbstractServiceChain  implements IServiceChain {

	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(DefaultServiceChainExecutor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * generic.processing.framework.service.chain.IServiceChain#runChain(java.lang
	 * .Object, java.util.List)
	 */
	public boolean runChain(Object obj, List<Object> responseList)
			throws ServiceChainException {
		try {
			long start = System.currentTimeMillis();
			Iterator<IService> iter = serviceChain.iterator();
			while (iter.hasNext()) {
				IService service = iter.next();
				logger.debug("Executing : " + service);
				service.execute(obj, responseList);
				if (this.getStopChainOnError()) {
					if (null != responseList && !responseList.isEmpty()) {
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
