package hr.fer.oprpp1.hw02.prob1;

/**
 * Instances of this class represent a simple lexic analizator which groups
 * Characters into Tokens.
 * 
 * @author lukasunara
 *
 */
public class Lexer {
	
	/** Input text **/
	private char[] data;
	
	/** Current token **/
	private Token token;

	/** Index of the first unused Character **/
	private int currentIndex;
	
	/** State in which Lexer currently works **/
	private LexerState state;

	/**
	 * Constructor recieves a text and tokenizes it.
	 * 
	 * @param text String which is used to create tokens
	 * @throws NullPointerException when the given text is null
	 **/
	public Lexer(String text) {
		super();
		if(text == null) throw new NullPointerException("Text cannot be null!");
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = new Token(null, null);
		this.state = LexerState.BASIC;
	}

	/**
	 * Generates and returns next Token from data.
	 * 
	 * @return next Token from data
	 * @throws LexerException when there is a mistake in the given text input
	 */
	public Token nextToken() {
		if(token.getType() == TokenType.EOF)
			throw new LexerException("The data array is empty!");
		
		if(checkIfEndOfInputText()) return token;
		
		// skip all leading whitespaces
		skipAllWhitespaces();
		
		if(checkIfEndOfInputText()) return token;
		
		if(state == LexerState.BASIC) basicNextToken();
		else if(state == LexerState.EXTENDED) extendedNextToken();
		
		return token;
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
	
	/**
	 * Public setter method for state of Lexer.
	 * 
	 * @param state new state in which the Lexer is going to work from now on
	 * @throws NullPointerException if the given state is <code>null</code>
	 */
	public void setState(LexerState state) {
		if(state == null)
			throw new NullPointerException("State of Lexer cannot be null!");
		
		this.state = state;
	}
	
	/** Generating next tokens while in BASIC mode. **/
	private void basicNextToken() {
		if(data[currentIndex]=='#') {
//			state = LexerState.EXTENDED; => user should recognize and do this
			token = new Token(TokenType.SYMBOL, '#');
			currentIndex++;
			return;
		}
		
		String stringToken = "";
		TokenType stringType = null;
		// read the first character to define the type of the next token
		stringToken += checkIfBackslash();
		if(!stringToken.equals("")) {
			stringType = TokenType.WORD;
		} else {
			stringType = characterType(data[currentIndex]);
			// if character is a digit, letter or a symbol add to the stringToken
			if(stringType != null) {
				stringToken += data[currentIndex++];
			} else {
				/* this should never happen because null means a whitespace
				and we skipped all leading whitespaces */
				stringType = TokenType.EOF;
			}
		}
		
		switch(stringType) {
			case SYMBOL: token = new Token(stringType, stringToken.charAt(0)); break;
			case NUMBER: workForNumber(stringType, stringToken); break;
			case WORD: workForWord(stringType, stringToken);  break;
			case EOF: token = new Token(TokenType.EOF, null); break;
		}
	}
	
	/** Generating next tokens while in EXTENDED mode. **/
	private void extendedNextToken() {
		if(data[currentIndex]=='#') {
//			state = LexerState.BASIC; => user should recognize and do this
			token = new Token(TokenType.SYMBOL, '#');
			currentIndex++;
			return;
		}
		// start reading (until you see a '#' or a whitespace)
		String stringToken = "";
		while(data[currentIndex]!='#'
				&& "\r\n\t ".indexOf(data[currentIndex])==-1) {
			stringToken += data[currentIndex++];
			if(currentIndex == data.length) break;
		}
		token = new Token(TokenType.WORD, stringToken);
	}
	
	/** Checks if it is the end of the given text input. **/
	private boolean checkIfEndOfInputText() {
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return true;
		}
		return false;
	}

	/** Skips all whitespaces until it reaches a letter, a digit or a symbol. **/
	private void skipAllWhitespaces() {
		while("\r\n\t ".indexOf(data[currentIndex]) != -1) {
			currentIndex++;
			if(currentIndex == data.length) return;
		}
	}
	
	/** Checks if there is a backslash and starts working. **/
	private String checkIfBackslash() {
		String newString = "";
		if(data[currentIndex] == '\\') {
			TokenType stringType = checkAfterBackslash();
			if(stringType == TokenType.WORD) {
				// read only the digit (or the second backslash) after first backslash
				newString += data[currentIndex+1];
				// skip 2 characters (two backslash or backslash+digit)
				currentIndex += 2;
			} else {
				throw new LexerException("After backslash there can only be a digit or another backslash!");
			}
		}
		return newString;
	}
	
	/** Checks if the character c is a letter, number, symbol or a whitespace. **/
	private TokenType characterType(char c) {
		if(Character.isLetter(c)) return TokenType.WORD;
		if(Character.isDigit(c)) return TokenType.NUMBER;
		if("\r\n\t ".indexOf(c) == -1) return TokenType.SYMBOL; // skip all whitespaces
		
		return null;
	}
	
	/** After backslash there can only be a digit or another backslash. **/
	private TokenType checkAfterBackslash() {
		if(currentIndex+1 == data.length)
			throw new LexerException("Backslash cannot be at the end of input text!");
		if(data[currentIndex+1] == '\\') return TokenType.WORD;
		if(Character.isDigit(data[currentIndex+1])) return TokenType.WORD;
		
		return null;
	}

	/** Does the rest for type NUMBER. **/
	private void workForNumber(TokenType stringType, String stringToken) {
		while(characterType(data[currentIndex]) == stringType) {
			stringToken += data[currentIndex++];
			if(currentIndex == data.length) break;
		}
		long number;
		// it should be able to parse it as type Long
		try {
			number = Long.parseLong(stringToken);
		} catch(NumberFormatException ex) {
			throw new LexerException(ex.getMessage());
		}
		token = new Token(stringType, number);
	}
	
	/** Does the rest for type WORD. **/
	private void workForWord(TokenType stringType, String stringToken) {
		TokenType newType;
		do {
			if(currentIndex == data.length) break;
			newType = null;
			String newString = checkIfBackslash();
			if(newString.isEmpty()) {
				// if character is a digit, letter or a symbol add to the stringToken
				if(characterType(data[currentIndex]) == stringType) {
					stringToken += data[currentIndex++];
					newType = TokenType.WORD;
				}
			} else {
				stringToken += newString;
				newType = TokenType.WORD;
			}
		} while(newType == stringType);
		
		token = new Token(stringType, stringToken);
	}
	
}
