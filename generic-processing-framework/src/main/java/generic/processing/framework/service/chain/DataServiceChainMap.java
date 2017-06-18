/**
 * 
 */
package generic.processing.framework.service.chain;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * This represents a ServiceChain map that represents
 * the Service Chains that can be used for the general
 * Data Architecture use cases;
 * <ul>
 * 		<li>AddDataServiceChain</li>
 * 		<li>DeleteDataServiceChain</li>
 * 		<li>GetDataServiceChain</li>
 * 		<li>ListAllDataServiceChain</li>
 * 		<li>UpdateDataServiceChain</li>
 * </ul>
 * 
 * @author UP779462
 *
 */
public class DataServiceChainMap {
	
	/**
	 * LOGGER for this RestController...
	 */
	static Logger logger = Logger.getLogger(DataServiceChainMap.class);
	
	/**
	 * 
	 */
	private Map<String, IServiceChain> serviceChainMap;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public IServiceChain getServiceChain(String key) {
		logger.info("getServiceChain : " + key);
		return this.serviceChainMap.get(key);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Map<String, IServiceChain> getServiceChainMap() {
		return this.serviceChainMap; 
	}
	
	/**
	 * 
	 * @param serviceChainMap
	 */
	public void setServiceChainMap(Map<String, IServiceChain> serviceChainMap) {
		this.serviceChainMap = serviceChainMap;
	}

}
