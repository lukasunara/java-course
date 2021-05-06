package hr.fer.oprpp1.hw08.jnotepadpp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * This class is an instance of {@link JComponent}
 * and represents a clock which shows current
 * date and time.
 * 
 * @author lukasunara
 *
 */
public class ClockComponent extends JLabel implements Runnable {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/** Date format **/
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	/** Date and time for display **/
	private volatile String time;
	
	private Thread t;
	
	/** Default constructor creates a new {@link ClockComponent}. **/
	public ClockComponent() {
		setHorizontalAlignment(SwingConstants.RIGHT);
		
		updateTime();

		t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}
	
	/** Updates {@link #time}. **/
	private void updateTime() {
		time = formatter.format(LocalDateTime.now());
		this.setText(time);
	}

	@Override
	public void run() {
		while(true) {
			try {
				SwingUtilities.invokeLater(() -> {
					updateTime();
				});
				Thread.sleep(1000);
			} catch(Exception exc) {}
		}
	}

}
