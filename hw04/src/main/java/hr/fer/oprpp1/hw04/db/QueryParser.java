package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.lexer.QueryLexer;
import hr.fer.oprpp1.hw04.db.lexer.Token;
import hr.fer.oprpp1.hw04.db.lexer.TokenType;

/**
 * Instances of this class represent a parser of query statement.
 * 
 * @author lukasunara
 *
 */
public class QueryParser {

	/** Query lexer used to create tokens **/
	private QueryLexer lexer;
	
	/** List which contains all expressions from query command **/
	private LinkedList<ConditionalExpression> expressions;
	
	/**
	 * Recieves everything after the "query" keyword and creates a new query parser.
	 * 
	 * @param query the String to be parsed
	 * @throws QueryParserException
	 */
	public QueryParser(String query) {
		super();
		lexer = new QueryLexer(query);
		expressions = new LinkedList<>();
		this.parse();
	}
	
	/**
	 * Method must return true if query was in the form of jmbag="xxx" (i.e. it must
	 * have only one comparison, on attribute jmbag, and operator must be equals).
	 * 
	 * @return returns boolean value (true if it is a direct query, false otherwise)
	 */
	public boolean isDirectQuery() {
		if(expressions.size() != 1) return false;
		if(expressions.getFirst().getComparisonOperator() != ComparisonOperators.EQUALS)
			return false;
		
		return true;
	}
	
	/**
	 * Method must return the string "xxx" which was given in equality comparison
	 * in direct query.
	 * 
	 * @return String which represents the "xxx" in direct query statement
	 * @throws IllegalStateException if the query was not a direct one
	 */
	public String getQueriedJMBAG() {
		if(!this.isDirectQuery())
			throw new IllegalStateException("This query is not a direct query!");
		
		return expressions.getFirst().getStringLiteral();
	}
	
	/**
	 * For all queries, this method must return a list of conditional expressions
	 * from query.
	 * 
	 * @return a List<ConditionalExpression> of conditional expressions from query
	 */
	public List<ConditionalExpression> getQuery() {
		return this.expressions;
	}
	
	/**
	 * Method starts the parsing of given text.
	 * 
	 * @throws QueryParserException if any Exception occurs
	 */
	private void parse() {
		try {
			this.startParsing();			
		} catch(Exception ex) {
			// System.out.println(ex.getMessage());
			throw new QueryParserException(ex.getMessage());
		}
	}
	
	/** Start parsing. **/
	private void startParsing() {
		while(true) {
			Token token = lexer.nextToken();
			
			if(token.getType() == TokenType.EOF) break; // end of parsing
			if(token.getType() == TokenType.AND) continue; // skip to next token
			
			// first token is a variable
			switch(token.getType()) {
				case JMBAG: {
					createConditionalExpression(FieldValueGetters.JMBAG);
					break;
				}
				case FIRST_NAME: {
					createConditionalExpression(FieldValueGetters.FIRST_NAME);
					break;
				}
				case LAST_NAME: {
					createConditionalExpression(FieldValueGetters.LAST_NAME);
					break;
				}
				default: throw new QueryParserException("Invalid attribute!");
			}
		}
	}
	
	/** Creates a new {@link ConditionalExpression}. **/
	private void createConditionalExpression(IFieldValueGetter attribute) {
		// second token has to be a operator
		Token operator = lexer.nextToken();
		if(operator.getType() != TokenType.OPERATOR)
			throw new QueryParserException("Expected an operator!");
		
		IComparisonOperator compOperator = checkOperator(operator.getValue().toString());
		
		// third token is a string literal
		Token literal = lexer.nextToken();
		if(literal.getType() != TokenType.STRING)
			throw new QueryParserException("Expected a string literal!");
		
		ConditionalExpression expression = new ConditionalExpression(
			attribute,
			literal.getValue().toString(),
			compOperator
		);
		expressions.add(expression);
	}
	
	/** Checks which operator is used for comparison. **/
	private IComparisonOperator checkOperator(String operator) {
		switch(operator) {
			case "<": return ComparisonOperators.LESS;
			case ">": return ComparisonOperators.GREATER;
			case "=": return ComparisonOperators.EQUALS;
			case "<=": return ComparisonOperators.LESS_OR_EQUALS;
			case ">=": return ComparisonOperators.GREATER_OR_EQUALS;
			case "!=": return ComparisonOperators.NOT_EQUALS;
			case "LIKE": return ComparisonOperators.LIKE;
			default: throw new QueryParserException("Invalid comparison operator!");
		}
	}

}
