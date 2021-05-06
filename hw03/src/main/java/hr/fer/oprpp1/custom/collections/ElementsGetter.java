package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface ElementsGetter contains a list of methods which allow us to iterate
 * over any collection of Objects.
 * 
 * @author lukasunara
 *
 * @param <E> type of Object which this ElementsGetter can iterate over
 */
public interface ElementsGetter<E> {

	/**
	 * Checks if there exists another element in the collection.
	 * 
	 * @return true if there is another element and false otherwise
	 * @throws ConcurrentModificationException if collection has been altered
	 */
	boolean hasNextElement();
	
	/**
	 * Checks if there exists another element in the collection, returns the
	 * Object and move past it (iterates over an element in the Collection).
	 * 
	 * @return the next element of Collection
	 * @throws NoSuchElementException if hasNextElement() returns false
	 * @throws ConcurrentModificationException if collection has been altered
	 */
	E getNextElement();
	
	/**
	 * Calls Processor's function on every remaining element of a Collection.
	 *  
	 * @param p the Processor which executes his function
	 * @throws NoSuchElementException if there are not any elements remaining in the Collection
	 */
	void processRemaining(Processor<? super E> p);

}
