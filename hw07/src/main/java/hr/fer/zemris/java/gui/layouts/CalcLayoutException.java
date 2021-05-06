package hr.fer.zemris.java.gui.layouts;

/**
 * This exception is thrown by {@link CalcLayout}.
 * 
 * @author lukasunara
 *
 */
public class CalcLayoutException extends RuntimeException {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/** Default constructor creates a new CalcLayoutException without any message. **/
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Constructor creates a new CalcLayoutException with the given message.
	 * 
	 * @param message String to display when this exception occures
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
}
