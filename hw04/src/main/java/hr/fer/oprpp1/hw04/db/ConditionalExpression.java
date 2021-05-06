package hr.fer.oprpp1.hw04.db;

/**
 * Instance of this class contains a reference to IFieldValueGetter strategy, a reference to
 * string literal and a reference to IComparisonOperator strategy.
 * 
 * @author lukasunara
 *
 */
public class ConditionalExpression {

	/** Represents a comparison operator **/
	private IComparisonOperator comparisonOperator;
	
	/** Represents a string literal **/
	private String stringLiteral;
	
	/** Represents a field getter **/
	private IFieldValueGetter fieldValueGetter;
	
	/**
	 * Constructor recieves an {@link IFieldValueGetter}, a string literal and an {@link IComparisonOperator}.
	 * 
	 * @param fieldGetter instance of {@link IFieldValueGetter}
	 * @param stringLiteral String literal
	 * @param comparisonOperator instance od {@link IComparisonOperator}
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		super();
		this.fieldValueGetter = fieldValueGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Public getter method for this comparison operator.
	 * 
	 * @return the comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Public getter for this string literal.
	 * 
	 * @return the stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Public getter method for this field getter.
	 * 
	 * @return the fieldGetter
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}
	
}
