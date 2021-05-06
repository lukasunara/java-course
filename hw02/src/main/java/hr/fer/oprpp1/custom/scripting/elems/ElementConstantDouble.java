package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class ElementConstantDouble inherits {@link Element} and represents a type
 * Double constant.
 * 
 * @author lukasunara
 *
 */
public class ElementConstantDouble implements Element {

	/** Read-only double property: value **/
	private double value;
	
	/**
	 * Constructor initializes the value property.
	 * 
	 * @param value double which represents the value of ElementConstantDouble
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}
	
	/** Public getter method for double property: value **/
	public double getValue() {
		return value;
	}
	
	/** Represents this ElementConstantDouble as String text **/
	@Override
	public String asText() {
		return String.valueOf(value)+" ";
	}
	
	/**
	 * Checks if this ElementConstantDouble contains the same double value as
	 * the given ElementConstantDouble. 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof ElementConstantDouble) {
			ElementConstantDouble el = (ElementConstantDouble)obj;
			return Math.abs(this.value - el.value) <= 1E-6;
		}
		return false;
	}
	
}
