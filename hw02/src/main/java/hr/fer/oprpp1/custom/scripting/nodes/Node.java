package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Abstract class Node is used for representation of structured documents.
 * Base class for all graph nodes.
 * 
 * @author lukasunara
 *
 */
public abstract class Node {

	/** Internally managed {@link ArrayIndexedCollection} of children **/
	private ArrayIndexedCollection children;
	
	/**
	 * Adds given child to an internally managed collection of children.
	 * 
	 * @param child Node which is added to an {@link ArrayIndexedCollection}
	 */
	public void addChildNode(Node child) {
		if(children == null) children = new ArrayIndexedCollection();
		
		children.add(child);
	}
	
	// create an instance of the collection on demand → on first call of addChildNode
	
	/**
	 * Method counts and returns a number of (direct) children.
	 * 
	 * @return int number of (direct) children to this Node
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Returns selected child or throws an appropriate exception if the given
	 * index is invalid.
	 * 
	 * @param index represents a position in collection
	 * @return child which
	 * @throws IndexOutOfBoundsException valid indexes are 0 to size-1
	 */
	public Node getChild(int index) {
		return (Node)children.get(index);
	}
	
}
