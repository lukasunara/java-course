package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Objects which are instances of classes that implement
 * this interface define what happens if the current document
 * has been changed, a new document was added and a document
 * has been removed.
 * 
 * @author lukasunara
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Defines what happens if the current document has
	 * been changed (user switched to another tab).
	 * 
	 * @param previousModel the previously selected document
	 * @param currentModel the new selected document
	 * @throws NullPointerException when both the currentModel
	 * and the previousModel are <code>null</code>
	 */
	default void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) { }
	
	/**
	 * Defines what happens if a new document was added.
	 * 
	 * @param model {@link SingleDocumentModel} which represents
	 * the added document.
	 */
	default void documentAdded(SingleDocumentModel model) { }
	
	/**
	 * Defines what happens if a a document has been removed.
	 * 
	 * @param model {@link SingleDocumentModel} which represents
	 * the removed document.
	 */
	default void documentRemoved(SingleDocumentModel model) { }

}
