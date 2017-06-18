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
 * @author UP779462
 *
 */
public class Dto2DaoMapperService implements IService {
	
	/**
	 * The Logger... 
	 */
	static Logger logger = Logger.getLogger(Dto2DaoMapperService.class);
	
	/**
	 *	Data Mapping object to perform the actual mapping 
	 *between objects
	 * 
	 */
	@Autowired
	@Qualifier("dto2daomapper")
	private IDataMapper dataMapper;
	

	/**
	 * 
	 */
	public Dto2DaoMapperService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		try {
			logger.info("Dto2DaoMapperService being called...");
			DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
			Object dtoObj = dataBlock.getInput();
			if(dtoObj instanceof java.util.ArrayList) {
				logger.info("We have an instance of ArrayList...");
				List<Object> returnList = new ArrayList<Object>();
				List<Object> objList = (ArrayList<Object>)dtoObj;
				Iterator<Object>iter = objList.iterator();
				while(iter.hasNext()) {
					Object iterObj = iter.next();
					returnList.add(dataMapper.mapObject(iterObj));
				}
				dataBlock.setInput(returnList);
			} else {
				logger.info("A single object is mine ... mwah hah hah hah ... ");
				dataBlock.setInput(dataMapper.mapObject(dtoObj));
			}
			logger.info("Dto2DaoMapperService called...");			
		} catch(Exception e) {
			throw new ServiceException(e);
		}
		return false;
	}

}
