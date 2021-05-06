package hr.fer.oprpp1.hw04.db;

/**
 * Strategy IComparisonOperator defines one concrete strategy for each comparison
 * operator.
 * 
 * On the left side of a comparison operator a field name is required and on
 * the right side string literal.
 * 
 * @author lukasunara
 *
 */
@FunctionalInterface
public interface IComparisonOperator {

	/**
	 * Defines the strategy for an operator.
	 * 
	 * @param value1 String literal
	 * @param value2 String literal
	 * @return true if satisfied, otherwise false
	 */
	public boolean satisfied(String value1, String value2);
	
}
