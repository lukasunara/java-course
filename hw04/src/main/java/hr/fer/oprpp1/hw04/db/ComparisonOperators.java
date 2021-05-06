package hr.fer.oprpp1.hw04.db;

/**
 * Instances of this class support following seven comparison operators:
 * >, <, >=, <=, =, !=, LIKE (LIKE can contain a wildcard).
 * 
 * On the left side of a comparison operator a field name is required and on
 * the right side string literal. 
 * 
 * @author lukasunara
 *
 */
public class ComparisonOperators {

	/** value1 < value2 **/
	public static final IComparisonOperator LESS = (value1, value2) -> {
		return value1.compareTo(value2) < 0;
	};
	
	/** value1 <= value2 **/
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) <= 0;
	};
	
	/** value1 > value2 **/
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		return value1.compareTo(value2) > 0;
	};
	
	/** value1 >= value2 **/
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) >= 0;
	};
	
	/** value1 == value2 **/
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) == 0;
	};
	
	/** value1 != value2 **/
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) != 0;
	};
	
	/**
	 * String literal can contain a wildcard '*', but only once. It can be at the
	 * beginning, at the end or somewhere in the middle.
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		int counter = 0;
		for(char c : value2.toCharArray()) {
			if(c == '*') counter++;
		}
		if(counter > 1) System.out.println("More than one wildcard '\\*' !");

		String[] splitedValue2 = value2.split("\\*");
		
		if(splitedValue2.length == 1) return value1.startsWith(splitedValue2[0]);
		if(splitedValue2[0].equals("")) return value1.endsWith(splitedValue2[1]);
		
		return value1.startsWith(splitedValue2[0]) && value1.endsWith(splitedValue2[1]);
	};
	
}
