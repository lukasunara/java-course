package hr.fer.zemris.java.hw06.shell;

/**
 * Class ShellIOException represents a custom exception which is thrown when there is an
 * exception while reading or writing from {@link MyShell}.
 * 
 * @author lukasunara
 *
 */
public class ShellIOException extends RuntimeException {

	/** Used for Serializable objects **/
	private static final long serialVersionUID = 1L;

	/** Default constructor creates an EmptyStackException without a message. **/
	public ShellIOException() {
		super();
	}
	
	/** Creates an EmptyStackException with a message. **/
	public ShellIOException(String message) {
		super(message);
	}
	
}
