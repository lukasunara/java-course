package hr.fer.oprpp1.custom.scripting.nodes;


/**
 * Class DocumentNode is a {@link Node} representing an entire document.
 * 
 * @author lukasunara
 *
 */
public class DocumentNode extends Node {

	/** Default constructor creates a new Node reference **/
	public DocumentNode() {
		super();
	}
	
	/** Creates a string which should give the same parse result as user input text **/
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		return sb.toString();
	}
	
	/** Checks if this DocumentNode is the same as the given DocumentNode. **/
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof DocumentNode) {
			DocumentNode document = (DocumentNode)obj;
			for(int i = 0; i < numberOfChildren(); i++) {
				if(!document.getChild(i).equals(this.getChild(i))) return false;
			}
		}
		return true;
	}
	
}
