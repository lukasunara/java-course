package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Instances of this class extend the {@link JLabel} and represent a read-only
 * screen (used by etc. {@link Calculator}). 
 * 
 * @author lukasunara
 *
 */
public class DisplayScreen extends JLabel implements CalcValueListener {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/** Default constructor **/
	public DisplayScreen() {
		super("0");
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Color.YELLOW);
		setOpaque(true);
		setFont(getFont().deriveFont(30f));
		setHorizontalAlignment(SwingConstants.RIGHT);
		setEnabled(false);
	}

	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
	}
	
}
