package hr.fer.oprpp1.custom.collections;

/**
 * Interface List contains methods every List of elements should have.
 * 
 * @author lukasunara
 *
 * @param <E> type of Object which the List can storage
 */
public interface List<E> extends Collection<E> {

	/**
	 * Returns the object that is stored in List at position index.
	 * 
	 * @param index position of the Object we want to return
	 * @return Object that is stored at position index
	 */
	E get(int index);
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in Collection.
	 * 
	 * @param value the Object which we want to insert
	 * @param position position where the given value is inserted
	 */
	void insert(E value, int position);
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value
	 * or -1 if the value is not found.
	 * 
	 * @param value the Object which we are searching for
	 * @return int value (index of occurrence or -1)
	 */
	int indexOf(Object value);
	
	/**
	 * Removes element at specified index from collection. Element that was previously at
	 * location index+1 after this operation is on location index, etc.
	 * 
	 * @param index position in Collection from which we want to remove an Object
	 * @throws IndexOutOfBounds legal indexes are 0 to size-1
	 */
	void remove(int index);
	
}
