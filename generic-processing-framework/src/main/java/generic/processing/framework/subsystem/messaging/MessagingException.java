/**
 * 
 */
package generic.processing.framework.subsystem.messaging;

/**
 * This is the exception to use while activity is occuring within the 
 * messaging subsystem boundaries
 * 
 * @author UP779462
 *
 */
public class MessagingException extends Exception {

	/**
	 * 
	 */
	public MessagingException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public MessagingException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public MessagingException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MessagingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
