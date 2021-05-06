package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class ElementString inherits {@link Element} and represents an element which is
 * a String.
 * 
 * @author lukasunara
 *
 */
public class ElementString implements Element {

	/** Read-only String property: value **/
	private String value;
	
	/**
	 * Constructor initializes the value property.
	 * 
	 * @param value String which represents the name of ElementString
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	/** Public getter method for String property: value **/
	public String getName() {
		return value;
	}
	
	/** Represents this ElementString as String text **/
	@Override
	public String asText() {
		return "\""+value+"\" ";
	}
	
	/**
	 * Checks if this ElementString contains the same String name as
	 * the given ElementString. 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof ElementString) {
			ElementString el = (ElementString)obj;
			return this.value.equals(el.value);
		}
		return false;
	}
	
}
