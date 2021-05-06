package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class is an implementation of the {@link SingleDocumentModel}
 * interface. It connects a document with its own {@link JTextArea}
 * editor component.
 * 
 * @author lukasunara
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/** Represents the file path **/
	private Path path;
	
	/** Represents the text editor of document **/
	private JTextArea editor;
	
	/** Shows if the editor has been modified **/
	private boolean modified;
	
	/** List of all registered {@link SingleDocumentListener}s **/
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Constructor receives a file {@link Path} and creates
	 * an instance of {@link JTextArea} and initializes its
	 * text content by the given text.
	 * 
	 * @param path represents the file {@link Path} - {@link #path}
	 * @param text represents text content of {@link #editor}
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		super();
		editor = new JTextArea(text);
		editor.setLineWrap(true);
		
		listeners = new ArrayList<>();
		setFilePath(path);
		setModified(path == null); // if null it is a new document

		editor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path;
		
		if(path != null) fireFilePathUpdated();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		fireModifyStatusUpdated();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/** Notifies all listeners about document modification. **/
	private void fireModifyStatusUpdated() {
		listeners.forEach(l -> {
			l.documentModifyStatusUpdated(this);
		});	
	}
	
	/** Notifies all listeners about file path update. **/
	private void fireFilePathUpdated() {
		listeners.forEach(l -> {
			l.documentFilePathUpdated(this);
		});	
	}
	
}
