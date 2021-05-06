package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorButton;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Instances of this class represent a {@link Calculator} button
 * with two possible {@link DoubleBinaryOperator}.
 * 
 * @author lukasunara
 *
 */
public class CalculatorInversibleBinaryButton extends CalculatorButton {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Constructor initializes the button and its {@link ActionListener}. **/
	public CalculatorInversibleBinaryButton(String text1, String text2, DoubleBinaryOperator operation1,
			DoubleBinaryOperator operation2, CalcModelImpl model
	) {
		super(text1, text2);
		
		// decide which operation is used
		addActionListener(e -> {
			try {
				if(model.hasFrozenValue())
					throw new CalculatorInputException("Model has a frozen value!");
				
				DoubleBinaryOperator currOperation;
				if(getText().equals(text1)) {
					currOperation = operation1;
				}
				else if(getText().equals(text2)) {
					currOperation = operation2;
				} else {
					throw new IllegalStateException("Something is very wrong in unary buttons action listener!");
				}
				
				double result;
				if(model.isActiveOperandSet()) {
					DoubleBinaryOperator pendingOperation = model.getPendingBinaryOperation();
					result = pendingOperation.applyAsDouble(model.getActiveOperand(), model.getValue());
				} else {
					result = model.getValue();
				}
				model.clear();
				model.setPendingBinaryOperation(currOperation);
				model.setActiveOperand(result);
				
				model.freezeValue(String.valueOf(result));
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
