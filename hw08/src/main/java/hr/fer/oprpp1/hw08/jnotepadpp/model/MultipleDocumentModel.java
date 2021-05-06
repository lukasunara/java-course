package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

/**
 * Objects which are instances of classes that implement
 * this interface represents a model capable of holding
 * zero, one or more documents; and having a concept of
 * current document – the one which is shown to the user
 * and on which user works.
 * 
 * @author lukasunara
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Method creates a new document
	 * 
	 * @return {@link SingleDocumentModel} which represents the
	 * created document
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Getter method for current document.
	 * 
	 * @return {@link SingleDocumentModel} which represents the
	 * current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads a document from the given path.
	 * 
	 * @param path Path of a document which we want to load
	 * @return {@link SingleDocumentModel} which represents the
	 * loaded document
	 * @throws NullPointerException when path is <code>null</code>
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves a document if there were any changes.
	 * 
	 * If the given newPath is <code>null</code>, document is
	 * saved using path associated from document, otherwise,
	 * the given newPath is used and after saving is completed,
	 * document’s path is updated to newPath.
	 * 
	 * @param model {@link SingleDocumentModel} which is used for
	 * saving the document
	 * @param newPath represents the new path of the document
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Removes and closes specified document (does not check
	 * modification status or ask any questions).
	 * 
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Registration method for new listeners.
	 * 
	 * @param l {@link MultipleDocumentListener} to be registered
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Deregistration method for registered listeners.
	 * 
	 * @param l {@link MultipleDocumentListener} to be deregistered
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Counts and returns number of documents in this model.
	 * 
	 * @return int number of documents in this model.
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns a document for the given index.
	 * 
	 * @param index represents the index of the document
	 * @return {@link SingleDocumentModel} which represents a document
	 */
	SingleDocumentModel getDocument(int index);

}
