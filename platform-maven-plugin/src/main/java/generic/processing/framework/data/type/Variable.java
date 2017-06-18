/**
 * 
 */
package generic.processing.framework.data.type;

/**
 * @author Steve
 *
 */
public class Variable {

	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private boolean isPrimary;
	
	/**
	 * 
	 */
	private boolean isDateType;
	
	/**
	 * 
	 */
	private boolean isForeign;	
	
	/**
	 * 
	 */
	private Relation relation;

	/**
	 * @param type
	 * @param name
	 * @param isPrimary
	 * @param isDateType
	 * @param isForeign
	 * @param relation
	 */
	public Variable(String type, String name, boolean isPrimary, boolean isDateType, boolean isForeign,
			Relation relation) {
		super();
		this.type = type;
		this.name = name;
		this.isPrimary = isPrimary;
		this.isDateType = isDateType;
		this.isForeign = isForeign;
		this.relation = relation;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the isPrimary
	 */
	public boolean isPrimary() {
		return isPrimary;
	}

	/**
	 * @param isPrimary the isPrimary to set
	 */
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	/**
	 * @return the isDateType
	 */
	public boolean isDateType() {
		return isDateType;
	}

	/**
	 * @param isDateType the isDateType to set
	 */
	public void setDateType(boolean isDateType) {
		this.isDateType = isDateType;
	}

	/**
	 * @return the isForeign
	 */
	public boolean isForeign() {
		return isForeign;
	}

	/**
	 * @param isForeign the isForeign to set
	 */
	public void setForeign(boolean isForeign) {
		this.isForeign = isForeign;
	}

	/**
	 * @return the relation
	 */
	public Relation getRelation() {
		return relation;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Variable [type=" + type + ", name=" + name + ", isPrimary=" + isPrimary + ", isDateType=" + isDateType
				+ ", isForeign=" + isForeign + ", relation=" + relation + "]";
	}

}
