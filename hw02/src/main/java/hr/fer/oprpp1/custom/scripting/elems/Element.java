package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class Element is used for the representation of expressions.
 * 
 * @author lukasunara
 *
 */
public interface Element {

	/** Represents this Element as String text **/
	default String asText() {
		return "";
	}
	
}
