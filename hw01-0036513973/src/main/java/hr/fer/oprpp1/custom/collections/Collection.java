package hr.fer.oprpp1.custom.collections;

/**
 * Instances of this class represent some general collection of Objects and specifie
 * methods that can be used to manipulate those collections.
 * 
 * @author lukasunara
 *
 */
public class Collection {

	/** Protected default constructor. **/
	protected Collection() {
		super();
	}
	
	/**
	 * Method returns true if this Collection contains no Objects and false otherwise.
	 * 
	 * @return boolean value which shows if this Collection is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Counts the number of currently stored Objects in this Collection.
	 * 
	 * @return int value which shows the exact number of currently stored Objects
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given Object into this Collection.
	 * 
	 * @param value the Object which needs to be added
	 */
	public void add(Object value) {	}
	
	/**
	 * Method returns true only if this Collection contains given value, as determined by
	 * <code>boolean equals(Object other)</code> method.
	 * It doesn't throw <code>NullPointerException</code> if given value is <code>null</code>.
	 * 
	 * @param value the Object which we are searching for in this Collection
	 * @return boolean value which shows if this Collection contains given value
	 */
	public boolean contains(Object value) {
		return false;
	}
	
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
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this Collection, fills it with
	 * Collection's content and returns the array. This method never returns <code>null</code>.
	 * 
	 * @return new array filled with content from this Collection
	 * @throws UnsupportedOperationException because this Collection doesn't have storage
	 * capabilities
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("It is not possible to create an array; "
				+ "class Collection does not have storage capabilities"); 
	}
	
	/**
	 * Method calls <code>Processor.process(Object value)</code> for each element of this
	 * Collection.
	 * 
	 * @param processor entity of class Processor which performs a specific operation
	 */
	public void forEach(Processor processor) { }
	
	/**
	 * Method adds into the current Collection all elements from the given Collection.
	 * This other Collection remains unchanged.
	 * 
	 * @param other Collection whose Objects are added to this Collection
	 */
	public void addAll(Collection other) {
		
		/**
		 * Local class which creates a specific Processor whose role is to add all Objects
		 * from Collection other into this Collection.
		 */
		class ProcessorAdder extends Processor {
			
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}

		other.forEach(new ProcessorAdder());
	}
	
	/**
	 * Removes all elements from this Collection.
	 */
	public void clear() { }

}
