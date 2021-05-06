package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

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
 * which is used for sorting selected text lines in
 * alphabetical order ascending or descending.
 * 
 * @author lukasunara
 *
 */
public class SortAction extends JNotepadPPAction {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** {@link JNotepadPP} represents notepad application **/
	private JNotepadPP notepad;
	
	/** Sort ascending when true and descending when false **/
	private boolean ascending;
	
	/**
	 * Constructor initializes {@link Action} for given
	 * notepad depending on given key.
	 * 
	 * @param notepad the {@link #notepad}
	 * @param key String used by localization
	 */
	public SortAction(JNotepadPP notepad, String key, boolean ascending) {
		super(key, KeyStroke.getKeyStroke(ascending ? "alt shift A" : "alt shift D")
				, ascending ? KeyEvent.VK_A : KeyEvent.VK_D, notepad.getFlp()
		);
		this.notepad = notepad;
		this.ascending = ascending;
		
		this.setEnabled(false);
		
		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				SortAction.this.setEnabled(currentModel != null);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel model = notepad.getMultipleModel().getCurrentDocument();
		JTextArea editor = model.getTextComponent();
		
		int start = ActionUtils.getMinOffset(editor.getCaret());
		int end = ActionUtils.getMaxOffset(editor.getCaret());

		try {
			Document doc = editor.getDocument();
			Element root = doc.getDefaultRootElement();
			
			int startLine = root.getElementIndex(start);
			int endLine = root.getElementIndex(end);

			// sort line by line
			while(startLine <= endLine) {
				Element line = root.getElement(startLine);
				
				int startOffset = line.getStartOffset();
				int endOffset = line.getEndOffset();
				int length = endOffset - startOffset - 1; // don't remove new line
				
				String text = doc.getText(startOffset, length);
				doc.remove(startOffset, length);
				doc.insertString(startOffset, sort(text), null);
				
				startLine++;
			}
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Sorts line by line.
	 * 
	 * @param text text which needs to get sorted
	 * @return sorted text
	 */
	private String sort(String text) {
		Locale locale = new Locale(notepad.getFlp().getLanguage());
		Collator collator = Collator.getInstance(locale);
		
		String[] words = text.split("\\s+");
		Arrays.sort(words, ascending ? collator : collator.reversed());
		
		String newText = Arrays.stream(words).collect(Collectors.joining(" "));
		
		return newText;
	}

}
