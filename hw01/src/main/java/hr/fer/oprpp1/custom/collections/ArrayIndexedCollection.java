package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * Instances of this class represent a resizable array-backed {@link Collection} of Objects.
 * In this Collection duplicate elements are allowed, but storage of <code>null</code>
 * references is not allowed.
 * 
 * @author lukasunara
 *
 */
public class ArrayIndexedCollection extends Collection {
	
	/** The default cacity of this Collection **/
	final static int DEFAULT_CAPACITY = 16;

	/** Current size of this Collection **/
	private int size;
	
	/** Array of Object references (length is determined by capacity) **/
	private Object[] elements;
	
	/**
	 * Default constructor sets the capacity of this Collection to 16 and preallocates the 
	 * elements array of that size.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Sets the capacity of this Collection to value initalCapacity and preallocates the 
	 * elements array of that size.
	 * 
	 * @param initialCapacity int value which is used to set the capacity of Collection
	 * @throws IllegalArgumentException when initialCapacity is a number < 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		if(initialCapacity < 1)
			throw new IllegalArgumentException("Initial capacity is < 1 !");

		this.elements = new Object[initialCapacity];
		this.size = 0;
	}
	
	/**
	 * Receieves some other Collection which elements are copied into this newly constructed
	 * Collection. If the size of given Collection is bigger than the DEFAULT_CAPACITY, the
	 * capacity is set to that bigger size. Collection's private variable size is also set to
	 * <code>other.size()</code>.
	 * 
	 * @param other a non-null reference to some other Collection
	 * @throws NullPointerException when the given Collection is null
	 */
	public ArrayIndexedCollection(Collection other) {
		this();
		if(other == null)
			throw new NullPointerException("Value of other Collection mustn't be null!");
		
		if(other.size() > DEFAULT_CAPACITY) {
			this.elements = new Object[other.size()];
		}
		this.addAll(other);
	}
	
	/**
	 * Receieves some other Collection which elements are copied into this newly constructed
	 * Collection. Also sets the capacity of this Collection to initalCapacity and preallocates
	 * the elements array of that size. If the size of given Collection is bigger than the
	 * initialCapacity, the capacity is set to that bigger size.
	 * Collection's private variable size is also set to <code>other.size()</code>.
	 * 
	 * @param other a non-null reference to some other Collection
	 * @param initialCapacity int value which is used to set the capacity of Collection
	 * @throws NullPointerException when the given Collection is null
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		if(other == null)
			throw new NullPointerException("Value of other mustn't be null!");
		
		if(other.size() > initialCapacity) {
			this.elements = new Object[other.size()];
		}		
		this.addAll(other);
	}
	
	/**
	 * Adds the given object into this collection (reference is added into first empty place in
	 * the elements array; if the elements array is full, it should be reallocated by doubling
	 * its size).
	 * Average complexity of this method is O(1).
	 * 
	 * @throws NullPointerException Object value mustn't be <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if(value == null)
			throw new NullPointerException("You can not add null to this Collection!");
		
		// if Collection is full => after reallocating the array add value to the end
		if(size == elements.length) reallocateByDoublingSize();
		
		elements[size++] = value;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean contains(Object value) {
		return this.indexOf(value) != -1;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		
		if(index != -1) {
			for(int i = index; i < size-1; i++) {
				elements[i] = elements[i+1];
			}
			elements[--size] = null; // reduces size by 1 and deletes duplicate at the end of array
			return true;
		}
		return false;
	}
	
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Reallocates elements array by doubling its size.
	 */
	private void reallocateByDoublingSize() {
		elements = Arrays.copyOf(elements, 2*size);
	}
	
	/**
	 * Returns the object that is stored in backing array at position index.
	 * Average complexity of this method is O(1).
	 * 
	 * @param index position of the Object we want to return
	 * @return Object that is stored at position index
	 * @throws IndexOutOfBoundsException valid indexes are 0 to size-1
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1)
			throw new IndexOutOfBoundsException("Valid indexes are 0 to " + (size-1) + "!");
		
		return elements[index];
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array.
	 * Average complexity of this method is O(n).
	 * 
	 * @param value the Object which we want to insert
	 * @param position index of array where the given value is inserted
	 * @throws IndexOutOfBoundsException the legal positions are 0 to size (both are included)
	 * @throws NullPointerException Object value mustn't be <code>null</code>
	 */
	public void insert(Object value, int position) {
		if(value == null)
			throw new NullPointerException("You cannot insert null to Collection!");
		if(position < 0 || position > size)
			throw new IndexOutOfBoundsException("Legal positions are 0 to " + size + "!");
		
		// if Collection is full reallocate elements array
		if(size == elements.length)	reallocateByDoublingSize();
		
		// create a spot for the new value
		for(int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		elements[position] = value;		// insert the value and expand size by 1
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value
	 * or -1 if the value is not found.
	 * Average complexity of this method is O(n).
	 * 
	 * @param value the Object which we are searching for
	 * @return int value (index of occurrence or -1)
	 */
	public int indexOf(Object value) {
		if(this.isEmpty() || value==null) return -1;
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) return i;
		}
		return -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was previously at
	 * location index+1 after this operation is on location index, etc.
	 * 
	 * @param index position in array from which we want to remove an Object
	 * @throws IndexOutOfBounds legal indexes are 0 to size-1
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1)
			throw new IndexOutOfBoundsException("Valid indexes are 0 to " + (size-1) + "!");
		
		for(int i = index; i < size-1; i++) {
			elements[i] = elements[i+1];
		}
		elements[--size] = null;	// deletes duplicate on the end and reduces size by 1
	}

}
