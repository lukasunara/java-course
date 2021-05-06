package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Objects which are instances of classes that implement
 * this interface define what happens if document's
 * modify status has been updated and file path updated.
 * 
 * @author lukasunara
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Defines what happens if the current document has
	 * been changed.
	 * 
	 * @param model {@link SingleDocumentModel} which represents
	 * the the document which modify status has been changed.
	 */
	default void documentModifyStatusUpdated(SingleDocumentModel model) { }
	
	/**
	 * Defines what happens if the current document has
	 * been changed.
	 * 
	 * @param model {@link SingleDocumentModel} which represents
	 * the document which file path has been updated.
	 */
	default void documentFilePathUpdated(SingleDocumentModel model) { }
}
