/**
 * 
 */
package generic.processing.framework.service.component;

import java.util.List;

import generic.processing.framework.data.DataServiceChainDataBlock;

/**
 * Adds a package name to the entity being requested
 * through a GET command or a DELETE command
 * 
 * @author UP779462
 *
 */
public class AddPackageName implements IServiceComponent {
	
	/**
	 * The packageName for adding to the type 
	 */
	private String packageName;

	/**
	 * Default Constructor
	 */
	public AddPackageName() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.service.component.IServiceComponent#process(java.lang.Object, java.util.List)
	 */
	public boolean process(Object obj, List<Object> errorList) throws ServiceComponentException {
		DataServiceChainDataBlock dataBlock = (DataServiceChainDataBlock)obj;
		String entityType = dataBlock.getType();
		dataBlock.setType(this.packageName + "." + entityType); 
		return false;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
