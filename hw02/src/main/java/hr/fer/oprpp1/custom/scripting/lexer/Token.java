package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Instances of this class represent a token which can be one of the types in
 * enum {@link TokenType}. Also, contain the value of the Token.
 * 
 * @author lukasunara
 *
 */
public class Token {

	/** Type of the Object the token contains **/
	private TokenType type;
	
	/** Value of the Token **/
	private Object value;
	
	/**
	 * Constructor creates a reference on a new Token which has a specific type
	 * and value.
	 * 
	 * @param type TokenType of the new Token
	 * @param value Object value of the new Token
	 */
	public Token(TokenType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Public getter for value which this Token contains.
	 * 
	 * @return Object value from this Token
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Public getter for the type of this Token.
	 * 
	 * @return TokenType of this Token
	 */
	public TokenType getType() { 
		return type;
	}
	
}
