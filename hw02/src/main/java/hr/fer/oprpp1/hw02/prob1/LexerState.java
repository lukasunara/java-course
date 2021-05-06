package hr.fer.oprpp1.hw02.prob1;

/**
 * This enumeration defines in which way should the {@link Lexer} work.
 * 
 * @author lukasunara
 *
 */
public enum LexerState {

	/** There exist numbers, words and symbols **/
	BASIC,
	
	/** There exist only words **/
	EXTENDED
}
