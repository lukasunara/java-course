package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;

/**
 * Instances of this class should use {@link SmartScriptLexer} for production of tokens.
 * Its task is to build the accurate document model which represents the document
 * as provided by the user.
 * 
 * @author lukasunara
 *
 */
public class SmartScriptParser {

	/** String which contains document body */
	private SmartScriptLexer lexer;
	
	/** Stack used for building stack tree */
	private ObjectStack stack;
	
	/** {@link DocumentNode} which represents the whole document which is being parsed **/
	private DocumentNode document;
	
	/**
	 * Constructor creates an instance of {@link SmartScriptLexer} and initializes it
	 * with obtained text.
	 * 
	 * @param text String which contains document body
	 * @throws SmartScriptParserException if the given text is invalid
	 */
	public SmartScriptParser(String text) {
		super();
		this.lexer = new SmartScriptLexer(text);
		this.stack = new ObjectStack();
		this.document = new DocumentNode();
		
		this.parse();
	}

	/** Public getter method for DocumentNode property: document. **/
	public DocumentNode getDocumentNode() {
		return document;
	}
	
	/**
	 * Method starts the parsing of given text.
	 * 
	 * @throws SmartScriptParserException if any Exception occurs
	 */
	public void parse() {
		try {
			this.startParsing();			
		} catch(Exception ex) {
			// System.out.println(ex.getMessage());
			throw new SmartScriptParserException(ex.getMessage());
		}
	}
	
	/** Parsing begins. **/
	private void startParsing() {
		stack.push(document);

		while(true) {
			Token token = lexer.nextToken(); // read next token
			
			if(token.getType() == TokenType.EOF) break; // end of parsing
			
			Node node = null; // current node
			Node peekNode = null; // node at the top of stack
			if(token.getType() == TokenType.STRING) {
				node = new TextNode((String)token.getValue()); // text out of Tags
				peekNode = (Node)stack.peek();
				peekNode.addChildNode(node);
				continue;
			}
			if(token.getType() == TokenType.START) { // beginning of a new Tag
				lexer.setState(SmartScriptLexerState.TAG);
				node = checkTypeOfTag();
				if(node instanceof EchoNode) {
					// add every EchoNode as child to Node on top of stack
					peekNode = (Node)stack.peek();
					peekNode.addChildNode(node);
				} else if(node instanceof ForLoopNode) {
					// add every ForLoopNode as child to Node on top of stack
					peekNode = (Node)stack.peek();
					peekNode.addChildNode(node);
					stack.push(node); // push every ForLoopNode to stack
				}
				continue;
			}
		}
	}
	
	/** Method checks type of tag. **/
	private Node checkTypeOfTag() {
		Token token = lexer.nextToken(); //read next token
		Node node = null;
		switch(token.getType()) {
			case SYMBOL: {
				if((Character)token.getValue() == '=') {
					node = createEchoNode();
					break;
				}
				throw new SmartScriptParserException("Invalid tag name!");
			}
			case VARIABLE: {
				if(String.valueOf(token.getValue()).toUpperCase().equals("FOR")) {
					node = createForLoopNode();
					break;
				} else if(String.valueOf(token.getValue()).toUpperCase().equals("END")) {
					token = lexer.nextToken();
					if(token.getType() == TokenType.END) {
						lexer.setState(SmartScriptLexerState.BASIC);
						stack.pop();
						// If stack is => we have more END-tags than FORLOOP-tags
						if(stack.isEmpty())
							throw new SmartScriptParserException("Too many END-tags!");
					}
					break;
				}
				throw new SmartScriptParserException("Invalid tag name!");
			}
			default: throw new SmartScriptParserException("This never happens!");
		}
		return node;
	}

	/** Creates a new {@link EchoNode}. **/
	private Node createEchoNode() {
		LinkedListIndexedCollection elements = new LinkedListIndexedCollection();
		while(true) {
			Token token = lexer.nextToken();
			
			if(token.getType() == TokenType.END) {
				lexer.setState(SmartScriptLexerState.BASIC);
				break;
			}
			
			Element elem = null; // current element
			switch(token.getType()) {
				case VARIABLE: {
					elem = new ElementVariable(String.valueOf(token.getValue())); break;
				}
				case SYMBOL: {
					// "=" is not a valid operator
					if("+-/*^".indexOf((Character)token.getValue()) != -1) {
						elem = new ElementOperator(String.valueOf(token.getValue()));
						break;
					}
					if('\"' == (Character)token.getValue()) {
						token = lexer.nextToken();
						// continues to read STRING
						if(token.getType() == TokenType.STRING) {
							elem = new ElementString((String)token.getValue());
							// continues to see if string normally ended with '\"'
							token = lexer.nextToken();
							if((Character)token.getValue() == '\"') break;
						}
						throw new SmartScriptParserException("Invalid EchoTag!");
					}
				}
				case FUNCTION: {
					elem = new ElementOperator((String)token.getValue()); break;
				}
				case INTEGER: {
					elem = new ElementConstantInteger((Integer)token.getValue()); break;
				}
				case DOUBLE: {
					elem = new ElementConstantDouble((Double)token.getValue()); break;
				}
				default: throw new SmartScriptParserException("Invalid EchoTag!");
			}
			elements.add(elem);
		}
		Element[] realElementsArray = new Element[elements.size()];
		for(int i = 0; i < elements.size(); i++) {
			realElementsArray[i] = (Element)elements.get(i);
		}
		return new EchoNode(realElementsArray);
	}
	
	/** Creates a new {@link ForLoopNode}. **/
	private Node createForLoopNode() {
		Element[] elements = new Element[ForLoopNode.FOR_LOOP_NUMBER_OF_VARIABLES];
		int i = 0;
		while(true) {
			Token token = lexer.nextToken();
			
			if(token.getType() == TokenType.END) {
				if(i < 2) throw new SmartScriptParserException("Too few arguments in ForLoopTag!");
				lexer.setState(SmartScriptLexerState.BASIC);
				break;
			}
			Element elem = null; // current element

			if(i == 0) {
				if(token.getType() != TokenType.VARIABLE)
					throw new SmartScriptParserException("First argument in ForLoopTag must be a variable!");
				elem = new ElementVariable(String.valueOf(token.getValue()));
			} else if(i > 3) {
				throw new SmartScriptParserException("Too many arguments in ForLoopTag!");
			} else {
				switch(token.getType()) {
					case VARIABLE: {
						elem = new ElementVariable(String.valueOf(token.getValue())); break;
					}
					case STRING: {
						elem = new ElementString((String)token.getValue()); break;
					}
					case INTEGER: {
						elem = new ElementConstantInteger((Integer)token.getValue()); break;
					}
					case DOUBLE: {
						elem = new ElementConstantDouble((Double)token.getValue()); break;
					}
					default: throw new SmartScriptParserException("Invalid ForLoopTag!");
				}
			}
			elements[i++] = elem;
		}
		if(elements[ForLoopNode.FOR_LOOP_NUMBER_OF_VARIABLES-1] != null)
			return new ForLoopNode((ElementVariable)elements[0], elements[1], elements[2], elements[3]);
		else
			return new ForLoopNode((ElementVariable)elements[0], elements[1], null, elements[3]);
	}
	
}
