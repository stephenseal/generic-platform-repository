/**
 * 
 */
package ${mapperpackagename};

import org.apache.log4j.Logger;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import generic.processing.framework.data.mapper.DataMappingException;
import generic.processing.framework.data.mapper.IDataMapper;

import ${modelpackagename}.*;

/**
 * This implements the ModelMapper mapping
 * to satisfy mappinrg between DAO to DTO etc.
 * 
 * @author system
 *
 */
public class M2TDataMapper implements IDataMapper {
	
	/**
	 * The Logger...
	 * 
	 */
	static Logger logger = Logger.getLogger(M2TDataMapper.class);
	
	/**
	 * 
	 */
	public M2TDataMapper() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see generic.processing.framework.data.mapper.IDataMapper#mapObject(java.lang.Object)
	 */
	public Object mapObject(Object mapFrom) throws DataMappingException {
		try {
			logger.info("About to mapObject... : " + mapFrom.getClass().getName());
			String className = mapFrom.getClass().getSimpleName();
			logger.info("mapFrom : " + className);
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
			logger.info("MapObject.packageName : ${transferpackagename}");
			Object mappedObj =modelMapper.map(mapFrom, Class.forName("${transferpackagename}." + className));
			logger.info("mappedObject : " + mappedObj);
			return mappedObj;
		} catch(Exception e) {
			logger.info("Exception occured : " + e.getMessage());
			throw new DataMappingException(e);
		}
	}

}
