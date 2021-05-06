package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Objects which are instances of classes that implement
 * this interface make it possible to return a translation
 * of the given word for a specified language.
 * It notifies all listeners which depend on that word.
 * 
 * @author lukasunara
 *
 */
public interface ILocalizationProvider {

	/**
	 * Registers a new {@link ILocalizationListener} to this provider.
	 * 
	 * @param l the {@link ILocalizationListener} for registration
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Deregisters a new {@link ILocalizationListener} from this provider.
	 * 
	 * @param l the {@link ILocalizationListener} for deregistration
	 */
	void removeLocalizationListener(ILocalizationListener l);
 
	/**
	 * Method takes a key and gives back the localization (translation
	 * of the key for the current language).
	 * 
	 * @param key word which we want to localize
	 * @return String which represents the localized word
	 */
	String getString(String key);
	
	/**
	 * Getter for current language used by localization.
	 * 
	 * @param language String which represents the language
	 * used for localization
	 */
	String getLanguage();
}
