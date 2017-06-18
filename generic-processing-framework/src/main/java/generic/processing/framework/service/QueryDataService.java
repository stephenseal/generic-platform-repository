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
 * @author Steve
 *
 */
public class QueryDataService implements IService {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(QueryDataService.class);
	
	/**
	 * Repository for managing DB state
	 */
	@Autowired
	GenericRepository repository;	

	/**
	 * 
	 */
	public QueryDataService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		logger.info("QueryDataService being called...");
		long start = System.currentTimeMillis();		
		DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
		Integer keyId = (Integer)dataBlock.getInput();
		List<Object> listO = repository.query(dataBlock.getType(), dataBlock.getQuery(), keyId.intValue());
		dataBlock.setOutput(listO);
		long end = System.currentTimeMillis();
		logger.info("QueryDataService should now be processed in " + (end-start) + "ms");
		return true;
	}

}
