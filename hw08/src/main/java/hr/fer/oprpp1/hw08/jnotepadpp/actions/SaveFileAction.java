package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for saving a file as...
 * 
 * @author lukasunara
 *
 */
public class SaveFileAction extends JNotepadPPAction {
	
	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** {@link JNotepadPP} represents notepad application **/
	private JNotepadPP notepad;
	
	/**
	 * Constructor initializes {@link Action} for given
	 * notepad depending on given key.
	 * 
	 * @param notepad the {@link #notepad}
	 * @param key String used by localization
	 */
	public SaveFileAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, notepad.getFlp());
		this.notepad = notepad;
		
		SingleDocumentListener listener = new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				SaveFileAction.this.setEnabled(model.isModified());
			}
		};
		
		this.setEnabled(false);

		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				model.removeSingleDocumentListener(listener);
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				model.addSingleDocumentListener(listener);
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(currentModel == null)
					SaveFileAction.this.setEnabled(false);
				else
					SaveFileAction.this.setEnabled(currentModel.isModified());
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMultipleDocumentModel model = notepad.getMultipleModel();
		SingleDocumentModel document = model.getCurrentDocument();
		
		if(document == null || document.getFilePath() == null) {
			notepad.getActions().get("save_as").actionPerformed(null);
		} else {
			model.saveDocument(document, null);			
		}
	}
	
}