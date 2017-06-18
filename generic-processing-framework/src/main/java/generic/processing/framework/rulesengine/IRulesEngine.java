/**
 * 
 */
package generic.processing.framework.rulesengine;

import java.util.List;

/**
 * This is the interface for any prospective Decision Engine
 * implementation.  This is envisioned to be a Rules Engine
 * style implementation but, could be anything
 * 
 * @author UP779462
 *
 */
public interface IRulesEngine {
	
	/**
	 * 
	 * @param factList
	 * @return
	 * @throws RulesEngineException
	 */
	List<RuleExecutionResult> makeDecision(List<Object> factList) throws RulesEngineException;

}
