package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for closing a file.
 * 
 * @author lukasunara
 *
 */
public class CloseFileAction extends JNotepadPPAction {
	
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
	public CloseFileAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("control W"), KeyEvent.VK_W, notepad.getFlp());
		this.notepad = notepad;
		
		this.setEnabled(false);
		
		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				CloseFileAction.this.setEnabled(currentModel != null);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMultipleDocumentModel model = notepad.getMultipleModel();
		SingleDocumentModel document = model.getCurrentDocument();
		
		if(!document.isModified()) {
			model.closeDocument(document);			
		} else {
			FormLocalizationProvider flp = notepad.getFlp();
			
			String[] options = ActionUtils.optionsYesNoCancel(flp);
			String message = flp.getString("save_changes") + model.getTitleAt(model.getSelectedIndex()) + "?";
			
			int result = ActionUtils.showOptionDialog(notepad, options, message, flp.getString("save"));

			switch(result) {
				case JOptionPane.CLOSED_OPTION: ExitAppAction.shouldDispose = false; break;
				case 0: {
					notepad.getActions().get("save").actionPerformed(null);
					model.closeDocument(document);
					break;
				}
				case 1: model.closeDocument(document); break;
				case 2: ExitAppAction.shouldDispose = false; break;
			}
		}
	}

}
