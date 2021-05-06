package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ComplexTest {

	@Test
	public void testDefaultConstructor() {
		Complex num1 = new Complex();
		assertEquals(0., num1.getRe());
		assertEquals(0., num1.getIm());
	}
	
	@Test
	public void testConstructor() {
		Complex num1 = new Complex(1., -1.);
		assertEquals(1., num1.getRe());
		assertEquals(-1., num1.getIm());
		
		Complex num2 = new Complex(1.74, 2.);
		assertEquals(1.74, num2.getRe());
		assertEquals(2., num2.getIm());
	}
	
	@Test
	public void testModule() {
		Complex num = new Complex(5.5, -3.3);
		
		assertEquals(6.41404708, num.module(), 0.000001);
	}
	
	@Test
	public void testAdd() {
		Complex c1 = new Complex(1.5, 1.5);
		Complex c2 = new Complex(1.5, -1.25);
		Complex c3 = c1.add(c2);
		
		assertEquals(3., c3.getRe());
		assertEquals(0.25, c3.getIm());
	}
	
	@Test
	public void testSub() {
		Complex c1 = new Complex(1.5, 1.5);
		Complex c2 = new Complex(1.5, -1.25);
		Complex c3 = c1.sub(c2);
		
		assertEquals(0., c3.getRe());
		assertEquals(2.75, c3.getIm());
	}
	
	@Test
	public void testMuliply() {
		Complex c1 = new Complex(1.5, 1.5);
		Complex c2 = new Complex(1.5, -1.25);
		Complex c3 = c1.multiply(c2);
		
		assertEquals(4.125, c3.getRe(), 0.001);
		assertEquals(0.375, c3.getIm(), 0.001);
	}
	
	@Test
	public void testDivide() {
		Complex c1 = new Complex(1.5, 1.5);
		Complex c2 = new Complex(1.5, -1.25);
		Complex c3 = c1.divide(c2);
		
		assertEquals(0.0983607, c3.getRe(), 0.000001);
		assertEquals(1.08197, c3.getIm(), 0.00001);
	}
	
	@Test
	public void testPower() {
		Complex c1 = new Complex(1.5, 1.5);
		Complex res = c1.power(3);
		
		assertEquals(-6.75, res.getRe(), 0.01);
		assertEquals(6.75, res.getIm(), 0.01);
		
		Complex c2 = new Complex(1.4, -3.2);
		Complex res1 = c2.power(1);
		Complex res2 = c2.power(2);
		Complex res3 = c2.power(3);
		
		assertEquals(1.4, res1.getRe(), 0.01);
		assertEquals(-3.2, res1.getIm(), 0.01);
		assertEquals(-8.28, res2.getRe(), 0.01);
		assertEquals(-8.96, res2.getIm(), 0.01);
		assertEquals(-40.264, res3.getRe(), 0.001);
		assertEquals(13.952, res3.getIm(), 0.001);
	}
	
	@Test
	public void testPowerShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> Complex.ONE.power(-1));
	}
	
	@Test
	public void testRoot() {
		Complex c1 = new Complex(1.5, 1.5);
		List<Complex> res = c1.root(3);
		
		assertEquals(1.2411, res.get(0).getRe(), 0.0001);
		assertEquals(0.33256, res.get(0).getIm(), 0.0001);
		
		assertEquals(-0.90856, res.get(1).getRe(), 0.0001);
		assertEquals(0.90856, res.get(1).getIm(), 0.0001);
		
		assertEquals(-0.33256, res.get(2).getRe(), 0.0001);
		assertEquals(-1.2411, res.get(2).getIm(), 0.0001);
	}
	
	@Test
	public void testRootShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> Complex.ONE.root(-5));
		assertThrows(IllegalArgumentException.class, () -> Complex.IM.root(0));
	}
	
	@Test
	public void testToString() {
		Complex c1 = new Complex(2, 3);
		assertEquals("2.0+i3.0", c1.toString());
		
		Complex c2 = new Complex(2.5, -3);
		assertEquals("2.5-i3.0", c2.toString());
		
		Complex c3 = new Complex(2, 3);
		assertEquals("2.0+i3.0", c3.toString());
		
		Complex c4 = new Complex(-2, -3);
		assertEquals("-2.0-i3.0", c4.toString());
		
		Complex c5 = new Complex(-2, 3);
		assertEquals("-2.0+i3.0", c5.toString());
		
		Complex c6 = new Complex(0, 3);
		assertEquals("0.0+i3.0", c6.toString());

		Complex c7 = new Complex(0, -3);
		assertEquals("0.0-i3.0", c7.toString());
		
		Complex c8 = new Complex(0, -1);
		assertEquals("0.0-i1.0", c8.toString());
		
		Complex c9 = new Complex(0, 1);
		assertEquals("0.0+i1.0", c9.toString());
		
		Complex c10 = new Complex(2, 0);
		assertEquals("2.0+i0.0", c10.toString());
	}
	
}
