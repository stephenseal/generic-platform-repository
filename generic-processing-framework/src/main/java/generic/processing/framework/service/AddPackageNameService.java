/**
 * 
 */
package generic.processing.framework.service;

import java.util.List;

import org.apache.log4j.Logger;

import generic.processing.framework.data.DataServiceChainDataBlock;
import generic.processing.framework.service.IService;
import generic.processing.framework.service.ServiceException;

/**
 * @author Steve
 *
 */
public class AddPackageNameService implements IService {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(AddPackageNameService.class);
	
	/**
	 * The packageName for adding to the type 
	 */
	private String packageName;

	/**
	 * 
	 */
	public AddPackageNameService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		logger.info("packageName to prefix : " + this.packageName);
		DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
		String entityType = dataBlock.getType();
		dataBlock.setType(this.packageName + "." + entityType); 
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
