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
 * which is used for changing selected text to uppercase
 * , lowercase or both.
 * 
 * @author lukasunara
 *
 */
public class CaseTextAction extends JNotepadPPAction {

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
	public CaseTextAction(JNotepadPP notepad, String key, KeyStroke keyStroke, int keyEvent) {
		super(key, keyStroke, keyEvent, notepad.getFlp());
		this.notepad = notepad;
		this.key = key;
		
		this.setEnabled(false);

		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(currentModel == null) {
					CaseTextAction.this.setEnabled(false);
					return;
				}
				
				JTextArea editor = currentModel.getTextComponent();
				editor.addCaretListener(l -> {
					int length = ActionUtils.getLength(editor.getCaret());
					CaseTextAction.this.setEnabled(length != 0);
				});
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel model = notepad.getMultipleModel().getCurrentDocument();
		JTextArea editor = model.getTextComponent();
		
		int length = ActionUtils.getLength(editor.getCaret());
		if(length == 0) return;
		int offset = ActionUtils.getMinOffset(editor.getCaret());

		try {
			Document doc = editor.getDocument();
			
			String text = doc.getText(offset, length);
			String newText = "";
			switch(key) {
				case "invert_case": newText = invertCase(text); break;
				case "to_uppercase": newText = toUpperCase(text); break;
				case "to_lowercase": newText = toLowerCase(text); break;
			}
			
			doc.remove(offset, length);
			doc.insertString(offset, newText, null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Inverts case of the given text.
	 * 
	 * @param text text for invertion
	 * @return inverted cased text
	 */
	private String invertCase(String text) {
		char[] characters = text.toCharArray();
		
		for(int i = 0; i < characters.length; i++) {
			char c = characters[i];
			if(Character.isLowerCase(c)) {
				characters[i] = Character.toUpperCase(c);
			} else if(Character.isUpperCase(c)) {
				characters[i] = Character.toLowerCase(c);
			}
		}
		
		return new String(characters);
	}
	
	/**
	 * Changes case of the given text to uppercase.
	 * 
	 * @param text text for invertion
	 * @return changed cased text
	 */
	private String toUpperCase(String text) {
		char[] characters = text.toCharArray();
		
		for(int i = 0; i < characters.length; i++) {
			characters[i] = Character.toUpperCase(characters[i]);
		}
		
		return new String(characters);
	}
	
	/**
	 * Changes case of the given text to uppercase.
	 * 
	 * @param text text for invertion
	 * @return changed cased text
	 */
	private String toLowerCase(String text) {
		char[] characters = text.toCharArray();
		
		for(int i = 0; i < characters.length; i++) {
			characters[i] = Character.toLowerCase(characters[i]);
		}
		
		return new String(characters);
	}

}
