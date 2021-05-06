package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class act like an implementation of a support for working with complex numbers.
 * Complex number looks like "a + bi" where a represents the real part and b represents the imaginary
 * part of the complex number. Also, in scientific mode it can look like "r*(cos(angle)+i*sin(angle))"
 * where r represents magnitude of the complex number.
 * 
 * @author lukasunara
 *
 */
public class Complex {

	/** Represents a complex number with re=0 and im=0 **/
	public static final Complex ZERO = new Complex(0,0);
	
	/** Represents a complex number with re=1 and im=0 **/
	public static final Complex ONE = new Complex(1,0);
	
	/** Represents a complex number with re=-1 and im=0 **/
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/** Represents a complex number with re=0 and im=1 **/
	public static final Complex IM = new Complex(0,1);
	
	/** Represents a complex number with re=0 and im=-1 **/
	public static final Complex IM_NEG = new Complex(0,-1);

	/** double read-only value which represents the real part of a complex number **/
	private double re;
	
	/** double read-only value which represents the imaginary part of a complex number **/
	private double im;
	
	/** Default constructor creates a complex number and sets the real and imaginary part to 0. **/
	public Complex() {
		this(ZERO.re, ZERO.im);
	}

	/**
	 * Creates and sets the real and imaginary part to given values.
	 * 
	 * @param re double value which represents the real part of a complex number
	 * @param im double value which represents the imaginary part of a complex number
	 */
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}

	/**
	 * Getter method for real part of this complex number.
	 * 
	 * @return the real part of this complex number
	 */
	public double getRe() {
		return this.re;
	}

	/**
	 * Getter method for imaginary part of this complex number.
	 * 
	 * @return the imaginary part of this complex number
	 */
	public double getIm() {
		return this.im;
	}
	
	/**
	 * Returns module of complex number.
	 * 
	 * @return double value which represents module of complex number
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}

	/**
	 * Multiplies this complex number with given complex number c. 
	 *
	 * @param c Complex number which is the multiplier
	 * @return new Complex number which is the result of multiplication of two complex numbers
	 */
	public Complex multiply(Complex c) {
		double newRe = this.re*c.re - this.im*c.im;
		double newIm = this.re*c.im + this.im*c.re;
		
		return new Complex(newRe, newIm);
	}

	/**
	 * Divides this complex number with given complex number c. 
	 *
	 * @param c Complex number which is the divider
	 * @return new Complex number which is the result of division of two complex numbers
	 */
	public Complex divide(Complex c) {
		double dividor = c.re*c.re + c.im*c.im;
		double newRe = (this.re*c.re + this.im*c.im) / dividor;
		double newIm = (this.im*c.re - this.re*c.im) / dividor;
		
		return new Complex(newRe, newIm);
	}

	/**
	 * Adds the given complex number c to this complex number. 
	 *
	 * @param c Complex number which is added to this complex number
	 * @return new Complex number which is the result of adding two complex numbers
	 */
	public Complex add(Complex c) {
		return new Complex(this.re+c.re, this.im+c.im);
	}

	/**
	 * Substitutes the given complex number c from this complex number. 
	 *
	 * @param c Complex number which is substituted from this complex number
	 * @return new Complex number which is the result of substitution of two complex numbers
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re-c.re, this.im-c.im);
	}

	/**
	 * Negates this complex number. 
	 *
	 * @return new Complex number which is the result of negation of this complex numbers
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}

	/**
	 * Creates a new complex number which is a result of n-th power of this complex number.
	 * 
	 * @param n int value which represents the n-th power
	 * @return reference on the new complex number
	 * @throws IllegalArgumentException when <code>n < 0</code>
	 */
	public Complex power(int n) {
		if(n < 0) 
			throw new IllegalArgumentException("Operation power(): n must be a non-negative int!");
		
		double r = Math.pow(this.module(), n);
		double newAngle = this.angle()*n;
		
		return new Complex(r*Math.cos(newAngle), r*Math.sin(newAngle));
	}

	// returns n-th root of this, n is positive integer
	/**
	 * Creates a {@link List} of n new complex numbers which represents the result of n-th
	 * root operation of this complex number.
	 * 
	 * @param n int value which is the n-th root
	 * @return reference on the {@link List} of n new complex numbers
	 * @throws IllegalArgumentException when <code>n <= 0</code>
	 */
	public List<Complex> root(int n) {
		if(n <= 0)
			throw new IllegalArgumentException("Operation root(): n must be a positive int!");
		
		double r = Math.pow(this.module(), 1./n);
		
		List<Complex> resultsOfRoot = new ArrayList<>();
		double newAngle;
		for(int k = 0; k < n; k++) {
			newAngle = (this.angle() + 2*Math.PI*k) / n;
			resultsOfRoot.add(new Complex(r*Math.cos(newAngle), r*Math.sin(newAngle)));
		}
		return resultsOfRoot;
	}

	/**
	 * Calculates and returns angle of this complex number.
	 * 
	 * @return angle of complex number (angle is in radians, from 0 to 2 Math.PI)
	 */
	private double angle() {
		double angle = Math.atan2(im, re);
		
		return angle < 0 ? angle+=2*Math.PI : angle;
	}
	
	@Override
	public String toString() {
		return String.format("%.1f%ci%.1f", re, im<0. ? '-' : '+', Math.abs(im));
	}

}
