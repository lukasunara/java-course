package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for displaying statistical info
 * about currently opened file.
 * 
 * @author lukasunara
 *
 */
public class StatisticsAction extends JNotepadPPAction {

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
	public StatisticsAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("alt F2"), KeyEvent.VK_F2, notepad.getFlp());
		this.notepad = notepad;

		this.setEnabled(false);
		
		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				StatisticsAction.this.setEnabled(currentModel != null);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel model = notepad.getMultipleModel().getCurrentDocument();
		
		if(model == null) return;
		
		JTextArea editor = model.getTextComponent();
		Document doc = editor.getDocument();

		int numOfCharacters = doc.getLength();
		
		int numOfWhitespaces = 0;
		int numOfLines = 1;
		try {
			for(char c : doc.getText(0, numOfCharacters-1).toCharArray()) {
				if(Character.isWhitespace(c)) {
					numOfWhitespaces++;
					if(c == '\n') numOfLines++;
				}
			}
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
		int numOfNonBlankCharacters = numOfCharacters - numOfWhitespaces;
		
		FormLocalizationProvider flp = notepad.getFlp();
		
		String message = String.format(flp.getString("statistical_info_message")
				, numOfCharacters, numOfNonBlankCharacters, numOfLines
		);
		ActionUtils.showMessageDialog(notepad, JOptionPane.WARNING_MESSAGE, message, flp.getString("statistical_info"));
	}

}
