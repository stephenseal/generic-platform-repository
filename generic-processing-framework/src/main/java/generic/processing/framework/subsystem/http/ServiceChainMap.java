/**
 * 
 */
package generic.processing.framework.subsystem.http;

import java.util.Map;

import org.apache.log4j.Logger;

import generic.processing.framework.service.chain.IServiceChain;

/**
 * This is just a way of understanding why my util:map 
 * is not being picked up inside the Spring runtime
 * 
 * @author UP779462
 *
 */
public class ServiceChainMap {
	
	/**
	 * LOGGER for this RestController...
	 */
	static Logger logger = Logger.getLogger(ServiceChainMap.class);

	/**
	 * The actual service chain map
	 */
	private Map<String, IServiceChain> map;

	/**
	 * 
	 */
	public ServiceChainMap() {
		logger.info("Constructing ServiceChainMap...");
	}

	/**
	 * @return the serviceChainMap
	 */
	public Map<String, IServiceChain> getMap() {
		return this.map;
	}

	/**
	 * @param serviceChainMap the serviceChainMap to set
	 */
	public void setMap(Map<String, IServiceChain> map) {
		this.map = map;
	}
	
	

}
