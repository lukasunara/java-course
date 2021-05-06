package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Instances of this class represent a positions or restrictions used by {@link CalcLayout}.
 * 
 * @author lukasunara
 *
 */
public class RCPosition {

	/** Represents the row, read-only value **/
	private int row;
	
	/** Represents the column, read-only value **/
	private int column;

	/**
	 * Constructor creates a new instance of {@link RCPosition} for the given row and column.
	 * 
	 * @param row int value which represents a row
	 * @param column int value which represents a column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Public getter method for read-only value row.
	 * 
	 * @return the int value of row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Public getter method for read-only value column.
	 * 
	 * @return the int value of column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Parses the given String and creates a new RCPosition.
	 * 
	 * @param text String to parse, represents a RCPosition
	 * @return the created RCPosition
	 * @throws IllegalArgumentException if given text is not in format: "%d,%d" (there can be whitespace after ",")
	 */
	public static RCPosition parse(String text) {
		if(text == null || text.isBlank())
			throw new IllegalArgumentException("Text used for parsing cannot be null or blank!");
		
		String[] args = text.strip().split(",");
		if(args.length != 2)
			throw new IllegalArgumentException("Method parse expects 2 integer arguments!");
		
		return new RCPosition(Integer.parseInt(args[0].strip()), Integer.parseInt(args[1].strip()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		
		return column == other.column && row == other.row;
	}
	
	
}
