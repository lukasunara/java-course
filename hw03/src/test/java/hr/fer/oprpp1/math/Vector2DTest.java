package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Vector2DTest {

	@Test
    public void testConstructor() {
		Vector2D vector = new Vector2D(3.3, 8.44);

        assertEquals(3.3, vector.getX());
        assertEquals(8.44, vector.getY());
        
    }

    @Test
    public void testAdd() {
    	Vector2D vector = new Vector2D(2.2, 5);
        
        vector.add(new Vector2D(3.3, 8.44));

        assertTrue(5.5 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(13.44 - Math.abs(vector.getY()) < 1E-8);
    }

    @Test
    public void testAdded() {
    	Vector2D vector = new Vector2D(2.2, 5);
        
    	Vector2D newVector = vector.added(new Vector2D(3.3, 8.44));

        assertTrue(2.2 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(5 - Math.abs(vector.getY()) < 1E-8);

        assertTrue(5.5 - Math.abs(newVector.getX()) < 1E-8);
        assertTrue(13.44 - Math.abs(newVector.getY()) < 1E-8);
    }

    @Test
    public void testRotate() {
    	Vector2D vector1 = new Vector2D(2.2, 0);
        vector1.rotate(Math.PI / 2);

        assertTrue(0 - Math.abs(vector1.getX()) < 1E-8);
        assertTrue(0 - Math.abs(vector1.getY()) < 1E-8);
        
        Vector2D vector2 = new Vector2D(2.2, 2.2);
        vector2.rotate(Math.PI);

        assertTrue(2.2 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(2.2 - Math.abs(vector2.getY()) < 1E-8);
    }

    @Test
    public void testRotated() {
    	Vector2D vector = new Vector2D(2.2, 2.2);
    	
    	Vector2D newVector = vector.rotated(Math.PI);

        assertNotSame(newVector, vector);
        assertTrue(2.2 - Math.abs(newVector.getX()) < 1E-8);
        assertTrue(2.2 - Math.abs(newVector.getY()) < 1E-8);
    }

    @Test
    public void testCopy() {
    	Vector2D vector = new Vector2D(3.3, 8.44);
    	
    	Vector2D newVector = vector.copy();

    	newVector.scale(2);
    	
        assertNotSame(newVector, vector);
    }

    @Test
    public void testScale() {
    	Vector2D vector = new Vector2D(3.3, 8.44);
        
        vector.scale(2);

        assertTrue(6.6 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(16.88 - Math.abs(vector.getY()) < 1E-8);
    }

    @Test
    public void testScaled() {
    	Vector2D vector = new Vector2D(3.3, 8.44);
    	
    	Vector2D newVector = vector.scaled(2);

        assertNotSame(newVector, vector);
        assertTrue(6.6 - Math.abs(newVector.getX()) < 1E-8);
        assertTrue(16.88 - Math.abs(newVector.getY()) < 1E-8);
    }
    
}
