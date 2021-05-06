package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Objects which are instances of classes that implement
 * this interface represent a model of single document,
 * having information about a file path from which document
 * was loaded (can be <code>null</code> for new document),
 * document modification status and reference to Swing component
 * which is used for editing (each document has its own editor
 * component).
 * 
 * @author lukasunara
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Getter method for the text component of this model.
	 * 
	 * @return {@link JTextArea} which is the text component
	 */
	JTextArea getTextComponent();
	
	/**
	 * Getter method for the path of file stored in this model.
	 * 
	 * @return {@link Path} for the file
	 */
	Path getFilePath();
	
	/**
	 * Setter method for the path of file stored in this model.
	 * 
	 * @param path {@link Path} for the file
	 * @throws NullPointerException when path is <code>null</code>
	 */
	void setFilePath(Path path);

	/**
	 * Shows if this model has been modified.
	 * 
	 * @return boolean value - true if it has been modified,
	 * otherwise, false
	 */
	boolean isModified();
	
	/**
	 * Setter method for modified field.
	 * 
	 * @param modified boolean value which represents if the model
	 * has been modified
	 */
	void setModified(boolean modified);
	
	/**
	 * Registration method for new listeners.
	 * 
	 * @param l {@link SingleDocumentListener} to be registered
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Deregistration method for registered listeners.
	 * 
	 * @param l {@link SingleDocumentListener} to be deregistered
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
	
}
