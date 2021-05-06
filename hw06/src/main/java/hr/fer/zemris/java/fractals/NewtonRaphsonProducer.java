package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Instances of this class implement {@link IFractalProducer} and produce fractals used
 * for creating an image.
 * 
 * @author lukasunara
 *
 */
public class NewtonRaphsonProducer implements IFractalProducer {

	/** The {@link ComplexRootedPolynomial} used for calculating fractals **/
	private ComplexRootedPolynomial crp;
	
	/**
	 * Constructor creates a new {@link NewtonRaphsonParallelProducer} with the given arguments.
	 * 
	 * @param crp the {@link ComplexRootedPolynomial} used for calculating fractals
	 */
	public NewtonRaphsonProducer(ComplexRootedPolynomial crp) {
		super();
		this.crp = crp;
	}
	
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax,
			int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
		
		ComplexPolynomial polynomial = crp.toComplexPolynom();
		
		double convergenceTreshold = 0.001;
		double rootTreshold = 0.002;
		
		System.out.println("Started calculating...");
		
		int maxIter = 16*16*16;
		int offset = 0;
		short[] data = new short[width * height];
		for(int y = 0; y < height; y++) {
			if(cancel.get()) break;
			
			for(int x = 0; x < width; x++) {
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = (height - 1.0 - y) / (height-1) * (imMax - imMin) + imMin;
				
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
		System.out.println("Calculation is completed. Lets notify the observer GUI!");
		observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
	}
	
}
