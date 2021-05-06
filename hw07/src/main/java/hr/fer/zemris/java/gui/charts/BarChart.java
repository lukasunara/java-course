package hr.fer.zemris.java.gui.charts;

import java.util.Collections;
import java.util.List;

/**
 * Instances of this class represent a bar chart.
 * 
 * @author lukasunara
 *
 */
public class BarChart {

	/** List used for storing {@link XYValue} coordinates (read-only) **/
	private List<XYValue> coordinatesList;
	
	/** String represents the text displayed next to x-axis (read-only) **/
	private String xText;
	
	/** String represents the text displayed next to y-axis (read-only) **/
	private String yText;
	
	/** Represents the minimum y-coordinate (read-only) **/
	private int yMin;

	/** Represents the maximum y-coordinate (read-only) **/
	private int yMax;
	
	/** Represents the step between each y value on chart (read-only) **/
	private int yStep;

	/**
	 * Constructor initializes the {@link BarChartComponent} by the given values.
	 * 
	 * @param coordinates List List used for storing {@link XYValue} coordinates
	 * @param xText String represents the text displayed next to x-axis
	 * @param yText String represents the text displayed next to y-axis
	 * @param yMin the minimum y-coordinate
	 * @param yMax the maximum y-coordinate
	 * @param yStep the step between each y value on chart
	 */
	public BarChart(List<XYValue> coordinatesList, String xText, String yText, int yMin, int yMax, int yStep) {
		super();
		if(yMin < 0) throw new IllegalArgumentException("Minimum y-coordinate cannot be negative!");
		if(yMax <= yMin) throw new IllegalArgumentException("Maximum y-coordinate must be greater than minimum y-coordinate!");

		for(XYValue xyValue : coordinatesList) {
			if(xyValue.getY() < yMin)
				throw new IllegalArgumentException("All y-coordinates must be equal or greater than the minimum y-coordinate!");
		}
		// number of whole steps between yMin and yMax must be a integer value
		while((yMax-yMin) % yStep != 0) yMax++;

		this.yMin = yMin;
		this.yMax = yMax;
		this.yStep = yStep;
		this.xText = xText;
		this.yText = yText;
		this.coordinatesList = coordinatesList;
	}

	/**
	 * Public getter method for list of coordinates.
	 * 
	 * @return the coordinatesList (read-only)
	 */
	public List<XYValue> getCoordinatesList() {
		return Collections.unmodifiableList(coordinatesList);
	}

	/**
	 * Public getter method for xText.
	 * 
	 * @return the xText
	 */
	public String getxText() {
		return xText;
	}

	/**
	 * Public getter method for yText.
	 * 
	 * @return the yText
	 */
	public String getyText() {
		return yText;
	}

	/**
	 * Public getter method for yMin.
	 * 
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Public getter method for yMax.
	 * 
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Public getter method for yStep.
	 * 
	 * @return the yStep
	 */
	public int getyStep() {
		return yStep;
	}
	
}
