package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

/**
 * Instances of this class remember the current position of the Turtle.
 * 
 * @author lukasunara
 *
 */
public class TurtleState {

	/** A radius Vector which contains current position of the Turtle **/
	private Vector2D currentPosition;
	
	/** A unit length Vector which contains the direction in which the Turtle is looking **/
	private Vector2D direction;
	
	/** Color with which the Turtle is drawing **/
	private Color color;
	
	/** Double value which represents current effective shift length **/
	private double shift;
	
	/**
	 * Constructor creates a new TurtleState with the given params.
	 * 
	 * @param currentPosition Vector2D which contains current position of the Turtle
	 * @param direction Vector which contains the direction in which the Turtle is looking
	 * @param color Color with which the Turtle is drawing
	 * @param length Double value which represents current effective shift length
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double shift) {
		super();
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.shift = shift;
	}
	
	/**
	 * Creates a new copy of this TurtleState.
	 * 
	 * @return new copy of this TurtleState
	 */
	public TurtleState copy() {
//		Zašto u novoj instanci pokazuje na jednake varijable???
//		return new TurtleState(currentPosition, direction, color, shift);
		return new TurtleState(currentPosition.copy(), direction.copy(), new Color(color.getRGB()), shift);
	}

	/**
	 * Public getter method for current position.
	 * 
	 * @return Vector2D which is the currentPosition of turtle
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Public setter method for current position.
	 * 
	 * @param currentPosition Vector2D which is the new current position
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	/**
	 * Public getter method for view direction of turtle.
	 * 
	 * @return Vector2D which is the direction of turtle
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Public setter method for view direction of turtle.
	 * 
	 * @param direction Vector2D which is the new direction of turtle
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}
	
	/**
	 * Public getter method for color in which this turtle is drawing.
	 * 
	 * @return Color in which this turtle is drawing
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Public setter method for color in which this turtle is drawing.
	 * 
	 * @param color new Color in which this turtle is drawing
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Public getter method for this turtle's current effective shift value.
	 * 
	 * @return double value which is this turtle's current shift value
	 */
	public double getShift() {
		return shift;
	}

	/**
	 * Public setter method for this turtle's current effective shift value.
	 * 
	 * @param shift double value which is new current shift value 
	 */
	public void setShift(double shift) {
		this.shift = shift;
	}
	
}
