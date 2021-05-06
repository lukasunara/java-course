package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Objects which are instances of classes that implement
 * this interface can specify what is going to happen when
 * localization changes.
 * 
 * @author lukasunara
 *
 *
 */
@FunctionalInterface
public interface ILocalizationListener {

	/** Specifies what is going to happen when localization changes. **/
	void localizationChanged();
	
}
