package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void testConstructor() {
		ComplexNumber num1 = new ComplexNumber(1., -1.);
		assertEquals(1., num1.getReal());
		assertEquals(-1., num1.getImaginary());
		
		ComplexNumber num2 = new ComplexNumber(1.74, 2.);
		assertEquals(1.74, num2.getReal());
		assertEquals(2., num2.getImaginary());
	}
	
	@Test
	public void testFromReal() {
		ComplexNumber num = ComplexNumber.fromReal(5.25);
		assertEquals(5.25, num.getReal());
		assertEquals(0., num.getImaginary());
	}
	
	@Test
	public void testFromImaginary() {
		ComplexNumber num = ComplexNumber.fromImaginary(5.25);
		assertEquals(0., num.getReal());
		assertEquals(5.25, num.getImaginary());
	}
	
	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber num = ComplexNumber.fromMagnitudeAndAngle(4, Math.PI/4);
		assertEquals(2.82842712474, num.getReal(), 0.000001);
		assertEquals(2.82842712474, num.getImaginary(), 0.000001);
	}
	
	@Test
	public void testParse() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		assertEquals(2., c1.getReal());
		assertEquals(3., c1.getImaginary());
		
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		assertEquals(2.5, c2.getReal());
		assertEquals(-3., c2.getImaginary());
		
		ComplexNumber c3 = ComplexNumber.parse("+2+3i");
		assertEquals(2., c3.getReal());
		assertEquals(3., c3.getImaginary());
		
		ComplexNumber c4 = ComplexNumber.parse("-2-3i");
		assertEquals(-2., c4.getReal());
		assertEquals(-3., c4.getImaginary());
		
		ComplexNumber c5 = ComplexNumber.parse("-2+3i");
		assertEquals(-2., c5.getReal());
		assertEquals(3., c5.getImaginary());
		
		ComplexNumber c6 = ComplexNumber.parse("+3i");
		assertEquals(0., c6.getReal());
		assertEquals(3., c6.getImaginary());
		
		ComplexNumber c7 = ComplexNumber.parse("3i");
		assertEquals(0., c7.getReal());
		assertEquals(3., c7.getImaginary());
		
		ComplexNumber c8 = ComplexNumber.parse("-3i");
		assertEquals(0., c8.getReal());
		assertEquals(-3., c8.getImaginary());
		
		ComplexNumber c9 = ComplexNumber.parse("-i");
		assertEquals(0., c9.getReal());
		assertEquals(-1., c9.getImaginary());
		
		ComplexNumber c10 = ComplexNumber.parse("i");
		assertEquals(0., c10.getReal());
		assertEquals(1., c10.getImaginary());
		
		ComplexNumber c11 = ComplexNumber.parse("+i");
		assertEquals(0., c11.getReal());
		assertEquals(1., c11.getImaginary());
	}
	
	@Test
	public void testParseShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("+-3i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-+3i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i351"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-i3.17"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2.71+-3.15i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2.71+i3.15"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2.7.1-3.15i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2.71+3.1.5i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("3i0"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("zsad"));
	}
	
	@Test
	public void testGetMagnitude() {
		ComplexNumber num = new ComplexNumber(5.5, -3.3);
		
		assertEquals(6.41404708, num.getMagnitude(), 0.000001);
	}
	
	@Test
	public void testGetAngle() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		assertEquals(Math.PI/4, c1.getAngle(), 0.00001);
		
		ComplexNumber c2 = new ComplexNumber(1, -1);
		assertEquals(7*Math.PI/4, c2.getAngle(), 0.000001);
		
		ComplexNumber c3 = new ComplexNumber(-1, -1);
		assertEquals(5*Math.PI/4, c3.getAngle(), 0.000001);
		
		ComplexNumber c4 = new ComplexNumber(-1, 1);
		assertEquals(3*Math.PI/4, c4.getAngle(), 0.000001);
	}
	
	@Test
	public void testAdd() {
		ComplexNumber c1 = new ComplexNumber(1.5, 1.5);
		ComplexNumber c2 = new ComplexNumber(1.5, -1.25);
		ComplexNumber c3 = c1.add(c2);
		
		assertEquals(3., c3.getReal());
		assertEquals(0.25, c3.getImaginary());
	}
	
	@Test
	public void testSub() {
		ComplexNumber c1 = new ComplexNumber(1.5, 1.5);
		ComplexNumber c2 = new ComplexNumber(1.5, -1.25);
		ComplexNumber c3 = c1.sub(c2);
		
		assertEquals(0., c3.getReal());
		assertEquals(2.75, c3.getImaginary());
	}
	
	@Test
	public void testMul() {
		ComplexNumber c1 = new ComplexNumber(1.5, 1.5);
		ComplexNumber c2 = new ComplexNumber(1.5, -1.25);
		ComplexNumber c3 = c1.mul(c2);
		
		assertEquals(4.125, c3.getReal(), 0.001);
		assertEquals(0.375, c3.getImaginary(), 0.001);
	}
	
	@Test
	public void testDiv() {
		ComplexNumber c1 = new ComplexNumber(1.5, 1.5);
		ComplexNumber c2 = new ComplexNumber(1.5, -1.25);
		ComplexNumber c3 = c1.div(c2);
		
		assertEquals(0.0983607, c3.getReal(), 0.000001);
		assertEquals(1.08197, c3.getImaginary(), 0.00001);
	}
	
	@Test
	public void testPower() {
		ComplexNumber c1 = new ComplexNumber(1.5, 1.5);
		ComplexNumber res = c1.power(3);
		
		assertEquals(-6.75, res.getReal(), 0.01);
		assertEquals(6.75, res.getImaginary(), 0.01);
	}
	
	@Test
	public void testRoot() {
		ComplexNumber c1 = new ComplexNumber(1.5, 1.5);
		ComplexNumber[] res = c1.root(3);
		
		assertEquals(1.2411, res[0].getReal(), 0.0001);
		assertEquals(0.33256, res[0].getImaginary(), 0.0001);
		
		assertEquals(-0.90856, res[1].getReal(), 0.0001);
		assertEquals(0.90856, res[1].getImaginary(), 0.0001);
		
		assertEquals(-0.33256, res[2].getReal(), 0.0001);
		assertEquals(-1.2411, res[2].getImaginary(), 0.0001);
	}
	
	@Test
	public void testToString() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		assertEquals("2.0+3.0i", c1.toString());
		
		ComplexNumber c2 = new ComplexNumber(2.5, -3);
		assertEquals("2.5-3.0i", c2.toString());
		
		ComplexNumber c3 = new ComplexNumber(2, 3);
		assertEquals("2.0+3.0i", c3.toString());
		
		ComplexNumber c4 = new ComplexNumber(-2, -3);
		assertEquals("-2.0-3.0i", c4.toString());
		
		ComplexNumber c5 = new ComplexNumber(-2, 3);
		assertEquals("-2.0+3.0i", c5.toString());
		
		ComplexNumber c6 = new ComplexNumber(0, 3);
		assertEquals("3.0i", c6.toString());

		ComplexNumber c7 = new ComplexNumber(0, -3);
		assertEquals("-3.0i", c7.toString());
		
		ComplexNumber c8 = new ComplexNumber(0, -1);
		assertEquals("-i", c8.toString());
		
		ComplexNumber c9 = new ComplexNumber(0, 1);
		assertEquals("i", c9.toString());
	}
}
