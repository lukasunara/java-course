package hr.fer.oprpp1.custom.collections;

/**
 * Instances of this class represent a linked list-backed collection of Objects.
 * In this Collection duplicate elements are allowed, but storage of <code>null</code>
 * references is not allowed.
 * 
 * @author lukasunara
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/** Current size of collection (number of nodes in list) **/
	private int size;
	
	/** Reference to the first node of the linked list **/
	private ListNode first;
	
	/** Reference to the last node of the linked list **/
	private ListNode last;
	
	/**
	 * Class ListNode reprresents one element (one Node) of a LinkedListIndexedCollection.
	 * 
	 * @author lukasunara
	 */
	private static class ListNode {
		
		/** Pointer to previous Node in List **/
		ListNode previous;
		
		/** Pointer to next Node in List **/
		ListNode next;
		
		/** Reference for value storage **/
		Object value;
		
		/**
		 * Creates a new ListNode which points on the previous and next nodes in Collection.
		 * Also stores given value in the reference for value storage.
		 * 
		 * @throws NullPointerException when the given value is <code>null</code>
		 * @param value the Object shich is stored in one Node of Collection
		 */
		public ListNode(Object value, ListNode previous, ListNode next) {
			super();
			if(value == null)
				throw new NullPointerException("Object value mustn't be a null reference!");
			
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
		
		/**
		 * Creates a new ListNode and sets previous and next pointers to null, because it
		 * doesn't know where in LinkedListIndexedCollection should store the new Node.
		 * Also stores given value in the reference for value storage.
		 * 
		 * @throws NullPointerException when the given value is <code>null</code>
		 * @param value the Object shich is stored in one Node of Collection
		 */
		public ListNode(Object value) {
			this(value, null, null);
		}
		
	}
	
	/**
	 * The default constructor creates an empty Collection. References first and last point to
	 * <code>null</code> when a Collection is empty.
	 */
	public LinkedListIndexedCollection() {
		super();
		this.size = 0;
		this.first = this.last = null;
	}
	
	/**
	 * Receieves some other Collection which elements are copied into this newly constructed
	 * Collection.
	 * 
	 * @param other a non-null reference to some other Collection
 	 * @throws NullPointerException when the given Collection is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		if(other == null)
			throw new NullPointerException("The value of other Collection mustn't be null!");
		
		this.addAll(other);
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the given Object into this Collection at the end of it. The newly
	 * added element becomes the element at the biggest index.
	 * 
	 * @throws NullPointerException when Object value is <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if(value == null)
			throw new NullPointerException("You can not add null to Collection!");
	
		ListNode newNode = new ListNode(value, last, null);

		// if this Collection is empty => first = newNode
		if(last == null) {
			first = newNode;
		} else {
			// this.last is not last element of this List anymore => this.last.next = newNode;
			last.next = newNode;
		}
		last = newNode;		// newNode becomes last element in the List
		
		size++;
	}
	
	@Override
	public boolean contains(Object value) {
		if(this.isEmpty() || value == null) return false;
		
		ListNode node = null;
		for(node = first; node != null && !value.equals(node.value); node = node.next) {
			;
		}
		if(node != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		if(this.isEmpty() || value == null) return false;
		
		ListNode node = null;
		// search for a Node in List which storaged the same value
		for(node = first; node != null && !value.equals(node.value); node = node.next) {
			;
		}
		if(node != null) {
			// removing of the only element in this List:
			if (node == first && node == last) { 
				first = null;
				last = null;
			// removing of first element in this List:
			} else if(node == first) {
				first = node.next;
				node.next.previous = null;	// because it's the first element now
			// removing of last element in this List:
			} else if(node == last) {
				last = node.previous;
				node.previous.next = null;	// because it's the last element now
			// removing of an normal element which is somewhere else in this List:
			} else {
				node.next.previous = node.previous;
				node.previous.next = node.next;
			}
			size--;
		}
		return false;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		ListNode node = first;
		for(int i = 0; i < size; i++) {
			array[i] = node.value;
			node = node.next;
		}

		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(ListNode node = first; node != null; node = node.next) {
			processor.process(node.value);
		}
	}
	
	@Override
	public void clear() {
		for(ListNode node = first; node != null; ) {
			ListNode pomNode = node.next;
			
			node.previous = null;
			node.next = null;
			node.value = null;
			
			node = pomNode;
		}
		size = 0;
	}
	
	/**
	 * Returns the object that is stored in linked list at position index.
	 * It never has the complexity greater than n/2+1.
	 *  
	 * @throws IndexOutOfBoundsException valid indexes are 0 to size-1
	 * @param index position of the Object we want to return
	 * @return Object that is stored at position index
	 */
	public Object get(int index) {
		if(this.isEmpty()) return null;
		if(index < 0 || index > size-1)
			throw new IndexOutOfBoundsException("Valid indexes are 0 to " + (size-1) + "!");
		
		ListNode node = null;
		if(index < size/2) {
			node = first;
			for(int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			node = last;
			for(int i = size-1; i > index; i--) {
				node = node.previous;
			}
		}
		return node.value;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * linked-list. Elements starting from this position are shifted one position.
	 * 
	 * @throws IndexOutOfBoundsException the legal positions are 0 to size (both are included)
	 * @throws NullPointerException Object value mustn't be <code>null</code>
	 * @param value the Object which we want to insert
	 * @param position index of array where the given value is inserted
	 */
	public void insert(Object value, int position) {
		if(value == null)
			throw new NullPointerException("You can not insert null to Collection!");
		if(position < 0 || position > size)
			throw new IndexOutOfBoundsException("Valid positions are 0 to " + size + "!");
		
		ListNode node = new ListNode(value);
		if(position == 0) {
			// adding of the only element in this List (list isEmpty)
			if (first == last) { 
				first = node;
				last = node;
			// adding at beginning in this List
			} else { 
				first.previous = node;
				node.next = first;
				first = node;
			}
		// adding of last element in this List
		} else if(position == size) {
			last.next = node;
			node.previous = last;
			last = node;
		// adding of a normal element which is somewhere else in this List
		} else {
			ListNode currentNode = first;
			for(int i = 0; i < position; i++) {
				currentNode = currentNode.next;
			}
			currentNode.previous.next = node;
			node.previous = currentNode.previous;
			node.next = currentNode;
			currentNode.previous = node;
			currentNode = node;
		}
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value
	 * or -1 if the value is not found.
	 * 
	 * @param value the Object which we are searching for
	 * @return int value (index of occurrence or -1)
	 */
	public int indexOf(Object value) {
		if(this.isEmpty() || value == null) return -1;
		
		ListNode node = first;
		for(int i = 0; i < size; i++) {
			if(value.equals(node.value)) return i;
			node = node.next;
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. Element that was previously at
	 * location index+1 after this operation is on location index, etc.
	 * 
	 * @throws IndexOutOfBounds legal indexes are 0 to size-1
	 * @param index position in array from which we want to remove an Object
	 */
	public void remove(int index) {
		if(this.isEmpty()) return;
		if(index<0 || index > size-1)
			throw new IndexOutOfBoundsException("Valid positions are 0 to " + (size-1) + "!");
		
		if(index == 0) {
			// removing of the only element in this List
			if (first == last) { 
				first = null;
				last = null;
			// removing of first element in this List
			} else { 
				first = first.next;
				first.previous = null;	// because it's the first element now
			}
		// removing of last element in this List
		} else if(index == size-1) {
			last = last.previous;
			last.next = null;	// because it's the last element now
		// removing of an normal element which is somewhere else in this List
		} else {
			ListNode node = first;
			for(int i = 0; i < index; i++) {
				node = node.next;
			}
			node.next.previous = node.previous;
			node.previous.next = node.next;
		}
		size--;
	}

}
