package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class ElementVariable inherits {@link Element} and represents an element which is
 * a variable.
 * 
 * @author lukasunara
 *
 */
public class ElementVariable implements Element {

	/** Read-only String property: name **/
	private String name;
	
	/**
	 * Constructor initializes the name property.
	 * 
	 * @param name String which represents the name of ElementVariable
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	/** Public getter method for String property: name **/
	public String getName() {
		return name;
	}
	
	/** Represents this ElementVariable as String text **/
	@Override
	public String asText() {
		return name+" ";
	}

	/**
	 * Checks if this ElementVariable contains the same String name as
	 * the given ElementVariable. 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof ElementVariable) {
			ElementVariable el = (ElementVariable)obj;
			return this.name.equals(el.name);
		}
		return false;
	}
	
}
