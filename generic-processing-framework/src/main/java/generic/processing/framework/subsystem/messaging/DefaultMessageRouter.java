/**
 * 
 */
package generic.processing.framework.subsystem.messaging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.drools.runtime.StatefulKnowledgeSession;

import generic.processing.framework.rulesengine.IRulesEngine;
import generic.processing.framework.rulesengine.RuleExecutionResult;
import generic.processing.framework.service.chain.IServiceChain;

/**
 * This is used in conjunction with the messaging layer of the 
 * Generic Business Processing Framework implementation
 * <br>
 * This is based on a DROOLs rules implementation which 
 * will ultimately be "uploadable" but, for now is passed as
 * part of the generated JAR implementation and referenced
 * accordingly.
 * <br>
 * The routing rules can be implemented by the relevant 
 * business organisation.  This is just the "executor" of those 
 * routing rules that will allow a message flow to be managed
 * by the framework. 
 * 
 * @author UP779462
 *
 */
public class DefaultMessageRouter implements IMessageRouter {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(DefaultMessageRouter.class);
	
	/**
	 * 
	 */
	private IRulesEngine routingEngine;
	
	/* (non-Javadoc)
	 * @see generic.processing.framework.subsystem.messaging.IMessageRouter#getRoutes(generic.processing.framework.service.chain.IServiceChain, java.util.List)
	 */
	public List<String> getRoutes(IServiceChain serviceChain,
			List<Object> responseList) throws MessagingException {
		try {
			long start = System.currentTimeMillis();
			List<Object> factList = new ArrayList<Object>();
			factList.add(serviceChain);
			factList.add(responseList);
			List<RuleExecutionResult> decisionResultList = routingEngine.makeDecision(factList);
			List<String> routes = new ArrayList<String>();
			Iterator<RuleExecutionResult> iter = decisionResultList.iterator();
			while(iter.hasNext()) {
				routes.add(iter.next().getDecisionDesc());
			}
			long end = System.currentTimeMillis();
			logger.info("Routes determined in " + (end-start) + "ms");
			return routes;
		} catch(Exception e) {
			throw new MessagingException(e);
		}
	}
	

	/**
	 * 
	 * @param routingEngine
	 */
	public void setRoutingEngine(IRulesEngine routingEngine) {
		this.routingEngine = routingEngine;
	}


}
