package hr.fer.oprpp1.hw08.jnotepadpp.local.swing;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * This class extends the {@link AbstractAction} class and 
 * the action itself will notify all interested listeners about
 * the localization change.
 * 
 * @author lukasunara
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/** The {@link ILocalizationProvider} used for localization **/
	private ILocalizationProvider prov;
	
	/** The {@link ILocalizationListener} specifies what happens on localization change **/
	private ILocalizationListener listener;
	
	/**
	 * Initializes {@link #prov} and {@link #listener}. Then asks
	 * the given {@link LocalizationProvider} for translation of
	 * the given key and then calls on {@link Action} object
	 * {@link #putValue(String, Object)}.
	 * 
	 * @param key String used for localization
	 * @param lp a reference to {@link ILocalizationProvider}
	 */
	public LocalizableAction(String key, String descriptionKey, ILocalizationProvider lp) {
		super();
		this.prov = lp;
		
		putValue(NAME, prov.getString(key));
		putValue(SHORT_DESCRIPTION, prov.getString(descriptionKey));
		
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, prov.getString(key));
				putValue(SHORT_DESCRIPTION, prov.getString(descriptionKey));
			}
		};
		
		prov.addLocalizationListener(listener);
	}

}
