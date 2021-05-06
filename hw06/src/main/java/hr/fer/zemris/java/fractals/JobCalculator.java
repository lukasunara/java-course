package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Instances of this class implement {@link Runnable} and in its run() method define how
 * are the {@link Thread}s going to calculate their job.
 * 
 * @author lukasunara
 *
 */
public class JobCalculator implements Runnable {
	
	/** Represents an "empty" job **/
	public static JobCalculator NO_JOB = new JobCalculator();

	private double reMin;
	private double reMax;
	private double imMin;
	private double imMax;
	private int width;
	private int height;
	private int yMin;
	private int yMax;
	private int m;
	private short[] data;
	private AtomicBoolean cancel;
	private ComplexRootedPolynomial crp;
	private ComplexPolynomial polynomial;
	
	/** Default constructor for {@link JobCalculator} **/
	private JobCalculator() {
		super();
	}
	
	public JobCalculator(ComplexRootedPolynomial crp, ComplexPolynomial polynomial,
			double reMin, double reMax, double imMin, double imMax, int width, int height,
			int yMin, int yMax, int m, short[] data, AtomicBoolean cancel) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.m = m;
		this.data = data;
		this.cancel = cancel;
		this.crp = crp;
		this.polynomial = polynomial;
	}
	
	@Override
	public void run() {
		int maxIter = m;
		int offset = yMin * width;
		double convergenceTreshold = 0.001;
		double rootTreshold = 0.002;
		
		for(int y = yMin; y <= yMax; y++) {
			if(cancel.get()) break;
			
			for(int x = 0; x < width; x++) {
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
				
				Complex zn = new Complex(cre, cim);
				
				int iter = 0;
				double module = 0.;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = polynomial.derive().apply(zn);
					Complex znold = zn;
					Complex fraction = numerator.divide(denominator);
					
					zn = zn.sub(fraction);
					module = znold.sub(zn).module();
					iter++;
				} while(module > convergenceTreshold && iter < maxIter);
				
				int index = crp.indexOfClosestRootFor(zn, rootTreshold);
				data[offset++] = (short) (index + 1);
			}
		}
	}
	
}
