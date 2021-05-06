package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * This enumeration defines in which way should the {@link SmartScriptLexer} work.
 * 
 * @author lukasunara
 *
 */
public enum SmartScriptLexerState {

	/** There exists only one string **/
	BASIC,
	
	/** Inside of a tag **/
	TAG,
	
	/** String inside of a tag **/
	STRINGTAG,
	
}
