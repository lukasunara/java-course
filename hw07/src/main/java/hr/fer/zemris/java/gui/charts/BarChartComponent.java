package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Instances of this class represent a {@link JComponent} used
 * for generating a bar chart.
 * 
 * @author lukasunara
 *
 */
public class BarChartComponent extends JComponent {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/** Margin on all sides **/
	private final int MARGIN = 15;
	
	/** gap between text and numbers **/
	private final int GAP_TEXT = 10;
	
	/** gap between numbers and dashes **/
	private final int GAP_NUMBER = 2;
	
	/** Dash width in pixels **/
	private final int DASH_WIDTH = 4;
	
	/** Arrow size **/
	private final int ARROW_SIZE = 8;
	
	/** {@link BarChart} saves info about bar **/
	private BarChart barChart;

	/** Constructor saves the {@link BarChart}. **/
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		FontMetrics fm = g2d.getFontMetrics();
		Dimension dim = getSize();
		Insets insets = getInsets();
		
		double maxNumberLength = fm.stringWidth(String.valueOf(barChart.getyMax())); // length of the biggest numberon y-axis
		double yAxisStartNumX = MARGIN + fm.getHeight() + GAP_TEXT; // x-start of numbers -> y-Axis
		double xAxisStartNumY = dim.getHeight() - insets.bottom - MARGIN - fm.getHeight() - GAP_TEXT; // y-start of numbers -> x-Axis

		double yAxisStart = insets.top + MARGIN + ARROW_SIZE; // start of y-Axis (starts from top)
		double yAxisEnd = xAxisStartNumY - fm.getHeight() - GAP_NUMBER; // end of y-Axis
		double yAxisHeight = yAxisEnd - yAxisStart - 2*DASH_WIDTH; // height of y-Axis
		double yBetweenTwoLines = yAxisHeight / ((barChart.getyMax()-barChart.getyMin()) / barChart.getyStep());
		
		double xAxisStart = yAxisStartNumX + maxNumberLength + GAP_NUMBER; // start of x-Axis (starts from left)
		double xAxisEnd = dim.getWidth() - insets.right - ARROW_SIZE - MARGIN; // end of x-Axis
		double xAxisWidth = xAxisEnd - xAxisStart - 2*DASH_WIDTH; // width of x-Axis
		double xBetweenTwoLines = xAxisWidth / barChart.getCoordinatesList().size();
		
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		drawYText(g2d, insets, fm, yAxisStart, yAxisHeight); // text next to y-axis
		
		at.rotate(Math.PI /2);
		g2d.setTransform(at);
		drawXText(g2d, insets, dim, fm, xAxisStart, xAxisWidth); // text next to x-axis
		
		drawYAxisNumbers(g2d, fm, yAxisStartNumX, yAxisEnd, yBetweenTwoLines, xAxisStart, xAxisEnd, maxNumberLength);
		drawXAxisNumbers(g2d, fm, xAxisStartNumY, xAxisStart, xBetweenTwoLines, yAxisStart, yAxisEnd);
		
		g2d.setColor(Color.ORANGE);
		drawCharts(g2d, xAxisStart, yAxisEnd, xBetweenTwoLines, yBetweenTwoLines);
		g2d.setColor(Color.BLACK);
		
		double x1 = xAxisStart;
		int[] xPoints1 = new int[] { (int)x1, (int)(x1 + ARROW_SIZE), (int)(x1 + ARROW_SIZE/2) };
		int[] yPoints1 = new int[] { (int)yAxisStart, (int)yAxisStart, (int)(yAxisStart - ARROW_SIZE) };
		g2d.fillPolygon(xPoints1, yPoints1, 3);
		
