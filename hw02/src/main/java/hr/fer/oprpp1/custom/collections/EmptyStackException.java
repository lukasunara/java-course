package hr.fer.oprpp1.custom.collections;

/**
 * Class EmptyStackException represents a custom exception which is thrown when someone
 * tries to <code>pop()</code> or <code>peek()</code> an element from empty stack.
 * 
 * @author lukasunara
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor creates an EmptyStackException without a message.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Creates an EmptyStackException with a message.
	 */
	public EmptyStackException(String msg) {
		super(msg);
	}
	
}
