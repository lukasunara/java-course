package hr.fer.oprpp1.hw02.prob1;

/**
 * LexerException is thrown when {@link Lexer} recieves a badly formatted String.
 * 
 * @author lukasunara
 *
 */
public class LexerException extends RuntimeException {

	/** Used for Serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Default constructor creates a new LexerException without an error message. **/
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor creates a new LexerException with an error message.
	 * 
	 * @param msg String whch represents the message to be displayed
	 */
	public LexerException(String msg) {
		super(msg);
	}

}
