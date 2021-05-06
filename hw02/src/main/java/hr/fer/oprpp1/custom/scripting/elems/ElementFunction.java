package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class ElementFunction inherits {@link Element} and represents an element which is
 * a function.
 * 
 * @author lukasunara
 *
 */
public class ElementFunction implements Element {

	/** Read-only String property: name **/
	private String name;
	
	/**
	 * Constructor initializes the name property.
	 * 
	 * @param name String which represents the name of ElementFunction
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}
	
	/** Public getter method for String property: name **/
	public String getName() {
		return name;
	}
	
	/** Represents this ElementFunction as String text **/
	@Override
	public String asText() {
		return "@"+name+" ";
	}
	
	/**
	 * Checks if this ElementFunction contains the same String name as
	 * the given ElementFunction. 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof ElementFunction) {
			ElementFunction el = (ElementFunction)obj;
			return this.name.equals(el.name);
		}
		return false;
	}
	
}
