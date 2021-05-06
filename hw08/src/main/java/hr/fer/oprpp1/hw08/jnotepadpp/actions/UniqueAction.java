package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for deleting all identical lines as the
 * first line in selected text.
 * 
 * @author lukasunara
 *
 */
public class UniqueAction extends JNotepadPPAction {

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
	public UniqueAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("alt shift Q"), KeyEvent.VK_Q, notepad.getFlp());
		this.notepad = notepad;
		
		this.setEnabled(false);
		
		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(currentModel == null) {
					UniqueAction.this.setEnabled(false);
					return;
				}
				
				JTextArea editor = currentModel.getTextComponent();
				editor.addCaretListener(l -> {
					int length = ActionUtils.getLength(editor.getCaret());
					UniqueAction.this.setEnabled(length != 0);
				});
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel model = notepad.getMultipleModel().getCurrentDocument();
		JTextArea editor = model.getTextComponent();
		
		int start = ActionUtils.getMinOffset(editor.getCaret()); // selection start
		int end = ActionUtils.getMaxOffset(editor.getCaret()); // selection end

		try {
			Document doc = editor.getDocument();
			Element root = doc.getDefaultRootElement();
			
			int startLine = root.getElementIndex(start);
			int endLine = root.getElementIndex(end);
			
			if(startLine == endLine) return; // only one line selected

			String firstLine = "";
			int index = startLine;
			while(index <= endLine) {
				Element line = root.getElement(index);
				
				int startOffset = line.getStartOffset();
				int endOffset = line.getEndOffset();
				int length = endOffset - startOffset - 1; // don't look at the new line
				
				String text = doc.getText(startOffset, length);
				if(index == startLine) {
					firstLine = text;
				} else {
					if(text.equals(firstLine)) {
						doc.remove(startOffset, (index == endLine) ? length : length+1);
						// stay in the same line => endLine is now too big so reduce it
						endLine--;
						continue;
					}
				}
				index++; // go to next line
			}
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}

}
