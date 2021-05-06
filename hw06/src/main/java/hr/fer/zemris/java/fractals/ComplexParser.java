package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;

/**
 * This class is used for parsing complex numbers.
 * 
 * @author lukasunara
 *
 */
public class ComplexParser {

	/** Represents input data **/
	private char[] data;
	
	/** Represents current index in data array **/
	private int currentIndex;
	
	/** Shows if the imaginary part had already been read **/
	private boolean readImaginary;
	
	/** Represents the created {@link Complex} number **/
	private Complex complexNumber;
	
	/**
	 * Constructor starts parsing the given String.
	 * 
	 * @param root String for parsing
	 */
	public ComplexParser(String root) {
		super();
		if(root.isEmpty())
			throw new IllegalArgumentException("Empty string is not a legal complex number!");

		this.data = root.toCharArray();
		this.currentIndex = 0;
		this.readImaginary = false;
		parse();
	}
	
	/** Method parses through the given String. **/
	public void parse() {
		double re = 0.;
		double im = 0.;
		try {
			double num1 = getNumber();
			skipWhitespaces();
			if(!readImaginary) {
				re = num1;
				
				if(currentIndex != data.length) {
					double num2 = getNumber();
					if(!readImaginary)
						throw new IllegalArgumentException("Illegal input - input must be a complex number!");
					im = num2;
					
					skipWhitespaces();
				}
			} else {
				im = num1;
			}
			if(currentIndex != data.length)
				throw new IllegalArgumentException("Illegal input - input must be a complex number!");
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Illegal input - input must be a complex number!");
		}
		complexNumber = new Complex(re, im);
	}

	/** Used to skip all next whitespaces. **/
	private void skipWhitespaces() {
		if(currentIndex == data.length) return;
		
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			if(currentIndex == data.length) return;
		}
	}

	/** Used to get one number which represents real or imaginary of complex number. **/
	private double getNumber() {
		String number = "";
		if(data[currentIndex] == '+' || data[currentIndex] == '-')
			number += data[currentIndex++];
		
		skipWhitespaces();
		while(true) {
			if(currentIndex == data.length) break;
			if(data[currentIndex] == 'i') {
				readImaginary = true;
				currentIndex++;
				skipWhitespaces();
				if(currentIndex == data.length) {
					number += 1;
					break;					
				}
				continue;
			}
			if(!Character.isDigit(data[currentIndex]) && data[currentIndex] != '.') break;
			
			number += data[currentIndex++];
		}
		return Double.parseDouble(number);
	}

	/**
	 * Getter method for the created {@link Complex} number.
	 * 
	 * @return the created {@link Complex} number
	 */
	public Complex getComplexNumber() {
		return this.complexNumber;
	}

}
