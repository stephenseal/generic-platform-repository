/**
 * 
 */
package generic.processing.framework.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import generic.processing.framework.data.DataServiceChainDataBlock;
import generic.processing.framework.data.mapper.IDataMapper;


/**
 * This service calls the object mapping 
 * utility class to map from DAO to DTO
 * 
 * @author UP779462
 *
 */
public class Dao2DtoMapperService implements IService{
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(Dao2DtoMapperService.class);
	
	/**
	 *	Data Mapping object to perform the actual mapping 
	 *between objects
	 * 
	 */
	@Autowired
	@Qualifier("dao2dtomapper")
	private IDataMapper dataMapper;

	/**
	 * 
	 */
	public Dao2DtoMapperService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		try {
			logger.info("Dao2DtoMapperService being called...");
			DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
			Object daoObj = dataBlock.getOutput();
			if(null != daoObj) { // only map if we have something to map!!
				if(daoObj instanceof java.util.ArrayList) {
					List<Object> returnList = new ArrayList<Object>();
					List<Object> objList = (ArrayList<Object>)daoObj;
					Iterator<Object>iter = objList.iterator();
					while(iter.hasNext()) {
						Object iterObj = iter.next();
						returnList.add(dataMapper.mapObject(iterObj));
					}
					dataBlock.setOutput(returnList);
				} else {
					dataBlock.setOutput(dataMapper.mapObject(daoObj));
				}				
			}
			logger.info("Dao2DtoMapperService called...");			
		} catch(Exception e) {
			throw new ServiceException(e);
		}
		return true;
	}

}
