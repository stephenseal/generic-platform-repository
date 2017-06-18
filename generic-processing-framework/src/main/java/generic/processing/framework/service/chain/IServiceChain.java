/**
 * 
 */
package generic.processing.framework.service.chain;

import java.util.List;

import generic.processing.framework.service.IService;

/**
 * Interface for the control and execution of a ServiceChain
 * 
 * @author UP779462
 *
 */
public interface IServiceChain {
	
	/**
	 * 
	 * @param obj
	 * @param errorList
	 * @return
	 * @throws ServiceChainException
	 */
	boolean runChain(Object obj, List<Object> errorList) throws ServiceChainException;

}
