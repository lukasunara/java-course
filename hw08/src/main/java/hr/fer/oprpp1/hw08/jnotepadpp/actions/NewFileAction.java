package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for creating a new a file.
 * 
 * @author lukasunara
 *
 */
public class NewFileAction extends JNotepadPPAction {
	
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
	public NewFileAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("alt shift N"), KeyEvent.VK_N, notepad.getFlp());
		this.notepad = notepad;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		notepad.getMultipleModel().createNewDocument();
	}

}
