package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class UtilOperations {

	/** Divides two double values **/
	public static final DoubleBinaryOperator DIVIDE = (num1, num2) -> {
		return num1 / num2;
	};
	
	/** Multiplies two double values **/
	public static final DoubleBinaryOperator MULTIPLY = (num1, num2) -> {
		return num1 * num2;
	};
	
	/** Substracts two double values **/
	public static final DoubleBinaryOperator SUBSTRACT = (num1, num2) -> {
		return num1 - num2;
	};
	
	/** Adds two double values **/
	public static final DoubleBinaryOperator ADD = (num1, num2) -> {
		return num1 + num2;
	};
	
	/** Represents 10^x **/
	public static final DoubleUnaryOperator POWER_10 = (x) -> {
		return Math.pow(10, x);
	};

	/** Represents e^x **/
	public static final DoubleUnaryOperator POWER_E = (x) -> {
		return Math.pow(Math.E, x);
	};
	
	/** Represents x^n **/
	public static final DoubleBinaryOperator POWER = (x, n) -> {
		return Math.pow(x, n);
	};
	
	/** Represents x^(1/n) **/
	public static final DoubleBinaryOperator N_ROOT = (x, n) -> {
		return Math.pow(x, 1/n);
	};
	
	/** Calculates the cotanges for the given double value **/
	public static final DoubleUnaryOperator CTG = (num) -> {
		return 1 / Math.tan(num);
	};
	
	/** Calculates the arcus cotanges for the given double value **/
	public static final DoubleUnaryOperator ARCCTG = (num) -> {
		return Math.PI/2 - Math.atan(num);
	};
	
}
