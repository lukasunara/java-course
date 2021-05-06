package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class TextNode is a {@link Node} representing a piece of textual data.
 * 
 * @author lukasunara
 *
 */
public class TextNode extends Node {

	/**  Read-only String property: text **/
	private String text;

	/**
	 * Constructor initializes the text property.
	 * 
	 * @param text String used to initialize the text property
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}
	
	/** Public getter method for String property: text. **/
	public String getText() {
		return text;
	}
	
	/** Return the text property because it is already a string. **/
	@Override
	public String toString() {
		return text;
	}
	
	/** Checks if this TextNode is the same as the given TextNode. **/
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof TextNode) {
			TextNode node = (TextNode)obj;
			if(!node.getText().equals(this.getText())) return false;
		}
		return true;
	}
	
}
