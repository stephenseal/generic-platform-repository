/**
 * 
 */
package generic.processing.framework.rulesengine.drools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import generic.processing.framework.rulesengine.IRulesEngine;
import generic.processing.framework.rulesengine.RuleExecutionResult;
import generic.processing.framework.rulesengine.RulesEngineException;

/**
 * This is the concrete implementation of an IRulesEngine.
 * <br>
 * In this case it is on the back of the DROOLs rules engine.
 * <br>
 * This is the only implementation as part of this framework as
 * it has been evaluated to be adequate for the purposes needed
 * within this framdwork.
 * 
 * @author UP779462
 *
 */
public class RulesEngine implements IRulesEngine {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(RulesEngine.class);	
	
	/**
	 * 
	 */
	KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	
	/**
	 * 
	 */
	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	

	/**
	 * 
	 */
	public RulesEngine() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public RulesEngine(List<String> rulesFiles) {
		Iterator<String> iter = rulesFiles.iterator();
		while(iter.hasNext()) {
			kbuilder.add(ResourceFactory.newClassPathResource(iter.next(), RulesEngine.class), ResourceType.DRL);
			if ( kbuilder.hasErrors() ) {
				logger.debug( kbuilder.getErrors().toString() );
			}
		}
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.rulesengine.IRulesEngine#makeDecision(java.util.List)
	 */
	public List<RuleExecutionResult> makeDecision(List<Object> factList) throws RulesEngineException {
		long start = System.currentTimeMillis();
		List<RuleExecutionResult> resultList = new ArrayList<RuleExecutionResult>();
		logger.debug("inserting facts...");
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ksession.setGlobal("list", new ArrayList<RuleExecutionResult>() );
		Iterator<Object> iter = factList.iterator();
		while(iter.hasNext()) {
			ksession.insert(iter.next());
		}
		logger.debug("firing them their rules...");
		ksession.fireAllRules();
		resultList = (List<RuleExecutionResult>)ksession.getGlobal("list");
		ksession.dispose();
		logger.debug("DecisionRetuls(s) : " + resultList.size());
		long end = System.currentTimeMillis();
		logger.info("Routes determined in " + (end-start) + "ms");
		return resultList;
	}

}
