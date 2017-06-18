/**
 * 
 */
package generic.processing.framework.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import generic.processing.framework.data.DataServiceChainDataBlock;
import generic.processing.framework.data.repository.GenericRepository;

/**
 * List All Data Service service for hooking service chains into 
 * Data Access activity for adding data
 * 
 * @author UP779462
 *
 */
public class ListAllDataService implements IService {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(ListAllDataService.class);
	
	/**
	 * Repository for managing DB state
	 */
	@Autowired
	GenericRepository repository;

	/**
	 * 
	 */
	public ListAllDataService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		logger.info("ListAllDataService being called...");
		long start = System.currentTimeMillis();		
		DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
		List<Object> listO = repository.get(dataBlock.getType());
		dataBlock.setOutput(listO);
		long end = System.currentTimeMillis();
		logger.info("ListAllDataService should now be processed in " + (end-start) + "ms");
		return true;
	}

}
