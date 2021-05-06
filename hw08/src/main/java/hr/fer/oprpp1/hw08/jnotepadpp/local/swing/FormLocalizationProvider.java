package hr.fer.oprpp1.hw08.jnotepadpp.local.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProviderBridge;


/**
 * This is a class derived from {@link LocalizationProviderBridge}.
 * Instances of this class register itself as a {@link WindowListener}
 * to its {@link JFrame}; when frame is opened, it calls {@link #connect()}
 * and when frame is closed, it calls {@link #disconnect()}.
 * 
 * @author lukasunara
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor registeres itself as a {@link WindowListener}
	 * to the given {@link JFrame}.
	 * 
	 * @param parent the {@link ILocalizationProvider} which is
	 * decorated by this {@link FormLocalizationProvider}
	 * @param frame a {@link JFrame}
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				disconnect();
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				connect();
			}
		});
	}
	
}
