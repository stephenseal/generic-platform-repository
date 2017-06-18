/**
 * 
 */
package generic.processing.framework.data;

/**
 * This represents the data object that is used when calling 
 * a DataServiceChain for Add/Get/Delete/Update etc.
 * <br>
 * It contains;
 * <ul>
 * 		<li>input field : for any key item needed to be retrieved or deleted etc or object for add/update etc</li>
 * 		<li>output field : for objects retrieved from any DataServiceChain call</li>
 * </ul>
 *  a field for any specific ID (in the event of a Get/Delete etc.)
 * 
 * @author UP779462
 *
 */
public class DataServiceChainDataBlock {
	
	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private String query;
	
	/**
	 * Input object for use in any DataServiceChain
	 */
	private Object input;
	
	/**
	 * Output object for use in any DataServiceChain
	 */
	private Object output;

	/**
	 * Default constructor (does nothing) 
	 */
	public DataServiceChainDataBlock() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor for setting the input object for use with the DataServiceChain
	 * 
	 * @param itput
	 */
	public DataServiceChainDataBlock(String type, Object input) {
		this.type = type;
		this.input = input;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the input
	 */
	public Object getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(Object input) {
		this.input = input;
	}

	/**
	 * @return the output
	 */
	public Object getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(Object output) {
		this.output = output;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataServiceChainDataBlock [type=" + type + ", query=" + query + ", input=" + input + ", output="
				+ output + "]";
	}
	
}
