package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class ElementConstantInteger inherits {@link Element} and represents a type
 * Integer constant.
 * 
 * @author lukasunara
 *
 */
public class ElementConstantInteger implements Element {

	/** Read-only int property: value **/
	private int value;
	
	/**
	 * Constructor initializes the value property.
	 * 
	 * @param value int which represents the value of ElementConstantInteger
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	/** Public getter method for int property: value **/
	public int getValue() {
		return value;
	}
	
	/** Represents this ElementConstantInteger as String text **/
	@Override
	public String asText() {
		return String.valueOf(value)+" ";
	}
	
	/**
	 * Checks if this ElementConstantInteger contains the same Integer value as
	 * the given ElementConstantInteger. 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof ElementConstantInteger) {
			ElementConstantInteger el = (ElementConstantInteger)obj;
			return this.value == el.value;
		}
		return false;
	}
	
}
