package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * This class is actually a command-line application which accepts a single
 * command-line argument and evaluates that expression. When starting program from
 * console, whole expression needs to be enclosed into quotation marks, so that this
 * program always gets just one argument.
 * 
 * @author lukasunara
 *
 */
public class StackDemo {

	/**
	 * When starting program from console, you will enclose whole expression into quotation
	 * marks, so that your program always gets just one argument. The calculation process is
	 * solved by using {@link ObjectStack}.
	 * 
	 * @param args only one String expression (specifies int numbers and operators)
	 * @throws IllegalArgumentException Terminates the evaluation if user tries to divide by zero or
	 * if the expression is invalid
	 */
	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack();	// stack = empty
		
		for(String str : args[0].trim().split("\\s+")) {	// for each element of expression
			// if element is number, push it on stack and continue
			try {
				int number = Integer.parseInt(str);
				stack.push(number);
			} catch(Exception e) {
				// else pop two elements from stack, perform operation and push result back on stack
				stack.push(performOperation((Integer)stack.pop(), (Integer)stack.pop(), str));
			}
		}
		if(stack.size() != 1)
			throw new IllegalArgumentException("The expression is invalid!");
		
		System.out.println("Expression evaluates to " + stack.pop());
	}
	
	/**
	 * Performes operation between two numbers.
	 * 
	 * @param secondNumber element from stack
	 * @param firstNumber element from stack
	 * @param operator is +,-,*,/ or %
	 * @return solution of the operation
	 */
	private static int performOperation(int secondNumber, int firstNumber, String operator) {
		switch(operator) {
		case "+": return firstNumber + secondNumber;
		case "-": return firstNumber - secondNumber;
		case "*": return firstNumber * secondNumber;
		case "/": {
			if(secondNumber == 0)
				throw new IllegalArgumentException("Divison by zero!");
			return firstNumber / secondNumber;
		}
		case "%": return firstNumber % secondNumber;
		default: throw new IllegalArgumentException("Operators are only: \"+,-,*,/ and %\"");
		}
	}
	
}
