/**
 * 
 */
package generic.processing.framework.data.type;

import java.util.List;

/**
 * @author UP779462
 *
 */
public class Converter {
	
	/**
	 * 
	 */
	private String tablename;
	
	/**
	 * 
	 */
	private List<Variable>variablelist;

	/**
	 * 
	 */
	public Converter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the tablename
	 */
	public String getTablename() {
		return tablename;
	}

	/**
	 * @param tablename the tablename to set
	 */
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	/**
	 * @return the variablelist
	 */
	public List<Variable> getVariablelist() {
		return variablelist;
	}

	/**
	 * @param variablelist the variablelist to set
	 */
	public void setVariablelist(List<Variable> variablelist) {
		this.variablelist = variablelist;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Converter [tablename=" + tablename + ", variablelist=" + variablelist
				+ "]";
	}

}
