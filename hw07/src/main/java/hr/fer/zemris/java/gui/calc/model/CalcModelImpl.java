package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {

	/** Tells the user if he can change the model **/
	private boolean editable;
	
	/** Tells the user if the number is positive or negative **/
	private boolean positive;
	
	/** Used for storing given digits **/
	private String digits;
	
	/** Used for storing numeric value of String digits **/
	private double value;
	
	/** Used for storing the frozen value of input **/
	private String frozenValue;
	
	/** Used for storing active operand **/
	private Double activeOperand;
	
	/** Used for storing pending operation **/
	private DoubleBinaryOperator pendingOperation;
	
	/** Used for storing {@link CalcValueListener}s **/
	private List<CalcValueListener> valueListeners;
	
	/** Default constructor initializes all default values **/
	public CalcModelImpl() {
		super();
		this.value = 0.;
		this.digits = "";
		this.editable = true;
		this.positive = true;
		this.frozenValue = null;
		this.valueListeners = new ArrayList<>();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l == null)
			throw new NullPointerException("CalcValueListener is null!");
		
		valueListeners.add(l);
	}

	/** Notifies all {@link CalcValueListener}s that value has been changed **/
	public void notifyCalcValueListeners() {
		valueListeners.forEach(l -> l.valueChanged(this));
	}
	
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		valueListeners.remove(l);
	}

	@Override
	public double getValue() {
		return positive ? value : -value;
	}

	@Override
	public void setValue(double value) {
		if(value < 0) positive = false;
		
		this.value = Math.abs(value);
		digits = String.valueOf(this.value);
		editable = false;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		digits = "";
		value = 0.;
		positive = true;
		editable = true;
		
		freezeValue(null);
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		setPendingBinaryOperation(null);
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable())
			throw new CalculatorInputException("Calculator is currently not editable!");
		
		positive = !positive;
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable())
			throw new CalculatorInputException("Calculator is currently not editable!");
		if(digits.contains(".") || digits.isEmpty())
			throw new CalculatorInputException("A decimal point already exists!");
		
		digits += ".";
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable())
			throw new CalculatorInputException("The model currently is not editable!");
		
		try {
			value = Double.parseDouble(digits + digit);
			if(value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY)
				throw new CalculatorInputException("Value is too big -> Infinity!");
		} catch(NumberFormatException exc) {
			throw new CalculatorInputException("The value could not be parsed as a double value!");
		}
		
		if(digit == 0) {
			if(!digits.equals("0")) // skip leading zeros
				digits += digit;
		} else {
			if(digits.equals("0")) {
				digits = digits.replaceFirst("0", String.valueOf(digit)); // delete leading zero
			} else {
				digits += digit;
			}
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet())
			throw new IllegalStateException("Active operator has not yet been set!");
		
		return activeOperand.doubleValue();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = Double.valueOf(activeOperand);
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	@Override
	public void freezeValue(String value) {
		frozenValue = value;
	}

	@Override
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}

	@Override
	public String toString() {
		String value = hasFrozenValue() ? frozenValue : (digits.isEmpty() ? "0" : digits);

		return (positive ? "" : "-") + value;
	}
	
}
