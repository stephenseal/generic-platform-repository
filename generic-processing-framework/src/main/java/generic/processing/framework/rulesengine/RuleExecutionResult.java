/**
 * 
 */
package generic.processing.framework.rulesengine;

/**
 * This is the concrete class for receiving the decisions from any 
 * call made to an implementation of a IRulesEngine.
 * <br>
 * These results can then be evaluated to determine next steps etc.
 * 
 * @author UP779462
 *
 */
public class RuleExecutionResult {
	
	/**
	 * 
	 */
	private String decisionCode;
	
	/**
	 * 
	 */
	private String decisionDesc;
	
	/**
	 * 
	 */
	private String decisionMisc;

	/**
	 * 
	 */
	public RuleExecutionResult() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param decisionCode
	 * @param decisionDesc
	 * @param decisionMisc
	 */
	public RuleExecutionResult(String decisionCode, String decisionDesc,
			String decisionMisc) {
		
		this.decisionCode = decisionCode;
		this.decisionDesc = decisionDesc;
		this.decisionMisc = decisionMisc;
	}



	/**
	 * @return the decisionCode
	 */
	public String getDecisionCode() {
		return decisionCode;
	}

	/**
	 * @return the decisionDesc
	 */
	public String getDecisionDesc() {
		return decisionDesc;
	}

	/**
	 * @return the decisionMisc
	 */
	public String getDecisionMisc() {
		return decisionMisc;
	}

	/**
	 * @param decisionCode the decisionCode to set
	 */
	public void setDecisionCode(String decisionCode) {
		this.decisionCode = decisionCode;
	}

	/**
	 * @param decisionDesc the decisionDesc to set
	 */
	public void setDecisionDesc(String decisionDesc) {
		this.decisionDesc = decisionDesc;
	}

	/**
	 * @param decisionMisc the decisionMisc to set
	 */
	public void setDecisionMisc(String decisionMisc) {
		this.decisionMisc = decisionMisc;
	}
	


}
