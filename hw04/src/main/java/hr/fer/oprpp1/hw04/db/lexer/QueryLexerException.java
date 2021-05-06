package hr.fer.oprpp1.hw04.db.lexer;

/**
 * QueryLexerException is thrown when a {@link QueryLexer} recieves a badly formatted String.
 * 
 * @author lukasunara
 *
 */
public class QueryLexerException extends RuntimeException {

	/** Used for Serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Default constructor creates a new QueryLexerException without an error message. **/
	public QueryLexerException() {
		super();
	}
	
	/**
	 * Constructor creates a new QueryLexerException with an error message.
	 * 
	 * @param msg String whch represents the message to be displayed
	 */
	public QueryLexerException(String msg) {
		super(msg);
	}

}
