package hr.fer.oprpp1.hw04.db;

/**
 * QueryParserException is thrown when a {@link QueryParser} recieves a badly formatted
 * String or if any other exception occurs.
 * 
 * @author lukasunara
 *
 */
public class QueryParserException extends RuntimeException {

	/** Used for Serializable objects */
	private static final long serialVersionUID = 1L;
	
	/** Default constructor creates a new QueryParserException without an error message. **/
	public QueryParserException() {
		super();
	}
	
	/**
	 * Constructor creates a new QueryParserException with an error message.
	 * 
	 * @param msg String whch represents the message to be displayed
	 */
	public QueryParserException(String msg) {
		super(msg);
	}
	
}
