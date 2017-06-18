/**
 * 
 */
package generic.processing.framework.serialisation;

/**
 * Exception class for anything occuring within any 
 * Serialisation activity 
 * 
 * @author UP779462
 *
 */
public class SerialisationException extends Exception {

	/**
	 * 
	 */
	public SerialisationException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public SerialisationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public SerialisationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SerialisationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
