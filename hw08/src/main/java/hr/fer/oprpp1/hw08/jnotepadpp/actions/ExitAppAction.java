package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for exiting application.
 * 
 * @author lukasunara
 *
 */
public class ExitAppAction extends JNotepadPPAction {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** {@link JNotepadPP} represents notepad application **/
	private JNotepadPP notepad;
	
	/**
	 * {@link CloseFileAction} sets this field => shows if
	 * notepad should be disposed or not
	 */
	static boolean shouldDispose;
	
	/**
	 * Constructor initializes {@link Action} for given
	 * notepad depending on given key.
	 * 
	 * @param notepad the {@link #notepad}
	 * @param key String used by localization
	 */
	public ExitAppAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("control shift W"), KeyEvent.VK_E, notepad.getFlp());
		this.notepad = notepad;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMultipleDocumentModel model = notepad.getMultipleModel();
		
		int counter = model.getNumberOfDocuments();
		if(counter == 0) notepad.dispose();
		
		shouldDispose = true;
		while(counter-- > 0) {
			notepad.getActions().get("close").actionPerformed(null);
			if(!shouldDispose) return;
		}
		notepad.dispose();
	}

}
