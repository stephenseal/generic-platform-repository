/**
 * 
 */
package generic.processing.framework.subsystem.http.rest.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import generic.processing.framework.data.DataServiceChainDataBlock;
import generic.processing.framework.service.chain.DataServiceChainMap;
import generic.processing.framework.service.chain.IServiceChain;


/**
 * This is the single REST controller for all
 * calls that are made to GET/POST/PUT and DELETE data.
 * <br>
 * The request will :end: with the entity 
 * that is being acted upon.
 *  
 * @author UP779462
 * @version 1.0
 */
@RestController("genericRestController")
public class GenericRestController {
	
	/**
	 * LOGGER for this RestController...
	 */
	static Logger logger = Logger.getLogger(GenericRestController.class);
	
	/**
	 * The ServiceChain(s) to execute
	 */
	@Autowired
	@Qualifier("dataServiceChainMap")
	private DataServiceChainMap serviceChainMap;

	/**
	 * Test message for use to ensure communication is being
	 * achieved when running up the framework for HTTP-RESTful 
	 * needs
	 */
	private String testMessage = "Hello ... look ... testMessage ... It works! ...";

	/**
	 * Default Constructor (does nothing!)
	 */
	public GenericRestController() {
		//TODO: something maybe later...
	}
	
	/**
	 * This retrieves the list of data that exists for the 
	 * called :entity" which is part of the URL being called
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testMessage/", method = RequestMethod.GET, produces = "text/plain")
	public ResponseEntity<String> testMessage(HttpServletRequest request) {
		logger.info("Calling testMessage...");
		return new ResponseEntity<String>(this.testMessage, HttpStatus.OK);
	}	
	
	/**
	 * This retrieves the list of data that exists for the 
	 * called :entity" which is part of the URL being called
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/restquery/*/*/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Object>> query(@PathVariable("id") int id, HttpServletRequest request) {	
		String queryName = null;
		DataServiceChainDataBlock obj = resolveURLRequest(request);
		obj.setInput(new Integer(id));		
		List<Object> errorList = null;
		try {
			IServiceChain serviceChain = this.serviceChainMap.getServiceChain("QUERY");
			serviceChain.runChain(obj, errorList);
			if (null != obj) {
				List<Object> objList = (List<Object>)obj.getOutput();
				if(null != objList && objList.size() > 0) {
					logger.info("Number of elements : " + objList.size());
					return new ResponseEntity<List<Object>>(objList, HttpStatus.OK);
				}
			}
		} catch(Exception e) {
			logger.info("We have failed to 'get' " + obj + " : " + e.getMessage() );
			return new ResponseEntity<List<Object>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Object>>(HttpStatus.NO_CONTENT);		
	}	
	
	/**
	 * This retrieves the list of data that exists for the 
	 * called :entity" which is part of the URL being called
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/rest/*/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Object>> listAllObjects(HttpServletRequest request) {
		DataServiceChainDataBlock obj = resolveURLRequest(request);
		logger.info("ListAll called for : " + obj.getType());		
		List<Object> errorList = null;
		try {
			IServiceChain serviceChain = this.serviceChainMap.getServiceChain("LISTALL");
			serviceChain.runChain(obj, errorList);
			if (null != obj) {
				List<Object> objList = (List<Object>)obj.getOutput();
				if(null != objList && objList.size() > 0) {
					logger.info("Number of elements : " + objList.size());
					return new ResponseEntity<List<Object>>(objList, HttpStatus.OK);
				}
			}
		} catch(Exception e) {
			logger.info("We have failed to 'get' " + obj + " : " + e.getMessage() );
			return new ResponseEntity<List<Object>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Object>>(HttpStatus.NO_CONTENT);		
	}	
	
	/**
	 * This is the retrieve an individual entities data 
	 * based on the "key" being requested 
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rest/*/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getObject(@PathVariable("id") int id, HttpServletRequest request) {
		DataServiceChainDataBlock obj = resolveURLRequest(request);		
		obj.setInput(new Integer(id));
		List<Object> errorList = null;
		try {
			IServiceChain serviceChain = serviceChainMap.getServiceChain("GET");
			serviceChain.runChain(obj, errorList);
			logger.info("runChain object : " + obj);
			if (null != obj) {
				if(null != obj.getOutput()) {
					logger.info("Fetched : " + obj);			
					return new ResponseEntity<Object>(obj.getOutput(), HttpStatus.OK);
				} else {
					logger.info("getOutput is NULL...");
				}
			}
		} catch(Exception e) {
			logger.info("Error getting information for id(" + id + ") and type (" + obj + ")" );
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping( value="/rest/*/", method=RequestMethod.POST )
	public ResponseEntity<Void> createObject(@RequestBody Object object, HttpServletRequest request) {
		DataServiceChainDataBlock obj = resolveURLRequest(request);		
		try {
			logger.info("Data to ADD : " + object);
			List<Object> errorList = null;
			obj.setInput(object);			
			IServiceChain serviceChain = serviceChainMap.getServiceChain("CREATE");
			serviceChain.runChain(obj, errorList);
		} catch (Exception e) {
			logger.info("creation failed : " + e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);	
		}
		logger.info("creation achieved without exception");		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * This "could" be used to UPDATE indiviual items much 
	 * like the POST method.  Implemented to preserve the 
	 * RESTful intent of this controller	 * 
	 * 
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping( value="/rest/*/", method=RequestMethod.PUT )
	public ResponseEntity<Void> updateObject(@RequestBody Object object, HttpServletRequest request) {
		DataServiceChainDataBlock obj = resolveURLRequest(request);		
		try {
			logger.info("Data to UPDATE : " + object);
			List<Object> errorList = null;
			obj.setInput(object);
			IServiceChain serviceChain = serviceChainMap.getServiceChain("UPDATE");
			serviceChain.runChain(obj, errorList);
		} catch (Exception e) {
			logger.info("creation failed : " + e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);	
		}
		logger.info("update achieved without exception");		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}	
	
	/**
	 * This will set the isDeleted flag to "true" on the 
	 * relevant object  when called
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/rest/*/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteObject(@PathVariable("id") int id, HttpServletRequest request) {
		DataServiceChainDataBlock obj = resolveURLRequest(request);		
		logger.info("Fetching id : " + id);
		Object deleteObj = null;
		obj.setInput(new Integer(id));
		List<Object> errorList = null;
		try {
			IServiceChain serviceChain = serviceChainMap.getServiceChain("GET");
			serviceChain.runChain(obj, errorList);
			logger.info("runChain object : " + obj);
			if (null != obj) {
				if(null != obj.getOutput()) {
					logger.info("Fetched : " + obj);
					deleteObj = obj.getOutput();
				} else {
					logger.info("getOutput is NULL...");
				}
			}
		} catch(Exception e) {
			logger.info("deletion [get] failed : " + e.getMessage());
			return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);				
		}
		try {
			String resolvedType = obj.getType();
			obj = new DataServiceChainDataBlock();
			obj.setType(resolvedType);
			obj.setInput(deleteObj);
			IServiceChain serviceChain = serviceChainMap.getServiceChain("DELETE");
			serviceChain.runChain(obj, errorList);				
			logger.info("Deletion achieved without exception");				
		} catch (Exception e) {
			logger.info("deletion failed : " + e.getMessage());
			return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);	
		}
		return new ResponseEntity<Object>(HttpStatus.OK);		
	}

	/**
	 * This will take the URL and then determine what 
	 * object is being acted upon
	 * 
	 * @param request
	 * @return
	 */
	private DataServiceChainDataBlock resolveURLRequest(HttpServletRequest request) {
		DataServiceChainDataBlock obj  = new DataServiceChainDataBlock();
		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String requestEntity[] = restOfTheUrl.split("/");
		logger.info("requestEntity : " + requestEntity[2]);
		if (Character.isUpperCase(requestEntity[2].charAt(0))) {
			obj.setType(requestEntity[2]);
		} else {
			obj.setType(requestEntity[2].substring(0, 1).toUpperCase() +
					requestEntity[2].substring(1));
		}
		logger.info("requestEntity.length : " + requestEntity.length);
		if(requestEntity.length > 3) {
			obj.setQuery(requestEntity[3]);
		}
		logger.info("Request : " + obj);
		return obj;
	}

	/**
	 * The test message to display when calling
	 * <br>
	 * "[url of choice]/testMessage/"
	 * 
	 * @param testMessage
	 */
	public void setTestMessage(String testMessage) {
		this.testMessage = testMessage;
	}

	/**
	 * @param serviceChainMap the serviceChainMap to set
	 */
	public void setServiceChainMap(Map<String, IServiceChain> serviceChainMap) {
//		this.serviceChainMap = serviceChainMap;
	}
	
}
