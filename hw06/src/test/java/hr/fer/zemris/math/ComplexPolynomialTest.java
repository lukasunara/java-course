package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {

	@Test
	public void testGetFactorsUnmodifiable() {
		ComplexPolynomial polynom = buildPolynom();
		
		assertThrows(UnsupportedOperationException.class, () -> polynom.getFactors().add(Complex.ONE));
		assertThrows(UnsupportedOperationException.class, () -> polynom.getFactors().remove(0));
	}
	
	@Test
	public void testOrder() {
		ComplexPolynomial polynom = buildPolynom();
		
		assertEquals(3, polynom.order());
	}
	
	@Test
	public void testMultiply() {
		Complex c1 = new Complex(2, 1);
		Complex c2 = new Complex(2, -3.5);
		ComplexPolynomial polynom1 = new ComplexPolynomial(c1, c2);
		
		Complex c3 = new Complex(1.2, 3.2);
		Complex c4 = new Complex(-2.7, 1);
		ComplexPolynomial polynom2 = new ComplexPolynomial(c3, c4);
		
		ComplexPolynomial polynom3 = polynom1.multiply(polynom2);
		
		assertEquals("(-1.9+i11.5)*z^2+(7.2+i1.5)*z^1+(-0.8+i7.6)", polynom3.toString());
	}
	
	@Test
	public void testDerive() {
		ComplexPolynomial polynom = buildPolynom();
		
		assertEquals("(-8.1+i3.0)*z^2+(2.4+i6.4)*z^1+(2.0-i3.5)", polynom.derive().toString());
	}
	
	@Test
	public void testApply() {
		ComplexPolynomial polynom = buildPolynom();
		Complex c = new Complex(107.0968, -125.4824);
		Complex res = polynom.apply(new Complex(1.4, -3.2));
		
		assertEquals(c.getRe(), res.getRe(), 0.000001);
		assertEquals(c.getIm(), res.getIm(), 0.000001);		
	}
	
	@Test
	public void testToString() {
		ComplexPolynomial polynom1 = buildPolynom();
		assertEquals("(-2.7+i1.0)*z^3+(1.2+i3.2)*z^2+(2.0-i3.5)*z^1+(2.0+i1.0)"
				, polynom1.toString()
			);
		
		ComplexPolynomial polynom2 = new ComplexPolynomial(new Complex(1.4, -3.2));
		assertEquals("1.4-i3.2", polynom2.toString());
	}
	
	private ComplexPolynomial buildPolynom() {
		// (-2.7+1i)*z^3+(1.2+3.2i)*z^2+(2-3.5i)*z^1+(2+i)
		Complex c1 = new Complex(2, 1);
		Complex c2 = new Complex(2, -3.5);
		Complex c3 = new Complex(1.2, 3.2);
		Complex c4 = new Complex(-2.7, 1);

		return new ComplexPolynomial(c1, c2, c3, c4);
	}
	
}
