package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorButton;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;

/**
 * Instances of this class represent a {@link Calculator} button
 * with a digit.
 * 
 * @author lukasunara
 *
 */
public class CalculatorDigitButton extends CalculatorButton {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/** Constructor initializes the button and its {@link ActionListener}. **/
	public CalculatorDigitButton(int digit, CalcModelImpl model) {
		super(String.valueOf(digit));
		setFont(getFont().deriveFont(30f));
		
		addActionListener(e -> {
			try {
				model.freezeValue(null);
				model.insertDigit(digit);
				model.notifyCalcValueListeners();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"" + this.getText() + "\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
	}
	
}
