package hr.fer.zemris.java.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Instances of this class implement {@link IFractalProducer} and produce fractals used
 * for creating an image.
 * 
 * @author lukasunara
 *
 */
public class NewtonRaphsonParallelProducer implements IFractalProducer {
	
	/** Number of threads used for parallelization **/
	private int n;
	
	/** Number of tracks (i.e. jobs) used in production **/
	private int k;
	
	/** The {@link ComplexRootedPolynomial} used for calculating fractals **/
	private ComplexRootedPolynomial crp;
	
	/**
	 * Constructor creates a new {@link NewtonRaphsonParallelProducer} with the given arguments.
	 * 
	 * @param crp the {@link ComplexRootedPolynomial} used for calculating fractals
	 * @param n number of tracks (i.e. jobs) used in production
	 * @param k number of threads used for parallelization
	 */
	public NewtonRaphsonParallelProducer(ComplexRootedPolynomial crp, int n, int k) {
		super();
		this.n = n;
		this.k = k;
		this.crp = crp;
	}
	
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax,	int width,
			int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
		
		// If user specifies K which is larger than the number of rows in picture => K=height
		if(k > height) k = height;
		ComplexPolynomial polynomial = crp.toComplexPolynom();
		
		System.out.format("Started calculating... Using %d threads and %d jobs...\n", n, k);
		int m = 16*16*16;
		short[] data = new short[width * height];
		int numOfYPerTrack = height / k;
		
		final BlockingQueue<JobCalculator> queue = new LinkedBlockingQueue<>();

		Thread[] workers = new Thread[n];
		for(int i = 0; i < workers.length; i++) {
			workers[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						JobCalculator p = null;
						try {
							p = queue.take();
							if(p == JobCalculator.NO_JOB) break;
						} catch (InterruptedException exc) {
							continue;
						}
						p.run();
					}
				}
			});
		}
		for(int i = 0; i < workers.length; i++) {
			workers[i].start();
		}
		
		for(int i = 0; i < k; i++) {
			int yMin = i * numOfYPerTrack;
			int yMax = (i + 1) * numOfYPerTrack - 1;
			
			if(i == k-1) {
				yMax = height - 1;
			}
			JobCalculator job = new JobCalculator(crp, polynomial, reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
			
			while(true) {
				try {
					queue.put(job); // set real jobs for all threads
					break;
				} catch (InterruptedException exc) { }
			}
		}
		
		for(int i = 0; i < workers.length; i++) {
			while(true) {
				try {
					queue.put(JobCalculator.NO_JOB); // thread poisoning in the end of threads jobs
					break;
				} catch (InterruptedException exc) { }
			}
		}
		
		for(int i = 0; i < workers.length; i++) {
			while(true) {
				try {
					workers[i].join(); // wait for all threads to finnish their jobs
					break;
				} catch (InterruptedException exc) { }
			}
		}
		
		System.out.println("Calculation is completed. Lets notify the observer GUI!");
		observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
	}
	
}
