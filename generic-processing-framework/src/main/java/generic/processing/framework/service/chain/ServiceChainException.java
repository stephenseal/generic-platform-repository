/**
 * 
 */
package generic.processing.framework.service.chain;

/**
 * Exception class for anything occuring within any 
 * ServiceChain activity 
 * 
 * @author UP779462
 *
 */
public class ServiceChainException extends Exception {

	/**
	 * 
	 */
	public ServiceChainException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ServiceChainException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ServiceChainException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceChainException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
