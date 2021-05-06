package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.FormLocalizationProvider;

/**
 * This class is an instance of {@link JNotepadPPAction}
 * which is used for opening a file in a new tab.
 * 
 * @author lukasunara
 *
 */
public class OpenFileAction extends JNotepadPPAction {
	
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
	public OpenFileAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, notepad.getFlp());
		this.notepad = notepad;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		FormLocalizationProvider flp = notepad.getFlp();
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString("open_file"));

		if(fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) return;

		File fileName = fc.getSelectedFile();
		Path openedFilePath = fileName.toPath();
		if(!Files.isReadable(openedFilePath)) {
			String message = String.format(flp.getString("not_exist"), fileName.getAbsolutePath());
			
			ActionUtils.showMessageDialog(notepad, JOptionPane.ERROR_MESSAGE, message, flp.getString("error"));
			return;
		}
		
		notepad.getMultipleModel().loadDocument(openedFilePath);
	}

}
