/**
 * 
 */
package generic.processing.framework.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import generic.processing.framework.data.DataServiceChainDataBlock;
import generic.processing.framework.service.IService;
import generic.processing.framework.service.ServiceException;

/**
 * @author UP779462
 *
 */
public class MapJson2ObjectService implements IService {
	
	/**
	 * LOGGER for this RestController...
	 */
	static Logger logger = Logger.getLogger(MapJson2ObjectService.class);
	
	/**
	 * 
	 */
	private String packageName;

	/**
	 * Default Constructor 
	 */
	public MapJson2ObjectService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		logger.info("packageName to prefix : " + this.packageName);
		DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
		String entityType = dataBlock.getType();
		dataBlock.setType(this.packageName + "." + entityType);		
		
		logger.info("We are resolving incoming entity... " + dataBlock.getType());
		Class<?> newClass = null;
		try {
			newClass = Class.forName(dataBlock.getType());	
		} catch(Exception e) {
			throw new ServiceException("Cannot convert " + dataBlock.getType() + "into an object", e);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Object object = objectMapper.convertValue(dataBlock.getInput(), newClass);
		logger.info("object : " + object);
		dataBlock.setInput(object);
		return false;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	

}
