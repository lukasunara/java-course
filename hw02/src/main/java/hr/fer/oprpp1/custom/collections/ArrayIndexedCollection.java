package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Instances of this class implement {@link List} and represent a resizable array-backed
 * Collection of Objects. In this Collection duplicate elements are allowed, but storage of
 * <code>null</code> references is not allowed.
 * 
 * @author lukasunara
 *
 */
public class ArrayIndexedCollection implements List {
	
	/** The default cacity of this Collection **/
	final static int DEFAULT_CAPACITY = 16;

	/** Current size of this Collection **/
	private int size;
	
	/** Array of Object references (length is determined by capacity) **/
	private Object[] elements;
	
	/** Used to see if collection was altered (altered when != 0) **/
	private long modificationCount;
	
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
		this.modificationCount = 0l;
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
			throw new NullPointerException("You can not add null to Collection!");
		
		// if Collection is full => after reallocating the array add value to the end
		if(size == elements.length) reallocateByDoublingSize();
		
		elements[size++] = value;
		modificationCount++;	// collection has been altered
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
			modificationCount++;	// collection has been altered
			return true;
		}
		return false;
	}
	
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
			modificationCount++;	// collection has been altered
		}
		size = 0;
	}
	
	/** Reallocates elements array by doubling its size. **/
	private void reallocateByDoublingSize() {
		elements = Arrays.copyOf(elements, 2*size);
		modificationCount++;	// collection has been altered
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
		modificationCount++;	// collection has been altered
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
		
		modificationCount++;	// collection has been altered
	}

	/**
	 * Returns elements array.
	 * 
	 * @return elements array
	 */
	public Object[] getElements() {
		return elements;
	}

	/**
	 * Extends the {@link ElementsGetter} interface, because it's going to specify in which
	 * way does the ElementsGetter iterate over every element of the {@link ArrayIndexedCollection}.
	 */
	private static class ArrayElementsGetter implements ElementsGetter {
		
		/** Points on the element on which we are currently while iterating over the array **/
		private int index;
		
		/** {@link ArrayIndexedCollection} we want to iterate over **/
		private ArrayIndexedCollection arrayCollection;
		
		/** Used to see if collection was altered (altered when != arrayCollection.modificationCount) **/
		private long savedModificationCount;
		
		/**
		 * Creates an instance of {@link ArrayElementsGetter} and a reference on the given
		 * {@link ArrayIndexedCollection}.
		 * 
		 * @param arrayCollection is the collection we want to iterate over.
		 */
		public ArrayElementsGetter(ArrayIndexedCollection arrayCollection) {
			super();
			this.index = -1;
			this.arrayCollection = arrayCollection;
			this.savedModificationCount = arrayCollection.modificationCount;
		}
		
		/**
		 * Checks if there exists another element in the ArrayIndexedCollection.
		 * 
		 * @throws ConcurrentModificationException if collection has been altered
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != arrayCollection.modificationCount)
				throw new ConcurrentModificationException("ArrayIndexedCollection has been altered!");
			
			return arrayCollection.elements[index+1] != null;
		}

		/**
		 * Checks if there exists another element in the {@link ArrayIndexedCollection}, returns
		 * the Object and moves past it (iterates over an element in the elements array).
		 * 
		 * @throws NoSuchElementException if hasNextElement() returns fals
		 * @throws ConcurrentModificationException if collection has been altered
		 */
		@Override
		public Object getNextElement() {
			if(savedModificationCount != arrayCollection.modificationCount)
				throw new ConcurrentModificationException("ArrayIndexedCollection has been altered!");
			if(!hasNextElement())
				throw new NoSuchElementException("ArrayIndexedCollection is empty!");
			
			return arrayCollection.elements[++index];
		}
		
		/**
		 * @throws NoSuchElementException if there are not any elements remaining in the array
		 */
		@Override
		public void processRemaining(Processor p) {
			if(!hasNextElement())
				throw new NoSuchElementException("ArrayIndexedCollection is empty!");
			
			for(index++; index < arrayCollection.size; index++) {
				p.process(arrayCollection.elements[index]);
			}
		}
		
	}
	
	/**
	 * Creates an {@link ElementsGetter} object which is used to iterate over this
	 * {@link ArrayIndexedCollection}.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		ElementsGetter eg = new ArrayElementsGetter(this);
		
		return eg;
	}

}
