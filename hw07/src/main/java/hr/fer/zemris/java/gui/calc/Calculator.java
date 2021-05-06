package hr.fer.zemris.java.gui.calc;

import java.awt.Component;
import java.awt.Container;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.CalculatorBinaryButton;
import hr.fer.zemris.java.gui.calc.buttons.CalculatorDigitButton;
import hr.fer.zemris.java.gui.calc.buttons.CalculatorInversibleBinaryButton;
import hr.fer.zemris.java.gui.calc.buttons.CalculatorUnaryButton;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * This class is a program which represents a simple calculator.
 * 
 * BE CAREFUL! -> After you press "=" calculators sets itself internaly to "0.0",
 * but shows you the correct result.
 * 
 * @author lukasunara
 *
 */
public class Calculator extends JFrame {
	
	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Used for storing user input **/
	private Stack<Double> stack;
	
	/** Used for calculating and storing listeners **/
	private CalcModelImpl model;
	
	/** Default constructor initializes the calculator. **/
	public Calculator() {
		super();
		
		setTitle("Java calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(200, 200);
		
		initGUI();
	}

	/** Initializes calculator GUI. **/
	private void initGUI() {
		this.stack = new Stack<>();
		model = new CalcModelImpl();
		
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		DisplayScreen screen = new DisplayScreen();
		cp.add(screen, new RCPosition(1, 1));
		model.addCalcValueListener(screen);
		
		int digit = 9;
		for(int i = 2; i <= 4; i++) {
			for(int j = 3; j <= 5; j++) {
				cp.add(new CalculatorDigitButton(digit--, model), new RCPosition(i, j));
			}
		}
		cp.add(new CalculatorDigitButton(0, model), new RCPosition(5, 3));
		
		// binary buttons
		cp.add(new CalculatorBinaryButton("/", UtilOperations.DIVIDE, model), new RCPosition(2, 6));
		cp.add(new CalculatorBinaryButton("*", UtilOperations.MULTIPLY, model), new RCPosition(3, 6));
		cp.add(new CalculatorBinaryButton("-", UtilOperations.SUBSTRACT, model), new RCPosition(4, 6));
		cp.add(new CalculatorBinaryButton("+", UtilOperations.ADD, model), new RCPosition(5, 6));

		// unary buttons
		cp.add(new CalculatorUnaryButton("sin", "arcsin", Math::sin, Math::asin, model), new RCPosition(2, 2));
		cp.add(new CalculatorUnaryButton("cos", "arccos", Math::cos, Math::acos, model), new RCPosition(3, 2));
		cp.add(new CalculatorUnaryButton("tan", "arctan", Math::tan, Math::atan, model), new RCPosition(4, 2));
		cp.add(new CalculatorUnaryButton("ctg", "arcctg", UtilOperations.CTG, UtilOperations.ARCCTG, model), new RCPosition(5, 2));
		cp.add(new CalculatorUnaryButton("log", "10^x", Math::log10, UtilOperations.POWER_10, model), new RCPosition(3, 1));
		cp.add(new CalculatorUnaryButton("ln", "e^x", Math::log, UtilOperations.POWER_E, model), new RCPosition(4, 1));

		// stack buttons
		CalculatorButton pushBtn = new CalculatorButton("push");
		pushBtn.addActionListener(e -> {
			try {
				stack.push(model.getValue());
				model.clear();
				model.notifyCalcValueListeners();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"push\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(pushBtn, new RCPosition(3, 7));
		
		CalculatorButton popBtn = new CalculatorButton("pop");
		popBtn.addActionListener(e -> {
			try {
				model.setValue(stack.pop());
				model.notifyCalcValueListeners();
				model.clear();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"pop\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(popBtn, new RCPosition(4, 7));

		// clear buttons
		CalculatorButton clearBtn = new CalculatorButton("clr");
		clearBtn.addActionListener(e -> {
			try {
				model.clear();
				model.notifyCalcValueListeners();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"clr\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(clearBtn, new RCPosition(1, 7));
		
		CalculatorButton resBtn = new CalculatorButton("res");
		resBtn.addActionListener(e -> {
			try {
				model.clearAll();
				model.notifyCalcValueListeners();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"res\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(resBtn, new RCPosition(2, 7));
		
		// symbol buttons
		CalculatorButton swapSignBtn = new CalculatorButton("+/-");
		swapSignBtn.addActionListener(e -> {
			try {
				model.swapSign();
				model.notifyCalcValueListeners();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"+/-\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(swapSignBtn, new RCPosition(5, 4));
		
		CalculatorButton insertDecBtn = new CalculatorButton(".");
		insertDecBtn.addActionListener(e -> {
			try {
				model.insertDecimalPoint();
				model.notifyCalcValueListeners();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \".\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(insertDecBtn, new RCPosition(5, 5));

		// reciprocal button
		CalculatorButton reciprocalBtn = new CalculatorButton("1/x");
		reciprocalBtn.addActionListener(e -> {
			try {
				if(model.hasFrozenValue())
					throw new CalculatorInputException("Model has a frozen value!");
				
				double result = 1 / model.getValue();
				model.freezeValue(String.valueOf(result));
				
				model.notifyCalcValueListeners();
				model.clear();
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"1/x\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(reciprocalBtn, new RCPosition(2, 1));
		
		// power (or nth-root) button
		cp.add(new CalculatorInversibleBinaryButton("x^n", "x^(1/n)", UtilOperations.POWER, UtilOperations.N_ROOT, model)
				, new RCPosition(5, 1)
		);

		// equals button
		CalculatorButton equalsBtn = new CalculatorButton("=");
		equalsBtn.addActionListener(e -> {
			try {
				if(model.isActiveOperandSet()) {
					DoubleBinaryOperator operation = model.getPendingBinaryOperation();
					double result = operation.applyAsDouble(model.getActiveOperand(), model.getValue());
					model.setValue(result);
					
					model.notifyCalcValueListeners();
					model.clearAll();
				}
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"=\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(equalsBtn, new RCPosition(1, 6));

		// check-box changes binary operations
		JCheckBox inverseBtn = new JCheckBox("Inv");
		inverseBtn.addActionListener(e -> {
			try {
				for(Component comp : cp.getComponents()) {
					if(comp instanceof CalculatorButton) {
						CalculatorButton btn = (CalculatorButton) comp;
						if(btn.hasInverse()) {
							btn.inverse(inverseBtn.isSelected());
						}
					}
				}
			} catch(Exception exc) {
				System.out.println(exc.toString());
				model.freezeValue("Error: \"Inv\" button!");
				model.notifyCalcValueListeners();
				model.clearAll();
			}
		});
		cp.add(inverseBtn, new RCPosition(5, 7));
	}

	/** Starts the calculator program (does not expect any arguments). **/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
//			frame.pack();
			frame.setSize(650, 300);
			frame.setVisible(true);
		});
	}
	
}
