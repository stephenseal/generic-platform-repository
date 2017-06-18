/**
 * 
 */
package generic.processing.framework.subsystem.messaging;

import java.util.List;

import generic.processing.framework.service.chain.IServiceChain;

/**
 * This provides the blueprint for routing of messages within
 * our generic business processing messaging subsystem
 * 
 * @author UP779462
 *
 */
public interface IMessageRouter {

	/**
	 * Determines route to tabke based upon;
	 * <ul>
	 * 		<li>Who am I</li>
	 * 		<li>Who have I just done</li>
	 *  </ul>
	 *  The returned list of Routes are then used to push messages 
	 *  forward to the appropriate destination with the desired protocol 
	 *  for that specific destination
	 *  
	 * @param serviceChain
	 * @param responseList
	 * @return
	 * @throws MessagingException
	 */
	List<String>getRoutes(IServiceChain serviceChain, List<Object> responseList) throws MessagingException;

}
