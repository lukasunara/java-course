package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Instances of this class represent a polynomial f(z)=zn*z^n+zn-1*z^(n-1)+...+z2*z^2+z1*z+z0
 * where z0,...,zn are {@link Complex} coefficients with powers of {@link Complex} number z.
 * @author lukasunara
 *
 */
public class ComplexPolynomial {

	/** This read-only list represents the {@link Complex} factors of this polynomial **/
	private List<Complex> factors;

	/**
	 * Constructor receieves 0 or more {@link Complex} numbers which represent factors of this
	 * {@link ComplexPolynomial}.
	 * 
	 * @param factors array of {@link Complex} numbers which represent factors of this {@link ComplexPolynomial}
	 */
	public ComplexPolynomial(Complex... factors) {
		super();
		this.factors = Arrays.asList(factors);
	}

	/**
	 * Getter method for the read-only {@link List} which contains the factors of this polynomial.
	 * 
	 * @return the factors zi (z0,...,zn) of this polynomial
	 */
	public List<Complex> getFactors() {
		return Collections.unmodifiableList(factors);
	}
	
	/**
	 * Returns order of this polynomial.
	 * 
	 * @return short value which represents order of this poynomial
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Computes a new polynomial which is the result of multiplying this polynomial and
	 * the given polynomial.
	 * 
	 * @param p the given {@link ComplexPolynomial} used for multiplying this polynomial
	 * @return a new {@link ComplexPolynomial} which is the result of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.factors.size()+p.factors.size()-1];
		for(int i = 0; i < this.factors.size(); i++) {
			for(int j = 0; j < p.factors.size(); j++) {
				Complex multiplied = this.factors.get(i).multiply(p.factors.get(j));
				if(newFactors[i+j] != null) {
					newFactors[i+j] = newFactors[i+j].add(multiplied);
				} else {
					newFactors[i+j] = multiplied;
				}
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes first derivative of this polynomial.
	 *  
	 * @return a new {@link ComplexPolynomial} which represents the first derivative of this
	 * polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.factors.size()-1];
		for(int i = 1; i < factors.size(); i++) {
			newFactors[i-1] = new Complex(factors.get(i).getRe()*i, factors.get(i).getIm()*i);
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes polynomial value at given point z and returns the result as a {@link Complex} number.
	 * 
	 * @param z specific {@link Complex} value for which this polynomial is computed
	 * @return the result as a {@link Complex} number
	 */
	public Complex apply(Complex z) {
		Complex res = factors.get(0); // res = z0
		for(int i = 1; i < factors.size(); i++) {
			res = res.add(z.power(i).multiply(factors.get(i))); // res += zi*z^i
		}
		return res;
	}

	@Override
	public String toString() {
		if(factors.size() == 1) return factors.get(0).toString();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = factors.size()-1; i > 0; i--) {
			sb.append("(")
					.append(factors.get(i).toString())
					.append(")")
					.append("*z^")
					.append(i)
					.append("+");
		}
		sb.append("(").append(factors.get(0).toString()).append(")");
		
		return sb.toString();
	}

}
