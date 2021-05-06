package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorButton;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Instances of this class represent a {@link Calculator} button
 * with two possible {@link DoubleUnaryOperator}.
 * 
 * @author lukasunara
 *
 */
public class CalculatorUnaryButton extends CalculatorButton {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Constructor initializes the button and its {@link ActionListener}. **/
	public CalculatorUnaryButton(String text1, String text2, DoubleUnaryOperator operation1,
			DoubleUnaryOperator operation2, CalcModelImpl model
	) {
		super(text1, text2);
		
		// decide which operation is used
		addActionListener(e -> {
			try {
				if(model.hasFrozenValue())
					throw new CalculatorInputException("Model has a frozen value!");
				
				double result;
				if(getText().equals(text1)) {
					result = operation1.applyAsDouble(model.getValue());
				}
				else if(getText().equals(text2)) {
					result = operation2.applyAsDouble(model.getValue());
				} else {
					throw new IllegalStateException("Something is very wrong in unary buttons action listener!");
				}
				model.freezeValue(String.valueOf(result));
				
				model.notifyCalcValueListeners();
				model.clear();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"" + this.getText() + "\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
	}
	
}
