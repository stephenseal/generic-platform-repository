/**
 * 
 */
package generic.processing.framework.data.type;

/**
 * This represents the data extracted from the 
 * getForeignKeys area of the database metadata
 * 
 * @author UP779462
 *
 */
public class Relation {
	
	/**
	 * 
	 */
	private String pkTable;
	
	/**
	 * 
	 */
	private String pkColumn;
	
	/**
	 * 
	 */
	private String fkTable;
	
	/**
	 * 
	 */
	private String fkColumn;	

	/**
	 * 
	 */
	public Relation() {
		// TODO Auto-generated constructor stub
	}
	
	public Relation(Relation relation) {
		super();
		this.pkTable = relation.pkTable;
		this.pkColumn = relation.pkColumn;
		this.fkTable = relation.fkTable;
		this.fkColumn = relation.fkColumn;		
	}
	
	/**
	 * @param pkTable
	 * @param pkColumn
	 * @param fkTable
	 * @param fkColumn
	 */
	public Relation(String pkTable, String pkColumn, String fkTable, String fkColumn) {
		super();
		this.pkTable = pkTable;
		this.pkColumn = pkColumn;
		this.fkTable = fkTable;
		this.fkColumn = fkColumn;
	}

	/**
	 * @return the pkTable
	 */
	public String getPkTable() {
		return pkTable;
	}

	/**
	 * @param pkTable the pkTable to set
	 */
	public void setPkTable(String pkTable) {
		this.pkTable = pkTable;
	}

	/**
	 * @return the pkColumn
	 */
	public String getPkColumn() {
		return pkColumn;
	}

	/**
	 * @param pkColumn the pkColumn to set
	 */
	public void setPkColumn(String pkColumn) {
		this.pkColumn = pkColumn;
	}

	/**
	 * @return the fkTable
	 */
	public String getFkTable() {
		return fkTable;
	}

	/**
	 * @param fkTable the fkTable to set
	 */
	public void setFkTable(String fkTable) {
		this.fkTable = fkTable;
	}

	/**
	 * @return the fkColumn
	 */
	public String getFkColumn() {
		return fkColumn;
	}

	/**
	 * @param fkColumn the fkColumn to set
	 */
	public void setFkColumn(String fkColumn) {
		this.fkColumn = fkColumn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Relation [pkTable=" + pkTable + ", pkColumn=" + pkColumn + ", fkTable=" + fkTable + ", fkColumn="
				+ fkColumn + "]";
	}


}
