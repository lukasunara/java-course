package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Instances of this class represent a simple lexic analizator which groups
 * Characters into Tokens.
 * 
 * @author lukasunara
 *
 */
public class QueryLexer {

	/** Input text **/
	private char[] data;
	
	/** Current token **/
	private Token token;

	/** Index of the first unused Character **/
	private int currentIndex;
	
	/**
	 * Constructor recieves a text and tokenizes it.
	 * 
	 * @param text String which is used to create tokens
	 * @throws NullPointerException when the given text is null
	 **/
	public QueryLexer(String text) {
		super();
		if(text == null) throw new NullPointerException("Text cannot be null!");
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = new Token(null, null);
	}

	/**
	 * Generates and returns next Token from data.
	 * 
	 * @return next Token from data
	 * @throws LexerException when there is a mistake in the given text input
	 */
	public Token nextToken() {
		if(token.getType() == TokenType.EOF)
			throw new QueryLexerException("The data array is empty!");
		
		if(checkIfEndOfInputText()) return token;
		skipAllWhitespaces(); // skip all leading whitespaces
		if(checkIfEndOfInputText()) return token;
		
		findNextToken();
		
		return token;
	}

	private void findNextToken() {
		// check if attribute
		switch(data[currentIndex]) {
			case 'j': workForJmbag(); return;
			case 'f': workForFirstName(); return;
			case 'l': workForLastName(); return;
			case '"': workForStringLiteral(); return;
		}
		// check if operator
		if(checkOperator()) return;
		
		// check if AND
		if(checkIfAND()) return;
		
		token = new Token(TokenType.EOF, null); // should never happen
	}

	/** 
	 * Returns current Token and can be called multiple times.
	 * It does not start generating next Token.
	 * 
	 * @return the current token
	 */
	public Token getToken() {
		return token;
	}
	
	/** Checks if it is the end of the given text input **/
	private boolean checkIfEndOfInputText() {
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return true;
		}
		return false;
	}

	/** Skips all whitespaces until it reaches a letter, a digit or a symbol **/
	private void skipAllWhitespaces() {
		while("\r\n\t ".indexOf(data[currentIndex]) != -1) {
			currentIndex++;
			if(currentIndex == data.length) return;
		}
	}
	
	/** Continues working when there was a jmbag. **/
	private void workForJmbag() {
		if(currentIndex+5 > data.length)
			throw new QueryLexerException("Wrong attribute name!");
		
		String variable = "";
		for(int i = 0; i < 5; i++) {
			variable += data[currentIndex++];
		}
		if(!variable.equals("jmbag"))
			throw new QueryLexerException("Wrong attribute name!");
		
		token = new Token(TokenType.JMBAG, variable);
	}
	
	/** Continues working when there was a firstName. **/
	private void workForFirstName() {
		if(currentIndex+4 > data.length)
			throw new QueryLexerException("Wrong attribute name!");
		
		String variable = "";
		for(int i = 0; i < 9; i++) {
			variable += data[currentIndex++];
		}
		if(!variable.equals("firstName"))
			throw new QueryLexerException("Wrong attribute name!");
		
		token = new Token(TokenType.FIRST_NAME, variable);
	}
	
	/** Continues working when there was a lastName. **/
	private void workForLastName() {
		if(currentIndex+4 > data.length)
			throw new QueryLexerException("Wrong attribute name!");
		
		String variable = "";
		for(int i = 0; i < 8; i++) {
			variable += data[currentIndex++];
		}
		if(!variable.equals("lastName"))
			throw new QueryLexerException("Wrong attribute name!");
		
		token = new Token(TokenType.LAST_NAME, variable);
	}

	/** Checks if the operator is valid. **/
	private boolean checkOperator() {
		String operator = "";
		do {
			if(checkIfEndOfInputText())
				throw new QueryLexerException("Unexpected operator!");
			if(Character.isDigit(data[currentIndex])) return false;
			if(Character.isAlphabetic(data[currentIndex])
					&& data[currentIndex] != 'L'
					&& data[currentIndex] != 'I'
					&& data[currentIndex] != 'K'
					&& data[currentIndex] != 'E'
					) {
				return false;
			}
			operator += data[currentIndex++];
		} while(data[currentIndex] != '\"'
				&& " \t\n\r".indexOf(data[currentIndex]) == -1);
		
		token = new Token(TokenType.OPERATOR, operator);
		
		return true;
	}
	
	/** Continues working when there was a String literal. **/
	private void workForStringLiteral() {
		if(currentIndex+1 > data.length)
			throw new QueryLexerException("Unexpected string literal!");
		
		String literal = "";
		while(data[++currentIndex] != '"') {
			literal += data[currentIndex];
			if(checkIfEndOfInputText())
				throw new QueryLexerException("Unexpected string literal!");
		}
		currentIndex++;
		
		token = new Token(TokenType.STRING, literal);
	}
	
	/** Checks if the next token is AND. **/
	private boolean checkIfAND() {
		if(currentIndex+4 > data.length) return false;
		
		String operatorAND = "";
		for(int i = 0; i < 3; i++) {
			operatorAND += data[currentIndex++];
		}
		if(!operatorAND.equalsIgnoreCase("AND"))
			throw new QueryLexerException("Expected AND!");
		
		token = new Token(TokenType.AND, operatorAND);
		
		return true;
	}
	
}
