package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class ElementOperator inherits {@link Element} and represents an element which is
 * an operator.
 * 
 * @author lukasunara
 *
 */
public class ElementOperator implements Element {

	/** Read-only String property: symbol **/
	private String symbol;
	
	/**
	 * Constructor initializes the symbol property.
	 * 
	 * @param symbol String which represents the symbol of ElementOperator
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	/** Public getter method for String property: symbol **/
	public String getName() {
		return symbol;
	}
	
	/** Represents this ElementOperator as String text **/
	@Override
	public String asText() {
		return symbol+" ";
	}
	
	/**
	 * Checks if this ElementOperator contains the same String symbol as
	 * the given ElementOperator. 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof ElementOperator) {
			ElementOperator el = (ElementOperator)obj;
			return this.symbol.equals(el.symbol);
		}
		return false;
	}
}
