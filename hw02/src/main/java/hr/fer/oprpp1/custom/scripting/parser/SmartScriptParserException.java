package hr.fer.oprpp1.custom.scripting.parser;

/**
 * LexerException is thrown when a {@link SmartScriptParser} recieves a badly formatted String
 * or if any other exception occurs.
 * 
 * @author lukasunara
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/** Used for Serializable objects */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor creates a new SmartScriptParserException without an error message.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor creates a new SmartScriptParserException with an error message.
	 * 
	 * @param msg String whch represents the message to be displayed
	 */
	public SmartScriptParserException(String msg) {
		super(msg);
	}
	
}
