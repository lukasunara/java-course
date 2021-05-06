package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Instances of this class allow the fractal display procedure to be performed and should
 * offer a stack on which it is possible to place and retrieve turtle states.
 * 
 * @see TurtleState
 * 
 * @author lukasunara
 *
 */
public class Context {

	/** Represents the stack on which turtle states can be storaged **/
	private ObjectStack<TurtleState> stack;
	
	/** Default constructor creates an empty Context. **/
	public Context() {
		super();
		stack = new ObjectStack<>();
	}
	
	/**
	 * Method is used to return the current state of the turtle.
	 * 
	 * @return current {@link TurtleState}
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Pushes given {@link TurtleState} on the stack.
	 * 
	 * @param state {@link TurtleState} to be pushed on top of stack
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/** Removes one {@link TurtleState} from the top of stack. **/
	public void popState() {
		stack.pop();
	}
	
}
