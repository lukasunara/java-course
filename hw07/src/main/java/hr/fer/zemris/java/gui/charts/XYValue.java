package hr.fer.zemris.java.gui.charts;

/**
 * Instances of this class represent coordinates on
 * the screen (x, y) => in pixels.
 * 
 * @author lukasunara
 *
 */
public class XYValue {

	/** Represents the x-coordinate (read-only) **/
	private int x;
	
	/** Represents the y-coordinate (read-only) **/
	private int y;
	
	/** Default constructor sets the coordinates for (0, 0). **/
	public XYValue() {
		this(0, 0);
	}
	
	/**
	 * Constructor sets the coordinates by the given parameters.
	 * 
	 * @param x int value represents the x-coordinate
	 * @param y int value represents the y-coordinate
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Public getter method for x-coordinate.
	 * 
	 * @return the x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Public getter method for y-coordinate.
	 * 
	 * @return the y-coordinate
	 */
	public int getY() {
		return y;
	}
	
}
