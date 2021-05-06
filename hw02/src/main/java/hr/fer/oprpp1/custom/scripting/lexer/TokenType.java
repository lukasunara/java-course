package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * This enumeration defines what can a {@link Token} represent. 
 * 
 * @author lukasunara
 *
 */
public enum TokenType {
	
	/** Represents end of file **/
	EOF,
	
	/** Represents a varaiable **/
	VARIABLE,
	
	/** Represents a string outside or inside of tag */
	STRING,
	
	/** Represents an Integer **/
	INTEGER,

	/** Represents an Double **/
	DOUBLE,
	
	/** Represents a single symbol **/
	SYMBOL,
	
	/** Represents a function **/
	FUNCTION,
	
	/** Represents the start of tag "{$" */
	START,
	
	/** Represents the end of tag "$}" */
	END,
	
}
