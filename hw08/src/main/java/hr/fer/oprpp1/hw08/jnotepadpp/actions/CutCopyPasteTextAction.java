package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for cutting or copying selected text
 * or for pasteing text saved from previous cut/copy
 * actions.
 * 
 * @author lukasunara
 *
 */
public class CutCopyPasteTextAction extends JNotepadPPAction {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** {@link JNotepadPP} represents notepad application **/
	private JNotepadPP notepad;
	
	/** Key is the name of action **/
	private String key;
	
	/**
	 * Constructor initializes {@link Action} for given
	 * notepad depending on given key.
	 * 
	 * @param notepad the {@link #notepad}
	 * @param key String used by localization
	 */
	public CutCopyPasteTextAction(JNotepadPP notepad, String key, KeyStroke keyStroke, int keyEvent) {
		super(key, keyStroke, keyEvent, notepad.getFlp());
		this.notepad = notepad;
		this.key = key;
		
		this.setEnabled(false);
		
		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(currentModel == null) {
					CutCopyPasteTextAction.this.setEnabled(false);
					return;
				}
				
				if(key.equals("paste")) {
					CutCopyPasteTextAction.this.setEnabled(true);
				} else {
					JTextArea editor = currentModel.getTextComponent();
					editor.addCaretListener(l -> {
						int length = ActionUtils.getLength(editor.getCaret());
						CutCopyPasteTextAction.this.setEnabled(length != 0);
					});
				}
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel model = notepad.getMultipleModel().getCurrentDocument();
		JTextArea editor = model.getTextComponent();
		
		int length = ActionUtils.getLength(editor.getCaret());
		if(length == 0 && !key.equals("paste")) return;
		int offset = ActionUtils.getMinOffset(editor.getCaret());
		
		try {
			Document doc = editor.getDocument();
			
			if(!key.equals("paste")) notepad.setSavedTextFromCutOrCopy(doc.getText(offset, length));
			
			if(!key.equals("copy")) doc.remove(offset, length);
			if(key.equals("paste")) doc.insertString(offset, notepad.getSavedTextFromCutOrCopy(), null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

}
