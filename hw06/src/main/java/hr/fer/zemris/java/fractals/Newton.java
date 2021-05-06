package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
public class Newton {

	/**
	 * Method used for interaction with user and starts the program.
	 * 
	 * @param args these arguments are ignored
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		try(Scanner sc = new Scanner(System.in)) {
			List<Complex> listComplex = new ArrayList<>();
			String root = "";
			short rootIndex = 0;
			// read roots until user enters 'done'
			while(true) {
				System.out.format("Root %d> ", ++rootIndex);
				
				root = sc.nextLine().strip();
				if(root.equals("done")) {
					if(rootIndex <= 2) {
						System.out.format("Not enough roots! Please enter at least two roots.\n");
						rootIndex--;
						continue;
					}
					break;
				}
				
				try {
					listComplex.add(new ComplexParser(root).getComplexNumber());
				} catch(IllegalArgumentException ex) {
					System.out.println(ex.toString() + " Try again.");
					rootIndex--;
				}
			}

			ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
					Complex.ONE, // constant of polynomial is z0=1.0+i0.0
					listComplex.toArray(new Complex[listComplex.size()]) // roots of polynomial
				);
			FractalViewer.show(new NewtonRaphsonProducer(crp));
			System.out.println("Image of fractal will appear shortly. Thank you.");
		} catch(Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Terminating program.");
		}
		
//		this polynomial was used for testing method produce in NewtonRaphsonProducer class
//		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
//				Complex.ONE, Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
//			);
//		FractalViewer.show(new NewtonRaphsonProducer(crp));
	}
	
}
