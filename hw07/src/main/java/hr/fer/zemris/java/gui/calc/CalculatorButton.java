package hr.fer.zemris.java.gui.calc;

import java.awt.Button;

import javax.swing.JButton;

public class CalculatorButton extends JButton {
	
	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Shows if this button can have a inverse function **/
	private boolean hasInverse;
	
	/** Normal text **/
	protected String text1;
	
	/** Text when inverse is on **/
	protected String text2;
	
	/**
	 * Constructor when {@link Button} doesn't have a inverse function.
	 *
	 * @param text text to display on button
	 */
	public CalculatorButton(String text) {
		super(text);
		this.hasInverse = false;
	}
	
	/**
	 * Constructor when {@link Button} has a inverse function.
	 *
	 * @param text1 text to display on button when not inverse
	 * @param text2 text to display on button when inverse
	 */
	public CalculatorButton(String text1, String text2) {
		super(text1);
		this.text1 = text1;
		this.text2 = text2;
		this.hasInverse = true;
	}

	/** Getter method for hasInverse value. **/
	public boolean hasInverse() {
		return hasInverse;
	}

	/** Inverses the button by changing it's text. **/
	public void inverse(boolean inverse) {
		this.setText(inverse ? text2 : text1);
	}
	
}
