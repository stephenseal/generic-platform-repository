/**
 * 
 */
package generic.processing.framework.service;

import java.util.List;

/**
 * This is the Service Interface model that all services that will be 
 * implement. 
 * 
 * @author UP779462
 *
 */
public interface IService {

	/**
	 * This is the Service Interface executor which ALL the 
	 * organisations Services will conform to
	 * 
	 * @param obj
	 * @param errorList
	 * @return
	 * @throws ServiceException
	 */
	boolean execute(Object obj, List<Object> errorList) throws ServiceException;
}
