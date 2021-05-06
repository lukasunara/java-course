package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.Container;

import javax.swing.JOptionPane;
import javax.swing.text.Caret;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPPAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.FormLocalizationProvider;

/**
 * This class represents a public utilities
 * class and contains helpfull methods for
 * {@link JNotepadPP} and {@link JNotepadPPAction}s.
 * 
 * @author lukasunara
 *
 */
public class ActionUtils {

	/**
	 * Creates a {@link String} array with
	 * localized options for {@link JOptionPane}.
	 * 
	 * @param flp {@link FormLocalizationProvider} used for localization
	 * @return the created {@link String} array
	 */
	public static String[] optionsYesNoCancel(FormLocalizationProvider flp) {
		return new String[] {
				flp.getString("yes"),
				flp.getString("no"),
				flp.getString("cancel")
		};
	}
	
	/**
	 * Calls {@link JOptionPane#showOptionDialog()}.
	 * 
	 * @param frame {@link Container} in which it is displayed
	 * @param options {@link String} array with options
	 * @param message the message displayed to the user
	 * @param title title of dialog
	 * @return int result of user response
	 */
	public static int showOptionDialog(Container frame, String[] options, String message, String title) {
		return JOptionPane.showOptionDialog(
				frame,
				message,
				title,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);
	}
	
	/**
	 * Calls {@link JOptionPane#showMessageDialog()}.
	 * 
	 * @param frame {@link Container} in which it is displayed
	 * @param messageType type of message
	 * @param message the message displayed to the user
	 * @param title title of dialog
	 */
	public static void showMessageDialog(Container frame, int messageType, String message, String title) {
		JOptionPane.showMessageDialog(
				frame,
				message,
				title,
				messageType
		);	
	}
	
	/**
	 * Calculates the selected text length.
	 * 
	 * @param caret {@link Caret} in document
	 * @return calculated length
	 */
	public static int getLength(Caret caret) {
		return Math.abs(caret.getDot() - caret.getMark());
	}
	
	/**
	 * Calculates the starting offset of
	 * selected text.
	 * 
	 * @param caret {@link Caret} in document
	 * @return calculated offset
	 */
	public static int getMinOffset(Caret caret) {
		return Math.min(caret.getDot(), caret.getMark());
	}
	
	/**
	 * Calculates the ending offset of
	 * selected text.
	 * 
	 * @param caret {@link Caret} in document
	 * @return calculated offset
	 */
	public static int getMaxOffset(Caret caret) {
		return Math.max(caret.getDot(), caret.getMark());
	}
	
}
