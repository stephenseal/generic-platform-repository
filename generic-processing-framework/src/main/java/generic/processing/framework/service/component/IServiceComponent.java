/**
 * 
 */
package generic.processing.framework.service.component;

import java.util.List;

/**
 * This comprises the lowest level atrifact in the organications Service Stack
 * 
 * @author UP779462
 *
 */
public interface IServiceComponent {

	/**
	 * This is the entry point nin the ServiceComponent stack 
	 * 
	 * @param obj
	 * @param errorList
	 * @return
	 * @throws ServiceComponentException
	 */
	boolean process(Object obj, List<Object> errorList) throws ServiceComponentException;
}
