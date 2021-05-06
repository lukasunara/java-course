package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class represent a calculator layout.
 * 
 * @author lukasunara
 *
 */
public class CalcLayout implements LayoutManager2 {

	/** Number of rows this layout contains **/
	private final static int ROWS = 5;
	
	/** Number of columns this layout contains **/
	private final static int COLUMNS = 7;
	
	/** Default value of gap between components **/
	private final static int DEFAULT_GAP = 0;
	
	/** Represents the row of the biggest component in layout **/
	private final static int BIG_COMPONENT_ROW = 1;
	
	/** Represents the start column of the biggest component **/
	private final static int BIG_COMPONENT_COLUMN_START = 1;
	
	/** Represents the end column of the biggest component **/
	private final static int BIG_COMPONENT_COLUMN_END = 5;
	
	/** Used for calculating components height **/
	private final static int[][] ROW_MODULUS = {
			{0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1},
			{0, 1, 0, 1, 0},
			{1, 0, 1, 0, 1},
			{1, 1, 0, 1, 1},
	};
	
	/** Used for calculating components width **/
	private final static int[][] COLUMN_MODULUS = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0},
			{0, 0, 1, 0, 1, 0, 0},
			{0, 1, 0, 1, 0, 1, 0},
			{1, 0, 1, 0, 1, 0, 1},
			{1, 1, 0, 1, 0, 1, 1},
			{1, 1, 1, 0, 1, 1, 1},
	};
	
	/** Represents the gap between rows and columns (in pixels) **/
	private int gap;
	
	/** {@link Map} used for storing {@link Component}s of parent {@link Container} **/
	private Map<Component, RCPosition> positions = new HashMap<>();
		
	/**
	 * Constructor creates a new CalcLayout and sets the gap to the given int value.
	 * 
	 * @param gap int value which represents the gap between rows and columns
	 */
	public CalcLayout(int gap) {
		super();
		this.gap = gap;
	}
	
	/** Default constructor creates a new CalcLayout and sets the gap to the default value. **/
	public CalcLayout() {
		this(DEFAULT_GAP);
	}
	
	/**
	 * @throws UnsupportedOperationException always, because it is not defined
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("CalcLayout does not support addLayoutComponent method!");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		positions.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return countSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return countSize(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return countSize(target, Component::getMaximumSize);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets parentInsets = parent.getInsets();

		// calculate width and height which this layout can use for it's components
		int width = Math.max(0, parent.getWidth() - parentInsets.left - parentInsets.right - gap*(COLUMNS-1));
		int height = Math.max(0, parent.getHeight() - parentInsets.bottom - parentInsets.top - gap*(ROWS-1));
		
		int rowMod = (height % ROWS) / (1 % ROWS);
		int columnMod = (width % COLUMNS) / (1 % COLUMNS);
		
		for(Component comp : parent.getComponents()) {
			// skip all components this layoutManager does not know about
			if(positions.containsKey(comp)) {
				compSetBounds(comp, parentInsets.left, parentInsets.top, rowMod, columnMod,
						(double)height / ROWS, (double)width / COLUMNS
				);
			}
		}
	}

	/** Sets bounds for the given component. **/
	private void compSetBounds(Component comp, int leftIns, int topIns, int rowMod, int columnMod, double compHeight, double compWidth) {
		RCPosition rcPosition = positions.get(comp);
		int row = rcPosition.getRow();
		int column = rcPosition.getColumn();

		double x = leftIns; // x can start after parent left inset
		double y = topIns;	// x can start after parent top inset
		
		if(row == BIG_COMPONENT_ROW && column == BIG_COMPONENT_COLUMN_START) {
			column = BIG_COMPONENT_COLUMN_END;	// important for calculating big component width
		}
		
		// calculate coordinates (x, y)
		for(int i = 0; i < column-1; i++) {
			x += compWidth + COLUMN_MODULUS[columnMod][i];
			if(i < COLUMNS-1) x += gap;
		}
		for(int j = 0; j < row-1; j++) {
			y += compHeight + ROW_MODULUS[rowMod][j];
			if(j < ROWS-1) y += gap;
		}
		// add (or not) extra pixel for uniform distribution
		double width = compWidth + COLUMN_MODULUS[columnMod][column-1];
		double height = compHeight + ROW_MODULUS[rowMod][row-1];
		
		// big component starts from (leftIns, topIns)
		if(row == BIG_COMPONENT_ROW && column == BIG_COMPONENT_COLUMN_END) {
			width = x + width - leftIns;
			x = leftIns;
		}
		comp.setBounds((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints == null)
			throw new NullPointerException("The object constraints mustn't be null!");
		
		RCPosition rcPosition = null;
		if(constraints instanceof String) {
			rcPosition = RCPosition.parse(String.valueOf(constraints));
		} else if(constraints instanceof RCPosition) {
			rcPosition = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Given constraint must be an instance of String or RCPosition!");
		}
		
		int row = rcPosition.getRow();
		int column = rcPosition.getColumn();
		
		if(row > ROWS || row < 1 || column > COLUMNS || column < 1)
			throw new CalcLayoutException("Invalid row or column in RCPosition constraint!");
		if(row == BIG_COMPONENT_ROW && (column > BIG_COMPONENT_COLUMN_START && column < BIG_COMPONENT_COLUMN_END))
			throw new CalcLayoutException("Invalid column for row 1!");
		
		if(positions.containsValue(rcPosition))
			throw new CalcLayoutException("This RCPosition is already taken!");
		
		positions.put(comp, rcPosition);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
	}

	private Dimension countSize(Container parent, SizeCounter counter) {
		// Find the starting and ending row and column (don't need this because every
		// layout must have ROWS and COLUMNS as specified in final constants
//		int startRow = ROWS, endRow = 1;
//		int startColumn = COLUMNS, endColumn = 1;
		
		double maxWidth = 0.;
		double maxHeight= 0.;
		for(Component comp : parent.getComponents()) {
			RCPosition rcPos = positions.get(comp);
			int row = rcPos.getRow();
			int column = rcPos.getColumn();

//			if(row == 1 && column == 1) column = BIG_COMPONENT_COLUMN_END;
//			if(row < startRow) startRow = row;
//			if(row > endRow) endRow = row;
//			if(column < startColumn) startColumn = column;
//			if(column > endColumn) endColumn = column;
			
			if(!positions.containsKey(comp)) continue; // skip all components this layoutManager does not know about
			Dimension dim = counter.count(comp);
			
			double compWidth = dim.getWidth();
			double compHeight = dim.getHeight();
			
			if(row == BIG_COMPONENT_ROW && column == BIG_COMPONENT_COLUMN_START) {
				int columnsBetween = BIG_COMPONENT_COLUMN_END - BIG_COMPONENT_COLUMN_START;
				compWidth = (compWidth - gap*columnsBetween) / (columnsBetween+1);
			}
			maxWidth = Math.max(compWidth, maxWidth);
			maxHeight = Math.max(compHeight, maxHeight);
		}
		Insets parentInsets = parent.getInsets();
		
		double width = maxWidth*COLUMNS + gap*(COLUMNS-1) + parentInsets.left + parentInsets.right;
		double height= maxHeight*ROWS + gap*(ROWS-1) + parentInsets.top + parentInsets.bottom;
		
		return new Dimension((int)width, (int)height);
	}
	
}
