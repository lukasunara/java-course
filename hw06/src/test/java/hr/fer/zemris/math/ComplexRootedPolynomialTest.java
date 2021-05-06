package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void testGetRootsUnmodifiable() {
		ComplexRootedPolynomial polynom = buildPolynom();
		
		assertThrows(UnsupportedOperationException.class, () -> polynom.getRoots().add(Complex.ONE));
		assertThrows(UnsupportedOperationException.class, () -> polynom.getRoots().remove(0));
	}

	@Test
	public void testApply() {
		ComplexRootedPolynomial polynom = buildPolynom();
		Complex c = new Complex(39.09, 40.62);
		Complex res = polynom.apply(new Complex(1.4, -3.2));
		
		assertEquals(c.getRe(), res.getRe(), 0.000001);
		assertEquals(c.getIm(), res.getIm(), 0.000001);		
	}
	
	@Test
	public void testToComplexPolynom() {
		// crp1
		ComplexRootedPolynomial crp1 = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
			);
		ComplexPolynomial cp1 = crp1.toComplexPolynom();
		
		assertEquals("(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))"
				, crp1.toString()
			);
		assertEquals("(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)"
				, cp1.toString()
			);
		Complex c = new Complex(1.4, -3.2);
		Complex res1 = crp1.apply(c);
		Complex res2 = cp1.apply(c);
		assertEquals(res1.getRe(), res2.getRe(), 0.000001);
		assertEquals(res1.getIm(), res2.getIm(), 0.000001);
		
		// crp2
		ComplexRootedPolynomial crp2 = buildPolynom();
		ComplexPolynomial cp2 = crp2.toComplexPolynom();
	
		assertEquals("(2.0+i1.0)*(z-(2.0-i3.5))*(z-(1.2+i3.2))*(z-(-2.7+i1.0))"
				, crp2.toString()
			);
		assertEquals("(2.0+i1.0)*z^3+(-0.3-i1.9)*z^2+(4.3+i17.7)*z^1+(85.5+i23.6)"
				, cp2.toString()
			);
		Complex res3 = crp2.apply(c);
		Complex res4 = cp2.apply(c);
		assertEquals(res3.getRe(), res4.getRe(), 0.000001);
		assertEquals(res3.getIm(), res4.getIm(), 0.000001);
	}
	
	@Test
	public void testToString() {
		ComplexRootedPolynomial polynom1 = buildPolynom();
		assertEquals("(2.0+i1.0)*(z-(2.0-i3.5))*(z-(1.2+i3.2))*(z-(-2.7+i1.0))"
				, polynom1.toString()
			);
		
		ComplexRootedPolynomial polynom2 = new ComplexRootedPolynomial(Complex.IM);
		assertEquals("0.0+i1.0", polynom2.toString());
	}
	
	@Test
	public void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial polynom = buildPolynom();
		
		assertEquals(2, polynom.indexOfClosestRootFor(Complex.ZERO, 3.25));
		assertEquals(-1, polynom.indexOfClosestRootFor(Complex.IM, 0.2));
		assertEquals(0, polynom.indexOfClosestRootFor(Complex.IM_NEG, 3.21));
		assertEquals(1, polynom.indexOfClosestRootFor(Complex.ONE, 3.209));
		assertEquals(2, polynom.indexOfClosestRootFor(Complex.ONE_NEG, 1.975));
		assertEquals(1, polynom.indexOfClosestRootFor(new Complex(1.75, 3.0), 1.25));
		assertEquals(1, polynom.indexOfClosestRootFor(new Complex(1.75, 3.0), 11.25));
	}
	
	private ComplexRootedPolynomial buildPolynom() {
		// (2+1i)(z-(2-3.5i))(z-(1.2+3.2i))(z-(-2.7+1i))
		Complex c1 = new Complex(2, 1);
		Complex c2 = new Complex(2, -3.5);
		Complex c3 = new Complex(1.2, 3.2);
		Complex c4 = new Complex(-2.7, 1);

		return new ComplexRootedPolynomial(c1, c2, c3, c4);
	}
}
