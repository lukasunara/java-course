package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Class EchoNode is a {@link Node}  representing a command which generates some
 * textual output dynamically.
 * 
 * @author lukasunara
 *
 */
public class EchoNode extends Node {

	/** Read-only {@link Element} array property: elements **/
	private Element[] elements;

	/**
	 * Constructor initializes the elements property.
	 * 
	 * @param elements Element array of 0 or more elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	/** Public getter method for {@link Element} array property: elements **/
	public Element[] getElements() {
		return elements;
	}
	
	/** Creates a string which contains all of its elements. **/
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");
		for(Element el : elements) {
			sb.append(el.asText());
		}
		sb.append("$}");
		
		return sb.toString();
	}
	
	/** Checks if this EchoNode is the same as the given EchoNode. **/
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof Element[]) {
			Element[] elements = (Element[])obj;
			for(int i = 0; i < elements.length; i++) {
				if(!this.elements[i].equals(elements[i])) return false;
			}
		}
		return true;
	}
	
}
