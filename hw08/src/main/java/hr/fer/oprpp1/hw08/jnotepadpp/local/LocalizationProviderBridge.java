package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Instances of this class represent a decorator for some other
 * {@link ILocalizationProvider}. It must listen for localization
 * changes so that, when it receives the notification, it will
 * notify all listeners that are registered as its listeners.
 * 
 * @author lukasunara
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** The {@link ILocalizationProvider} which is decorated **/
	private ILocalizationProvider parent;
	
	/** The {@link ILocalizationListener} which specifies what happens on localization change **/
	private ILocalizationListener listener;
	
	/** Shows if this bridge is already connected to the {@link #parent} **/
	private boolean connected;
	
	/**
	 * Constructor creates a new instance of {@link LocalizationProviderBridge}
	 * and receives the parent {@link ILocalizationProvider}. Also, it specifies
	 * the {@link #listener}.
	 * 
	 * @param parent the {@link #parent}
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		super();
		this.parent = parent;
		this.connected = false;
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	/** Connects this bridge with the {@link #parent}. **/
	public void connect() {
		if(connected) return;

		parent.addLocalizationListener(listener);
		connected = true;
	}
	
	/** Disconnects this bridge from the {@link #parent}. **/
	public void disconnect() {
		if(!connected) return;
		
		parent.removeLocalizationListener(listener);
		connected = false;
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getLanguage() {
		return parent.getLanguage();
	}
	
}
