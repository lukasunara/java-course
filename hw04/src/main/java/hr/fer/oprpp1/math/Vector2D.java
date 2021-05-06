package hr.fer.oprpp1.math;

/**
 * Instances of this class represent a 2D vector with Double components.
 * 
 * @author lukasunara
 *
 */
public class Vector2D {

	/** Represents a unit Vector **/
	public static Vector2D UNIT_VECTOR = new Vector2D(1, 0);
	
	/** x component of this 2D vector **/
	private double x;
	
	/** y component of this 2D vector **/
	private double y;
	
	/**
	 * Constructor creates a new 2D vector with the given x and y components.
	 * 
	 * @param x Double value of x component
	 * @param y Double value of y component
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/** Public getter for x component. **/
	public double getX() {
		return x;
	}
	
	/** Public getter for y component. **/
	public double getY() {
		return y;
	}
	
	/**
	 * Adds another Vector2D to this vector.
	 * 
	 * @param offset the vector to be added
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Adds another Vector2D to this vector without changing it.
	 * 
	 * @param offset the vector to be added
	 * @return a new Vector2D which is the result of adding offset to this vector
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x+offset.x, this.y+offset.y);
	}
	
	/**
	 * Rotates this Vector2D for the given angle.
	 * 
	 * @param angle Double value for which this vector should be rotated
	 */
	public void rotate(double angle) {
		double newX = x*Math.cos(angle) - y*Math.sin(angle);
		double newY = x*Math.sin(angle) + y*Math.cos(angle);
		x = newX;
		y = newY;
	}
	
	/**
	 * Rotates this Vector2D for the given angle without changing it.
	 * 
	 * @param angle Double value for which this vector should be rotated
	 * @return a new Vector2D which is the result of rotating this vector for given angle
	 */
	public Vector2D rotated(double angle) {
		double newX = x*Math.cos(angle) - y*Math.sin(angle);
		double newY = x*Math.sin(angle) + y*Math.cos(angle);
		
		return new Vector2D(newX, newY);
	}
	
	/**
	 * Scales this Vector2D with the given scaler.
	 * 
	 * @param scaler Double value for which this vector should be scaled
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Scales this Vector2D with the given scaler without changing it.
	 * 
	 * @param scaler Double value for which this vector should be scaled
	 * @return a new Vector2D which is the result of scaling this vector for given scaler
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x*scaler, this.y*scaler);
	}
	
	/** 
	 * Creates and returns new copy of this vector.
	 * 
	 * @return new copy of this Vector2D
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
	
}
