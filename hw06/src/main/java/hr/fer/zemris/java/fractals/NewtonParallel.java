package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents a program which starts creating an image of fractals
 * using multiple {@link Thread}s.
 * 
 * @author lukasunara
 *
 */
public class NewtonParallel {

	/** Number of threads used for parallelization **/
	private static int n;
	
	/** Number of tracks (i.e. jobs) used in production **/
	private static int k;
	
	/**
	 * Method used for interaction with user and starts the program.
	 * 
	 * @param args used for definition of N and K
	 */
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i++) {
			int checkReturnInt = parseArgument(args[i], i);
			if(checkReturnInt != i) {
				if(checkReturnInt == -5) n = Integer.parseInt(args[++i]);
				if(checkReturnInt == -10) k = Integer.parseInt(args[++i]);
			}
		}
		
		if(n == 0) n = Runtime.getRuntime().availableProcessors();
		if(k == 0) k = 4 * Runtime.getRuntime().availableProcessors();
		if(n < 1 || k < 1)
			throw new IllegalArgumentException("Number of workers and tracks must be > 1 !");
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				Complex.ONE, Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
			);
		FractalViewer.show(new NewtonRaphsonParallelProducer(crp, n, k));
	}

	/** Used for parsing arguments received from user. **/
	private static int parseArgument(String argument, int skipOneArg) {
		if(argument.startsWith("--workers=")) {
			checkIfAlreadySet(n);
			n = Integer.parseInt(argument.substring(argument.indexOf('=')+1));
		} else if(argument.startsWith("-w")) {
			checkIfAlreadySet(n);
			skipOneArg = -5;
		} else if(argument.startsWith("--tracks=")) {
			checkIfAlreadySet(k);
			k = Integer.parseInt(argument.substring(argument.indexOf('=')+1));
		} else if(argument.startsWith("-t")) {
			checkIfAlreadySet(k);
			skipOneArg = -10;
		} else {
			throw new IllegalArgumentException("Program cannot recognize: " + argument);
		}
		return skipOneArg;
	}
	
	/** Checks if N or K had already been set. **/
	private static void checkIfAlreadySet(int i) {
		if(i != 0)
			throw new IllegalArgumentException("Number of workers or tracks is being set twice!");
	}
	
}