		double y2 = yAxisEnd;
		int[] yPoints2 = new int[] { (int)y2, (int)(y2 - ARROW_SIZE), (int)(y2 - ARROW_SIZE/2) };
		int[] xPoints2 = new int[] { (int)xAxisEnd, (int)xAxisEnd, (int)(xAxisEnd + ARROW_SIZE) };
		g2d.fillPolygon(xPoints2, yPoints2, 3);
	}
	
	/** Draws text next to y-axis. **/
	private void drawYText(Graphics2D g2d, Insets insets, FontMetrics fm, double yAxisStart, double yAxisHeight) {
		int stringWidth = fm.stringWidth(barChart.getyText());
		
		double x = insets.left + MARGIN + fm.getAscent();
		double y = -(yAxisStart + DASH_WIDTH + (yAxisHeight - stringWidth)/2 + stringWidth);
		
		g2d.drawString(barChart.getyText(), (int)y, (int)x);
	}
	
	/** Draws text underneath to x-axis. **/
	private void drawXText(Graphics2D g2d, Insets insets, Dimension dim, FontMetrics fm, double xAxisStart,double xAxisWidth) {
		int stringWidth = fm.stringWidth(barChart.getxText());
		
		double x = xAxisStart + DASH_WIDTH + (xAxisWidth - stringWidth)/2;
		double y = dim.height - insets.bottom - MARGIN - fm.getDescent();

		g2d.drawString(barChart.getxText(), (int)x, (int)y);
	}
	
	/** Draws y-axis numbers and x-axis lines. **/
	private void drawYAxisNumbers(
			Graphics2D g2d, FontMetrics fm, double yAxisStartNumX, double yAxisEnd,
			double yBetweenTwoLines, double xAxisStart, double xAxisEnd, double maxNumberLength
	) {
		double y = yAxisEnd - DASH_WIDTH; // start of number 0
		
		for(int i = barChart.getyMin(); i <= barChart.getyMax(); i += barChart.getyStep()) {
			double x = yAxisStartNumX + (maxNumberLength - fm.stringWidth(String.valueOf(i))); // x-start of number

			g2d.drawString(String.valueOf(i), (float)x, (float)y + fm.getAscent()/2 - fm.getDescent()/2);
			g2d.drawLine((int)xAxisStart, (int)y, (int)xAxisEnd, (int)y);
			
			y -= yBetweenTwoLines;
		}
	}
	
	/** Draws x-axis numbers and y-axis lines. **/
	private void drawXAxisNumbers(
			Graphics2D g2d, FontMetrics fm, double xAxisStartNumY, double xAxisStart,
			double xBetweenTwoLines, double yAxisStart, double yAxisEnd
	) {
		double x = xAxisStart + DASH_WIDTH;
		List<XYValue> coordinatesList = barChart.getCoordinatesList();
		
		for(int i = 1; i <= coordinatesList.size(); i++) {
			g2d.drawLine((int)x, (int)yAxisStart, (int)x, (int)yAxisEnd);
			
			String value = String.valueOf(coordinatesList.get(i-1).getX());
			x += xBetweenTwoLines/2 - fm.stringWidth(String.valueOf(value))/2; // start of number
			g2d.drawString(String.valueOf(value), (float)x, (float)xAxisStartNumY);
			
			x += fm.stringWidth(String.valueOf(value))/2 + xBetweenTwoLines/2;
		}
		g2d.drawLine((int)x, (int)yAxisStart, (int)x, (int)yAxisEnd);
	}
	
	/** Fills the chart. **/
	private void drawCharts(Graphics2D g2d, double xAxisStart, double yAxisEnd, double xBetweenLines, double yBetweenLines) {
		double x = xAxisStart + DASH_WIDTH + 1;
		double y = yAxisEnd - DASH_WIDTH + 1;
		
		for(XYValue xyValue : barChart.getCoordinatesList()) {
			double width = xBetweenLines * (xyValue.getX() - 1);
			double height = yBetweenLines * ((xyValue.getY() - barChart.getyMin()) / barChart.getyStep());
			
			g2d.fill3DRect((int)(x + width), (int)(y - height), (int)xBetweenLines, (int)height, true);
		}
	}
	
}
