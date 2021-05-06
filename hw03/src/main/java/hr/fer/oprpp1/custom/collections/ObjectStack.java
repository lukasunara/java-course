package hr.fer.oprpp1.custom.collections;

/**
 * Class ObjectStack represents a Stack collection. It is very similar to
 * {@link ArrayIndexedCollection}. This class must provide the methods which are natural for
 * a Stack (push, pop, peek, isEmpty, size, clear) and hide everything else.
 * 
 * @author lukasunara
 *
 * @param <E> type of Object which this Stack can storage
 */
public class ObjectStack<E> {
	
	/** Instance of ArrayIndexedCollection used for actual element storage **/
	private ArrayIndexedCollection<E> stack;

	/** Default constructor creates an empty Stack. **/
	public ObjectStack() {
		super();
		this.stack = new ArrayIndexedCollection<E>();
	}
	
	/**
	 * Receieves some other Collection which elements are copied into this newly
	 * constructed Stack.
	 * 
	 * @param other a non-null reference to some other Collection
 	 * @throws NullPointerException when the given Collection is null
	 */
	public ObjectStack(Collection<? extends E> other) {
		super();
		if(other == null)
			throw new NullPointerException("The given colelction mustn't be a null reference!");

		this.stack = new ArrayIndexedCollection<E>(other);
	}
	
	/**
	 * Method returns true if this Stack contains no Objects and false otherwise.
	 * 
	 * @return boolean value which shows if this Stack is empty
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Counts the number of currently stored Objects in this Stack.
	 * 
	 * @return int value which shows the exact number of currently stored Objects
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Pushes given value on the Stack.
	 * Method has o(1) average complexity (except when the underlying array in used collection
	 * is reallocated).
	 * 
	 * @param value the Object which needs to be pushed on the Stack
	 * @throws NullPointerException when the given value is <code>null</code>
	 */
	public void push(E value) {
		stack.add(value);
	}
	
	/**
	 * Removes last value pushed on Stack from Stack and returns it.
	 * Method has o(1) average complexity.
	 * 
	 * @return the last value pushed on Stack
	 * @throws EmptyStackException if the Stack is empty when trying to pop()
	 */
	public E pop() {
		if(stack.isEmpty())
			throw new EmptyStackException("Stack is empty so pop() is not possible!");
		
		E popElement = stack.get(stack.size()-1);
		stack.remove(stack.size()-1);
		
		return popElement;
	}
	
	/**
	 * Returns last element placed on Stack, but does not delete it from Stack.
	 *
	 * @return the last value pushed on Stack
 	 * @throws EmptyStackException if the Stack is empty when trying to peek()
	 */
	public E peek() {
		if(stack.isEmpty())
			throw new EmptyStackException("Stack is empty so pop() is not possible!");
		
		return stack.get(stack.size()-1);
	}
	
	/** Removes all elements from Stack. **/
	public void clear() {
		stack.clear();
	}
	
}
