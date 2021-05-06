package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * This class demonstrates how does the {@link CalcLayout} work.
 * 
 * @author lukasunara
 *
 */
public class CalcLayoutDemo extends JFrame {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	public CalcLayoutDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		JPanel p = new JPanel(new CalcLayout(3));
		
		p.add(new JLabel("x"), new RCPosition(1,1));
		p.add(new JLabel("y"), new RCPosition(2,3));
		p.add(new JLabel("z"), new RCPosition(2,7));
		p.add(new JLabel("w"), new RCPosition(4,2));
		p.add(new JLabel("a"), new RCPosition(4,5));
		p.add(new JLabel("b"), new RCPosition(4,7));
		
		// this should give the same result:
//		p.add(new JLabel("x"), "1,1");
//		p.add(new JLabel("y"), "2,3");
//		p.add(new JLabel("z"), "2,7");
//		p.add(new JLabel("w"), "4,2");
//		p.add(new JLabel("a"), "4,5");
//		p.add(new JLabel("b"), "4,7");
		
		cp.add(p);
	}
	
	/** main method doesn't expect any arguments. **/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new CalcLayoutDemo().setVisible(true);
		});
	}
	
}
