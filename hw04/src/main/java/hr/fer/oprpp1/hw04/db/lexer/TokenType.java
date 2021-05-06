package hr.fer.oprpp1.hw04.db.lexer;

/**
 * This enumeration defines what can a {@link Token} represent. 
 * 
 * @author lukasunara
 *
 */
public enum TokenType {
	
	/** Represents end of file **/
	EOF,
	
	/** Represents a jmbag **/
	JMBAG,
	
	/** Represents first name of student **/
	FIRST_NAME,
	
	/** Represents last name of student **/
	LAST_NAME,
	
	/** Represents an operator **/
	OPERATOR,
	
	/** Represents AND **/
	AND,
	
	/** Represents a string literal **/
	STRING,
	
	/** Represents keyword "query" **/
	QUERY
}

