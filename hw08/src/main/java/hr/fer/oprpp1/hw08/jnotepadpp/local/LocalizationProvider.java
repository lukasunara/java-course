package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is a singleton class which is used for
 * setting the current language for localization. 
 * 
 * @author lukasunara
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** The singleton instance of {@link LocalizationProvider} **/
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/** Current language for localization **/
	private String language;
	
	/** The {@link ResourceBundle} which contains translations **/
	private ResourceBundle bundle;
	
	/**
	 * Default constructor sets the language to “en” by default.
	 * It also loads the {@link ResourceBundle} for this language
	 * and stores reference to it.
	 */
	public LocalizationProvider() {
		super();
		setLanguage("english");
	}
	
	/** Public getter for {@link #instance}. **/
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Setter for {@link #language} field. Loads the
	 * {@link ResourceBundle} for the given language.
	 * 
	 * @param language String which represents the new language
	 * used for localization
	 */
	public void setLanguage(String language) {
		this.language = language;
		
		setBundle(language);
		this.fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	@Override
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Loads the {@link ResourceBundle} for the given language.
	 * 
	 * @param language String which represents the new language
	 * used for localization
	 */
	private void setBundle(String language) {
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}

}
