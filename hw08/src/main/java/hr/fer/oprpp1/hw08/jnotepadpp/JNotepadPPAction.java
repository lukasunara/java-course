package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.LocalizableAction;

/**
 * This abstract class is an extension of {@link LocalizableAction}
 * which represents a action used by {@link JNotepadPP}.
 * 
 * @author lukasunara
 *
 */
public abstract class JNotepadPPAction extends LocalizableAction {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor puts values for accelerator key
	 * and mnemonic key.
	 * 
	 * @param key String used by localization
	 * @param keyStroke represents the {@link KeyStroke}
	 * which starts this action
	 * @param keyEvent represents the {@link KeyEvent}
	 * which starts this action
	 * @param flp {@link FormLocalizationProvider} used
	 * for localization
	 */
	public JNotepadPPAction(String key, KeyStroke keyStroke, int keyEvent, FormLocalizationProvider flp) {
		super(key, key + "_description", flp);

		putValue(ACCELERATOR_KEY, keyStroke); 
		putValue(MNEMONIC_KEY, keyEvent);
	}

}
