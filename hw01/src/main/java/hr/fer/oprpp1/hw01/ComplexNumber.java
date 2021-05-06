package hr.fer.oprpp1.hw01;

/**
 * Instances of this class act like an implementation of a support for working with complex numbers.
 * Complex number looks like "a + bi" where a represents real part of it and b represents the
 * imaginary part. Also, in scientific mode it can look like "r*(cos(angle)+i*sin(angle))" where r
 * is magnitude of the complex number.
 * 
 * @author lukasunara
 *
 */
public class ComplexNumber {

	/** The real part of a complex number **/
	private double real;
	
	/** The imaginary part of a complex number **/
	private double imaginary;
	
	/**
	 * Constructor creates a new ComplexNumber with given real and imaginary values.
	 * 
	 * @param real the real part of a complex number
	 * @param imaginary the imaginary part of a complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * It's a static factory method. Creates a new ComplexNumber which has only the real part.
	 * 
	 * @param real the real part of a complex number
	 * @return reference on the new ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.);
	}
	
	/**
	 * It's a static factory method. Creates a new ComplexNumber which has only the imaginary part.
	 * 
	 * @param imaginary the imaginary part of a complex number
	 * @return reference on the new ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0., imaginary);
	}

	/**
	 * It's a static factory method. Creates a new ComplexNumber by knowing its magnitude
	 * and angle.
	 * 
	 * @param magnitude double value which equals Math.sqrt(real*real + imaginary*imaginary)
	 * @param angle double value which equals Math.atan2(imaginary, real);
	 * @return reference on the new ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	
	/**
	 * It's a static factory method. Creates a new ComplexNumber from a String. Accepts strings
	 * such as: "3.51", "-3.17", "-2.71i", "i", "1", "-2.71-3.15i").
	 * 
	 * @param s the given String from which a complex number is created
	 * @return reference on the new ComplexNumber
	 * @throws IllegalArgumentException when the String is not well specified
	 */
	public static ComplexNumber parse(String s) {
		if(s.isEmpty())
			throw new IllegalArgumentException("Illegal argument to parse!");
		if(s.contains("++") || s.contains("+-") || s.contains("--") || s.contains("-+")) {
			throw new IllegalArgumentException("Illegal argument to parse!");
		}
		// Now we know there can only be +,- or nothing before numbers
		
		if(s.equals("i") || s.equals("+i")) {
			return new ComplexNumber(0., 1.);
		}
		if(s.equals("-i")) {
			return new ComplexNumber(0., -1.);
		}
		
		char[] charArray = s.toCharArray();
		double realPart = 0., imaginaryPart = 0.;
		String number1 = "", number2 = "";
		// start reading number1
		if(charArray[0]=='-' || charArray[0]=='+' || Character.isDigit(charArray[0])) {
			number1 += charArray[0];
		} else {
			throw new IllegalArgumentException("Illegal argument to parse!");
		}
		int countDots1 = 0, countDots2 = 0;
		for(int i = 1; i < charArray.length; i++) {
			// reads the rest od first double number value (can have only one '.')
			if(Character.isDigit(charArray[i])) {
				number1 += charArray[i];
			} else if(charArray[i]=='.' && countDots1==0 && Character.isDigit(charArray[i-1])) {
				number1 += charArray[i];
				countDots1++;
			} else if(charArray[i]=='i' && i==charArray.length-1) {
				// this should be the end of string
				imaginaryPart = Double.parseDouble(number1);
				break;
			} else if(charArray[i]=='+' || charArray[i]=='-' || Character.isDigit(charArray[i])) {
				// start reading number2
				number2 += charArray[i];
				countDots2 = 0;
				for(int j = i+1; j < charArray.length; j++) {
					if(Character.isDigit(charArray[j])) {
						number2 += charArray[j];
					} else if(charArray[j]=='.' && countDots2==0 && Character.isDigit(charArray[j-1])) {
						number2 += charArray[j];
						countDots2++;
					} else if(charArray[j]=='i' && j==charArray.length-1) {
						// this should be the end of string
						realPart = Double.parseDouble(number1);
						imaginaryPart = Double.parseDouble(number2);
						break;
					} else {
						throw new IllegalArgumentException("Illegal argument to parse!");
					}
				}
				break;
			} else {
				throw new IllegalArgumentException("Illegal argument to parse!");
			}
		}
		return new ComplexNumber(realPart, imaginaryPart);
	}
	
