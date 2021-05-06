package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.hw02.prob1.LexerException;

/**
 * Class SmartScriptLexer represents a lexic analizator which groups Characters into
 * Elements. Used by {@link SmartScriptParser}.
 * 
 * @author lukasunara
 *
 */
public class SmartScriptLexer {

	/** Input text **/
	private char[] data;
	
	/** Current token **/
	private Token token;

	/** Index of the first unused Character **/
	private int currentIndex;
		
	/** State in which lexer currently works **/
	private SmartScriptLexerState state;

	/**
	 * Constructor recieves a text and seperates it into elements.
	 * 
	 * @param text String which is used to create elements
	 * @throws NullPointerException when the given text is null
	 **/
	public SmartScriptLexer(String text) {
		super();
		if(text == null) throw new NullPointerException("Text cannot be null!");
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = new Token(null, null);
		this.state = SmartScriptLexerState.BASIC;
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
	public void setState(SmartScriptLexerState state) {
		if(state == null)
			throw new NullPointerException("State of Lexer cannot be null!");
		
		this.state = state;
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
		
		switch(state) {
			case BASIC: basicNextToken(); break;
			case TAG: tagNextToken(); break;
			case STRINGTAG: stringTagNextToken(); break;
		}
		
		return token;
	}
		
	/** Generating next tokens while in BASIC mode. **/
	private void basicNextToken() {
		if(checkStartOfTag()) {
//			state = SmartScriptLexerState.TAG; => parser should recognize and do this
			token = new Token(TokenType.START, "{$");
			currentIndex += 2;
			return;
		}
			
		// start reading (until you see a "{$")
		String stringToken = "";
		while(!checkStartOfTag()) {
			String newString = checkIfBackslash();
			// if there was not an escape sequence read normally
			if(newString.isEmpty()) {
				stringToken += data[currentIndex++];
			} else {
				stringToken += newString; // add the "\" or "{"
			}
			if(currentIndex == data.length) break;
		}
		token = new Token(TokenType.STRING, stringToken);
	}
	
	/** Generating next tokens while in TAG mode. **/
	private void tagNextToken() {
		skipAllWhitespaces(); // skip all leading whitespaces
		if(checkIfEndOfInputText()) return;
		
		if(checkEndOfTag()) {
//			state = SmartScriptLexerState.BASIC; => parser should recognize and do this
			token = new Token(TokenType.END, "$}");
			currentIndex += 2;
			return;
		}
		if(data[currentIndex] == '\"') {
			state = SmartScriptLexerState.STRINGTAG;
			token = new Token(TokenType.SYMBOL, '\"');
			currentIndex++;
			return;
		}
		
		String stringToken = "";
		TokenType stringType = null;
		// read the first character to define the type of the next token
		stringType = characterType(data[currentIndex]);
		// if character is a digit, letter or a symbol add to the stringToken
		if(stringType != null) {
			stringToken += data[currentIndex++];
		} else {
			/* this should never happen because null means a whitespace
			and we skipped all leading whitespaces */
			throw new NullPointerException("This never happens!");
		}

		switch(stringType) {
			case SYMBOL: token = new Token(stringType, stringToken.charAt(0)); return;
			case VARIABLE, FUNCTION: workForVariableOrFunction(stringType, stringToken); break;
			case INTEGER: workForNumber(stringType, stringToken);  break;
			default: break; // never happens
		}
	}
		
	/** Generating next tokens while in STRINGTAG mode. **/
	private void stringTagNextToken() {
		if(data[currentIndex] == '\"') {
			state = SmartScriptLexerState.TAG;
			token = new Token(TokenType.SYMBOL, '\"');
			currentIndex++;
			return;
		}
		String stringToken = "";
		while(data[currentIndex] != '\"') {
			String newString = checkIfBackslash();
			// if there was not an escape sequence read normally
			if(newString.isEmpty()) {
				stringToken += data[currentIndex++];
			} else {
				stringToken += newString; // add the "\" or "{"
			}
			if(currentIndex == data.length)
				throw new LexerException("Input text cannot end inside of tag!");
		}
		token = new Token(TokenType.STRING, stringToken);
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
	
	/** Checks if it's the start of a new tag. **/
	private boolean checkStartOfTag() {
		if(currentIndex+1 == data.length)
			return false;
		
		return data[currentIndex]=='{' && data[currentIndex+1]=='$';
	}
	
	/** Checks if it's the end of tag. **/
	private boolean checkEndOfTag() {
		if(currentIndex+1 == data.length)
			throw new LexerException("Input text cannot end while in TAG state!");
		
		return data[currentIndex]=='$' && data[currentIndex+1]=='}';
	}
	
	/** Checks if there is a backslash and starts working. **/
	private String checkIfBackslash() {
		String newString = "";
		if(data[currentIndex] == '\\') {
			if(state == SmartScriptLexerState.BASIC) {
				newString = String.valueOf(checkAfterBackslashBasic());
			} else if(state == SmartScriptLexerState.STRINGTAG) {
				newString = String.valueOf(checkAfterBackslashStringTag());
			}
			currentIndex += 2; // skip 2 characters
		}
		return newString;
	}
		
	/** After backslash there can only be a '{' or another backslash. **/
	private String checkAfterBackslashBasic() {
		if(currentIndex+1 == data.length)
			throw new LexerException("Backslash cannot be at the end of input text!");
		
		if(data[currentIndex+1] == '\\') return "\\\\";
		else if(data[currentIndex+1] == '{') return "\\{";
		else throw new LexerException("Invalid escape after backslash!");
	}
	
	/** After backslash there can only be a '\"', 'n', 'r', 't', or another backslash. **/
	private char checkAfterBackslashStringTag() {
		if(currentIndex+1 == data.length)
			throw new LexerException("Backslash cannot be at the end of input text!");
		
		switch(data[currentIndex+1]) {
			case '\\': return '\\';
			case '\"': return '\"';
			case 'n': return '\n';
			case 'r': return '\r';
			case 't': return '\t';
			default: throw new LexerException("Invalid escape after backslash!");
		}
	}
	
	/** Checks if the character c is a letter, number, symbol or a whitespace. **/
	private TokenType characterType(char c) {
		if(Character.isLetter(c)) return TokenType.VARIABLE;
		if(Character.isDigit(c)) return TokenType.INTEGER;
		if("\r\n\t ".indexOf(c) == -1) {	// skip all whitespaces
			// after "-" (MINUS) must be a digit to be a NUMBER, otherwise it is a SYMBOL
			if(c=='-' && Character.isDigit(data[currentIndex+1])) return TokenType.INTEGER;
			if(c=='@') {
				if(Character.isLetter(data[currentIndex+1])) {
					currentIndex++;	// don't save the '@' in function name
					return TokenType.FUNCTION;
				} else {
					throw new LexerException("After @ must be a letter!");
				}
			}
			if("=+-/*^.".indexOf(c) != -1) return TokenType.SYMBOL;
			
			throw new LexerException("Valid operators are: +,-,*,/,^ and tag name \"=\" !");
		}
			
		return null;
	}
	
	/** Does the rest for type INTEGER or DOUBLE. **/
	private void workForNumber(TokenType stringType, String stringToken) {
		// read while it is still the same number
		while(characterType(data[currentIndex]) == stringType) {
			stringToken += data[currentIndex++];
			if(currentIndex == data.length)
				throw new LexerException("Input text cannot end while inside of TAG!");
		}
		// if after a INTEGER there is a "dot", then make a DOUBLE number
		if(data[currentIndex] == '.') {
			stringToken += data[currentIndex++];
			if(currentIndex == data.length)
				throw new LexerException("Input text cannot end while inside of a TAG!");
			
			while(characterType(data[currentIndex]) == stringType) {
				stringToken += data[currentIndex++];
				if(currentIndex == data.length)
					throw new LexerException("Input text cannot end while inside of a TAG!");
			}
			double number;
			// it should be able to parse it as type Double
			try {
				number = Double.parseDouble(stringToken);
			} catch(NumberFormatException ex) {
				throw new LexerException(ex.getMessage());
			}
			token = new Token(TokenType.DOUBLE, number);
			return;
		}
		// it should be able to parse it as type Integer
		int number;
		try {
			number = Integer.parseInt(stringToken);
		} catch(NumberFormatException ex) {
			throw new LexerException(ex.getMessage());
		}
		token = new Token(stringType, number);
	}
		
	/** Does the rest for type VARIABLE **/
	private void workForVariableOrFunction(TokenType stringType, String stringToken) {
		do {
			if(currentIndex == data.length)
				throw new LexerException("Input text cannot end while inside of a TAG!");
			
			if(!isPartOfVariableOrFunction(data[currentIndex])) break;
			// if character is a digit, letter or an underscore add to the stringToken
			stringToken += data[currentIndex++];
		} while(true);
			
		token = new Token(stringType, stringToken);
	}
	
	/** Checks if next char is part of the variable (letter, digit or underscore). **/
	private boolean isPartOfVariableOrFunction(char c) {
		if(Character.isLetter(c)) return true;
		if(Character.isDigit(c)) return true;
		if(c == '_') return true;
		
		return false;
	}
	
}
