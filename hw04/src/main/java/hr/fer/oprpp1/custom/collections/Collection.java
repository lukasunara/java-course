package hr.fer.oprpp1.custom.collections;

/**
 * Interface Collection contains a list of methods crucial for any collection of Objects.
 * 
 * @author lukasunara
 *
 * @param <E> type of Object which this Collection can storage
 */
public interface Collection<E> {
	
	/**
	 * Method returns true if this Collection contains no Objects and false otherwise.
	 * 
	 * @return boolean value which shows if this Collection is empty
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Counts the number of currently stored Objects in this Collection.
	 * 
	 * @return int value which shows the exact number of currently stored Objects
	 */
	int size();
	
	/**
	 * Adds the given Object into this Collection.
	 * 
	 * @param value the Object which needs to be added
	 */
	void add(E value);
	
	/**
	 * Method returns true only if this Collection contains given value, as determined by
	 * <code>boolean equals(Object other)</code> method.
	 * It doesn't throw <code>NullPointerException</code> if given value is <code>null</code>.
	 * 
	 * @param value the Object which we are searching for in this Collection
	 * @return boolean value which shows if this Collection contains given value
	 */
	boolean contains(Object value);
	
	/**
	 * Returns true only if this Collection contains given value as determined by
	 * <code>boolean equals(Object other)</code> method and removes one occurrence of it
	 * (in this class it is not specified which one).
	 * 
	 * @param value the Object which we are searching for and want to remove from 
	 * this Collection
	 * @return boolean value which shows if we found and succesfully removed one occurence of
	 * the given value
	 */
	boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of this Collection, fills it with
	 * Collection's content and returns the array. This method never returns <code>null</code>.
	 * 
	 * @return new array filled with content from this Collection
	 * @throws UnsupportedOperationException because this Collection doesn't have storage
	 * capabilities
	 */
	Object[] toArray();
	
	/**
	 * Method calls <code>Processor.process(Object value)</code> for each element of this
	 * Collection (iterates through the collection with a ElementsGetter).
	 * 
	 * @param processor entity of class Processor which performs a specific operation
	 */
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = this.createElementsGetter();
		
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Method adds into the current Collection all elements from the given Collection.
	 * This other Collection remains unchanged.
	 * 
	 * @param other Collection whose Objects are added to this Collection
	 */
	default void addAll(Collection<? extends E> other) {
		other.forEach(value -> add(value));	// lambda function => Processor
	}
	
	/**
	 * Removes all elements from this Collection.
	 */
	void clear();

	/**
	 * Method creates a reference on a new ElementsGetter which is going to specify in
	 * which way does the ElementsGetter iterate over every element of the Collection.
	 * 
	 * @return reference on the new ElementsGetter
	 */
	ElementsGetter<E> createElementsGetter();
	
	/**
	 * Every element from the given Collection which is accepted by the tester is added to
	 * this Collection.
	 * 
	 * @param col the given Collection from which we add elements
	 * @param tester checks which elements from the given Collection are acceptable
	 */
	default void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester) {
		// forEach already uses ElementsGetter
		col.forEach(obj -> {
			if(tester.test(obj)) this.add(obj);
		});
	}
	
}