	/**
	 * Returns the real part of this ComplexNumber.
	 * 
	 * @return the real part of complex number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns the imaginary part of this ComplexNumber.
	 * 
	 * @return the imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Calculates and returns magnitude of this ComplexNumber.
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}

	/**
	 * Calculates and returns angle of this ComplexNumber.
	 * 
	 * @return angle of complex number (angle is in radians, from 0 to 2 Math.PI)
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		
		return angle<0 ? angle+=2*Math.PI : angle;
	}

	/**
	 * Creates a new ComplexNumber which is a result of adding a ComplexNumbers to
	 * this ComplexNumber.
	 * 
	 * @param c ComplexNumber which is added to this complex number
	 * @return reference on the new ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Creates a new ComplexNumber which is a result of substituting a ComplexNumbers from
	 * this ComplexNumber.
	 * 
	 * @param c ComplexNumber which is substituted from this complex number
	 * @return reference on the new ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Creates a new ComplexNumber which is a result of multiplying the given ComplexNumbers
	 * and this ComplexNumber.
	 * 
	 * @param c ComplexNumber which is multiplied with this complex number
	 * @return reference on the new ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double r = this.getMagnitude() * c.getMagnitude();
		double newAngle = this.getAngle() + c.getAngle();
		
		return new ComplexNumber(r*Math.cos(newAngle), r*Math.sin(newAngle));
	}

	/**
	 * Creates a new ComplexNumber which is a result of dividing the given ComplexNumbers
	 * from this ComplexNumber.
	 * 
	 * @param c ComplexNumber which is divided from this complex number
	 * @return reference on the new ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c) {
		double r = this.getMagnitude() / c.getMagnitude();
		double newAngle = this.getAngle() - c.getAngle();
		
		return new ComplexNumber(r*Math.cos(newAngle), r*Math.sin(newAngle));
	}
	
	/**
	 * Creates a new ComplexNumber which is a result of n-th power of this ComplexNumber.
	 * 
	 * @param n int value which is the n-th power
	 * @return reference on the new ComplexNumber
	 * @throws IllegalArgumentException when <code>n < 0</code>
	 */
	public ComplexNumber power(int n) {
		if(n < 0) 
			throw new IllegalArgumentException("Operation power(): n < 0 !");
		
		double r = Math.pow(this.getMagnitude(), n);
		double newAngle = this.getAngle()*n;
		
		return new ComplexNumber(r*Math.cos(newAngle), r*Math.sin(newAngle));
	}
	
	/**
	 * Creates an array of n new complex numbers which is a result of n-th root operation
	 * of this ComplexNumber.
	 * 
	 * @param n int value which is the n-th root
	 * @return reference on an array of n new complex numbers
	 * @throws IllegalArgumentException when <code>n <= 0</code>
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0)
			throw new IllegalArgumentException("Operation root(): n <= 0 !");
		
		ComplexNumber[] resultsOfRoot = new ComplexNumber[n];
		double r = Math.pow(this.getMagnitude(), 1./n);
		double newAngle;
		for(int k = 0; k < n; k++) {
			newAngle = (this.getAngle() + 2*Math.PI*k) / n;
			resultsOfRoot[k] = new ComplexNumber(r*Math.cos(newAngle), r*Math.sin(newAngle));
		}
		
		return resultsOfRoot;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(real == 0. && imaginary == 0.) return "0";
		
		if(real != 0.) sb.append(real);
		if(imaginary != 0.) {
			if(imaginary == -1.) {
				sb.append("-");
			}
			else if(imaginary < 0.) {
				sb.append(imaginary);
			} else {
				if(real != 0.) sb.append("+");
				if(imaginary != 1.)sb.append(imaginary);
			}
			sb.append("i");
		}
		return sb.toString();
	}
	
}
