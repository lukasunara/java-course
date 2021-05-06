package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class implements {@link ILocalizationProvider}
 * interface and adds it the ability to register, deregister and
 * inform listeners ({@link #fire()}).
 * 
 * @author lukasunara
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** The listeners which have to be notified when lozalization changes **/
	private List<ILocalizationListener> listeners;
	
	/** Default constructor initializes the listeners list. **/
	public AbstractLocalizationProvider() {
		super();
		this.listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Calls the {@link ILocalizationListener #localizationChanged} method
	 * for each {@link ILocalizationListener} from the listeners list.
	 */
	void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
	
}
