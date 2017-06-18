/**
 * 
 */
package generic.processing.framework.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import generic.processing.framework.data.DataServiceChainDataBlock;
import generic.processing.framework.data.repository.GenericRepository;
import generic.processing.framework.service.component.IServiceComponent;

/**
 * Get Data Service service for hooking service chains into 
 * Data Access activity for adding data
 * 
 * @author UP779462
 *
 */
public class GetDataService implements IService {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(GetDataService.class);
	
	/**
	 * Repository for managing DB state
	 */
	@Autowired
	GenericRepository repository;

	/**
	 * 
	 */
	public GetDataService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		logger.info("GetDataService being called...");
		long start = System.currentTimeMillis();
		DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
		Integer keyId = (Integer)dataBlock.getInput();
		Object o = repository.get(dataBlock.getType(), keyId.intValue());
		dataBlock.setOutput(o);
		long end = System.currentTimeMillis();
		logger.info("GetDataService should now be processed in " + (end-start) + "ms");
		return true;
	}

}
