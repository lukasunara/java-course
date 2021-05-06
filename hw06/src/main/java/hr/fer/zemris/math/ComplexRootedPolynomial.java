package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Instances of this class represent a polynomial f(z)=z0*(z-z1)*(z-z2)*...*(z-zn) where z0
 * represents a {@link Complex} constant, while zi and z represent a {@link Complex} number.
 * 
 * @author lukasunara
 *
 */
public class ComplexRootedPolynomial {

	/** Represents the z0 {@link Complex} constant (read-only) **/
	private Complex constant;
	
	/** This read-only list represents the {@link Complex} roots of this polynomial **/
	private List<Complex> roots;

	/**
	 * Constructor receives a constant and 0 or more {@link Complex} numbers which represent
	 * the roots of this {@link ComplexRootedPolynomial}.
	 * 
	 * @param constant {@link Complex} constant z0 of this {@link ComplexRootedPolynomial}
	 * @param roots represent the {@link Complex} roots of this {@link ComplexRootedPolynomial}
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		super();
		this.constant = constant;
		this.roots = Arrays.asList(roots);
	}

	/**
	 * Getter method for the read-only constant z0 of this polynomial.
	 * 
	 * @return the constant z0 of this polynomial
	 */
	public Complex getConstant() {
		return this.constant;
	}

	/**
	 * Getter method for the read-only {@link List} which contains the roots of this polynomial.
	 * 
	 * @return the roots zi of this polynomial
	 */
	public List<Complex> getRoots() {
		return Collections.unmodifiableList(this.roots);
	}

	/**
	 * Computes polynomial value at given point z and returns the result as a {@link Complex} number.
	 * 
	 * @param z specific {@link Complex} value for which this polynomial is computed
	 * @return the result as a {@link Complex} number
	 */
	public Complex apply(Complex z) {
		Complex res = constant; // res = z0
		for(Complex root : roots) {
			res = res.multiply(z.sub(root)); // res *= (z-zi)
		}			
		return res;
	}

	/**
	 * Converts this representation to {@link ComplexPolynomial} type.
	 * 
	 * @return this ppolynomial in {@link ComplexPolynomial} type
	 */
	public ComplexPolynomial toComplexPolynom() {
		// create polynom for every root f(z)=z-zi
		// and multiply them all to get: f(z)=z0*(z-z1)*(z-z2)*...*(z-zn)
		ComplexPolynomial finalPolynom = new ComplexPolynomial(constant);
		for(var root : roots) {
			finalPolynom = finalPolynom.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
		}
		return finalPolynom;
	}

	@Override
	public String toString() {
		if(roots.isEmpty()) return constant.toString();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("(").append(constant.toString()).append(")");
		for(Complex root : roots) {
			sb.append("*(z-(").append(root.toString()).append("))");
		}
		return sb.toString();
	}

	/**
	 * Finds index of closest root for given {@link Complex} number z that is within treshold.
	 * If there is no such root, returns -1. First root has index 0, second index 1, etc.
	 * 
	 * @param z given {@link Complex} number used for difference measurement
	 * @param treshold double value used as a condition for difference
	 * @return index of closest root for given {@link Complex} number z that is within treshold;
	 * if there is no such root, return -1
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		// |z1-z2| = |(x1-x2)+(y1-y2)i| = sqrt((x1-x2)^2 + (y1-y2)^2)
		int indexOfMin = -1;
		double currentLowestTreshold = Double.MAX_VALUE;
		for(int i = 0; i < roots.size(); i++) {
			double difference = z.sub(roots.get(i)).module();
			if(difference <= treshold && difference < currentLowestTreshold) {
				indexOfMin = i;
				currentLowestTreshold = difference;
			}
		}
		return indexOfMin;
	}

}
