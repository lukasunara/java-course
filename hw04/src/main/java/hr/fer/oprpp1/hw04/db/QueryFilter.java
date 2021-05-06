package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Instances of this class implement {@link IFilter}. Their role is to filter the
 * given {@link QueryParser} by accepting (or not) {@link StudentRecord}.
 * 
 * @author lukasunara
 *
 */
public class QueryFilter implements IFilter {

	/** List which contains all expressions from query command **/
	LinkedList<ConditionalExpression> expressions;
	
	/**
	 * Constructor receives one argument: a list of {@link ConditionalExpression}
	 * objects.
	 * 
	 * @param list List of {@link ConditionalExpression} objects
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		super();
		this.expressions = (LinkedList<ConditionalExpression>)expressions;
	}
	
	/**
	 * Method accepts the given {@link StudentRecord} if all of query's expressions
	 * are satisfied.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression exp : expressions) {
			if(!exp.getComparisonOperator()
					.satisfied(
							exp.getFieldValueGetter().get(record),
							exp.getStringLiteral()	
					)
			) return false;
		}
		return true;
	}

}
