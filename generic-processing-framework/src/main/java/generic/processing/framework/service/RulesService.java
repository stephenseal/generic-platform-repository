/**
 * 
 */
package generic.processing.framework.service;

import java.util.List;

import org.apache.log4j.Logger;

import generic.processing.framework.rulesengine.IRulesEngine;

/**
 * This represents an implementation of a Rules Service 
 * that can be invoked as a specific Service.  This is a concrete
 * implementation within the framework itself as the only 
 * thing required is access to data AND the rules to execute 
 * against the data.
 * 
 * @author UP779462
 *
 */
public class RulesService implements IService {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(RulesService.class);

	/**
	 * The rules engine to use in the RulesService
	 * <br>
	 * For the purposes of this framework class, it is the Drools Rules Engine
	 * implementation @see generic.processing.framework.rulesengine.drools,RulesEngine
	 */
	IRulesEngine rulesEngine = null;

	/**
	 * 
	 */
	public RulesService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.IService#execute(java.lang.Object, java.util.List)
	 */
	public boolean execute(Object obj, List<Object> errorList) throws ServiceException {
		logger.info("RulesService being called...");
		return false;
	}
	
	/**
	 * 
	 * @param rulesEngine
	 */
	public void setRulesEngine(IRulesEngine rulesEngine) {
		this.rulesEngine = rulesEngine;
	}

}
