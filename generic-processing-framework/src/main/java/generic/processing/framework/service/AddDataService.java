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
 * Add Data Service service for hooking service chains into 
 * Data Access activity for adding data
 * 
 * @author UP779462
 *
 */
public class AddDataService implements IService {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(AddDataService.class);
	
	/**
	 * Repository for managing DB state
	 */
	@Autowired
	GenericRepository repository;

	/**
	 * 
	 */
	public AddDataService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		logger.info("AddDataService being called...");
		long start = System.currentTimeMillis();		
		DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
		Object inputObj =dataBlock.getInput(); 
		logger.info("Object to ADD : " + inputObj);
		repository.add(inputObj);
		long end = System.currentTimeMillis();
		logger.info("AddDataService should now be processed in " + (end-start) + "ms");
		return true;
	}

}
