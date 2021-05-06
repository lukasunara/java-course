package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JFileChooser;
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
 * which is used for saving a file.
 * 
 * @author lukasunara
 *
 */
public class SaveFileAsAction extends JNotepadPPAction {
	
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
	public SaveFileAsAction(JNotepadPP notepad, String key) {
		super(key, KeyStroke.getKeyStroke("control shift S"), KeyEvent.VK_F3, notepad.getFlp());
		this.notepad = notepad;
		
		this.setEnabled(false);

		notepad.getMultipleModel().addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				SaveFileAsAction.this.setEnabled(currentModel != null);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMultipleDocumentModel model = notepad.getMultipleModel();
		SingleDocumentModel document = model.getCurrentDocument();
		FormLocalizationProvider flp = notepad.getFlp();
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString("save_file_as"));
		
		do {
			if(fc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						notepad,
						flp.getString("nothing_saved"),
						flp.getString("warning"),
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}
			
			File selectedFile = fc.getSelectedFile();
			if(selectedFile.exists()) {
				String[] options = new String[] {
						flp.getString("yes"),
						flp.getString("no")
				};
				int result = JOptionPane.showOptionDialog(
						notepad,
						selectedFile.getName() + " " + flp.getString("already_exists"),
						flp.getString("confirm_save_as"),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null,
						options,
						options[0]
				);
				switch(result) {
					case JOptionPane.CLOSED_OPTION: continue;
					case 0: {
						model.saveDocument(document, selectedFile.toPath());
						return;
					}
					case 1: continue;
				}
			} else {
				model.saveDocument(document, selectedFile.toPath());
				break;
			}
		} while(true);
	}
	
}