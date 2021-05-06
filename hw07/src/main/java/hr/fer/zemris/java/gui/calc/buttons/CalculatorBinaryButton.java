package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Instances of this class represent a {@link Calculator} button
 * with a {@link DoubleBinaryOperator}.
 * 
 * @author lukasunara
 *
 */
public class CalculatorBinaryButton extends JButton {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Constructor initializes the button and its {@link ActionListener}. **/
	public CalculatorBinaryButton(String text, DoubleBinaryOperator operation, CalcModelImpl model) {
		super(text);
	
		addActionListener(e -> {
			try {
				if(model.hasFrozenValue())
					throw new CalculatorInputException("Model has a frozen value!");
				
				double result;
				if(model.isActiveOperandSet()) {
					DoubleBinaryOperator currOperation = model.getPendingBinaryOperation();
					result = currOperation.applyAsDouble(model.getActiveOperand(), model.getValue());
				} else {
					result = model.getValue();
				}
				model.clear();
				model.setPendingBinaryOperation(operation);
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
