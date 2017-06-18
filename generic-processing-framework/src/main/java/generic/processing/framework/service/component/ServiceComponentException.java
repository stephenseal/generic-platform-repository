/**
 * 
 */
package generic.processing.framework.service.component;

/**
 * These represent the constituent parts of a Service.
 * <br>
 * All Services could be composed of ServiceComponents 
 * however, this is NOTenforced through this framework.
 * 
 * @author UP779462
 *
 */
public class ServiceComponentException extends Exception {

	/**
	 * 
	 */
	public ServiceComponentException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ServiceComponentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ServiceComponentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceComponentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
