package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Instances of this class represent a simple Hashtable (table with slots in which
 * can be stored multiple Objects; those slots act like a single linked list).
 * 
 * @author lukasunara
 *
 * @param <K> type of Object which can represent a key
 * @param <V> type of Object which can represent a value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	/** Constant used for overfill check **/
	private static final double OVERFILL = 0.75;

	/** The default capacity (number of slots) of this Hashtable **/
	private static int DEFAULT_CAPACITY = 16;
	
	/** Array whose element actually point to the slots of this Hashtable **/
	private TableEntry<K,V>[] table;
	
	/** Current number of elements in this Hashtable **/
	private int size;
	
	/** Used to see if collection was altered (altered when != 0) **/
	private long modificationCount;
	
	/** Default constructor creates a new empty Hashtable with default capacity. **/
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		super();
		this.table = (TableEntry<K, V>[])new TableEntry[DEFAULT_CAPACITY];
		this.size = 0;
		this.modificationCount = 0L;
	}
	
	/**
	 * Constructor creates an empty Hashtable with capacity which is equal to the first
	 * power of number 2 bigger than the given capacity. 
	 * 
	 * @param capacity int value used for calculating capacity of Hashtable
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		super();
		if(capacity < 1)
			throw new IllegalArgumentException("The capacity cannot be < 1 !");
		
		int newCapacity = 1;
		if(capacity%2 != 0) {
			while(capacity > newCapacity) newCapacity *= 2;
		} else {
			newCapacity = capacity;
		}
		this.table = (TableEntry<K, V>[])new TableEntry[newCapacity];
		this.size = 0;
		this.modificationCount = 0L;
	}
	
	/**
	 * Class TableEntry represents one pair of key and value (one Entry) of a {@link SimpleHashtable}.
	 * 
	 * @author lukasunara
	 * 
	 * @param <K> type of Object which can represent a key
	 * @param <V> type of Object which can represent a value
	 */
	public static class TableEntry<K, V> {
		
		/** Key of this Entry **/
		private K key;
		
		/** Value of this Entry **/
		private V value;
		
		/** Pointer to the next Entry in the same slot of this Hashtable **/
		private TableEntry<K, V> next;
		
		/**
		 * Creates a new TableEntry whith the given key and value.
		 * 
		 * @param key the Object which represents a key of an Entry
		 * @param value the Object which represents a value of an Entry
		 * @throws NullPointerException when the given key is <code>null</code>
		 */
		public TableEntry(K key, V value) {
			super();
			if(key == null)
				throw new NullPointerException("Key mustn't be a null reference!");
			
			this.key = key;
			this.value = value;
			this.next = null;
		}

		/**
		 * Public getter method for key of this Entry.
		 * 
		 * @return key of this Entry
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Public getter method for value of this Entry.
		 * 
		 * @return value of this Entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Public setter method for value of this Entry.
		 * 
		 * @param value the given value which is used to set the value of this Entry
		 */
		public void setValue(V value) {
			this.value = value;
		}

	}
	
	/**
	 * Counts the number of currently stored Entries in this Dictionary.
	 * 
	 * @return int value which shows the exact number of currently stored Entries
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Method returns true if this Dictionary contains no Entries and false otherwise.
	 * 
	 * @return boolean value which shows if this Dictionary is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Creates a TableEntry from the given key and value and puts it into this Hashtable.
	 * If an Entry with the same key already exists, it just sets its value to the given value.
	 * 
	 * @param key the Object which represents a key of the Entry
	 * @param value the Object which represents a value of the Entry
	 * @return <code>null</code> or value of the Entry if there is no Entry with the same key
	 * @throws NullPointerException when the given key is <code>null</code>
	 */
	public V put(K key, V value) {
		if(key == null)
			throw new NullPointerException("Key mustn't be a null reference!");
		
		checkComplexityOfTable();
		
		int slot = Math.abs(key.hashCode()) % table.length;
		
		if(table[slot] != null) {
			TableEntry<K, V> currentEntry = table[slot];
			V oldValue = null;
			while(currentEntry.next != null) {
				if(currentEntry.key.equals(key)) {
					oldValue = currentEntry.value;
					currentEntry.value = value;
					return oldValue;
				}
				currentEntry = currentEntry.next;
			}
			currentEntry.next = new TableEntry<>(key, value);
		} else {
			table[slot] = new TableEntry<>(key, value);
		}
		size++;
		modificationCount++;
		
		return null;
	}
	
	/**
	 * Returns the value of Entry that is stored in this Hashtable with the same key.
	 * 
	 * @param key the key value we are looking for
	 * @return V value of the Entry or <code>null</code> if there is no Entry with the same key
	 */
	public V get(Object key) {
		TableEntry<K, V> searchedEntry = findKey(key);
		if(searchedEntry != null) return searchedEntry.value;

		return null;
	}
	
	/**
	 * Removes the Entry with the same given key from this Dictionary and returns the value
	 * from that Entry.
	 * 
	 * @param key the key value we are looking for
	 * @return value of the Entry or <code>null</code> if there is no Entry with the same key
	 */
	public V remove(Object key) {
		if(key == null) return null;
		
		int slot = Math.abs(key.hashCode()) % table.length;
		
		V oldValue = null;
		
		if(table[slot]!=null && table[slot].key.equals(key)) {
			oldValue = table[slot].value;
			table[slot] = table[slot].next;
			modificationCount++;
			return oldValue;
		}
		
		TableEntry<K, V> currentEntry = table[slot];
		while(currentEntry != null) {
			if(currentEntry.next!=null && currentEntry.next.key.equals(key)) {
				oldValue = currentEntry.next.value;
				currentEntry.next = currentEntry.next.next; // removing of next Entry
				size--;
				modificationCount++;
				return oldValue;
			}
			currentEntry = currentEntry.next;
		}
		return null;
	}

	/**
	 * Searches for an Entry which has the same key as the given key.
	 * 
	 * @param key the key which we are looking for
	 * @return true if given key exists, false otherwise
	 */
	public boolean containsKey(Object key) {
		return findKey(key) != null;
	}
	
	/**
	 * Searches for an Entry which has the same value as the given value.
	 * 
	 * @param value the value which we are looking for
	 * @return true if given value exists, false otherwise
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> currentEntry = null;
		for(int i = 0; i < table.length; i++) {
			currentEntry = table[i];
			while(currentEntry != null) {
				if(currentEntry.value.equals(value)) return true;
				currentEntry = currentEntry.next;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		TableEntry<K, V> currentEntry = null;
		for(int i = 0; i < table.length; i++) {
			currentEntry = table[i];
			while(currentEntry != null) {
				sb.append(currentEntry.key).append("=").append(currentEntry.value).append(", ");
				currentEntry = currentEntry.next;
			}
		}
		sb.delete(sb.length()-2, sb.length()).append("]");
		
		return sb.toString();
	}
	
	/**
	 * Creates and returns a new array which contains all entries from this Hashtable.
	 * 
	 * @return the new array with all entries from this hashtable
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K, V>[] newArray = (TableEntry<K, V>[])new TableEntry[size];
		
		int arrayIndex = 0;
		TableEntry<K, V> currentEntry = null;
		for(int i = 0; i < table.length; i++) {
			currentEntry = table[i];
			while(currentEntry != null) {
				newArray[arrayIndex++] = currentEntry;
				currentEntry = currentEntry.next;
			}
		}
		return newArray;
	}
	
	/** Removes all Entries from this Hashtable. **/
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
			modificationCount++;
		}
		size = 0;
	}
	
	/** Finds Entry with the given key (used by many important methods) **/
	private TableEntry<K, V> findKey(Object key) {
		if(key == null)	return null;

		int slot = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K, V> currentEntry = table[slot];
		while(currentEntry != null) {
			if(currentEntry.key.equals(key)) {
				break;
			}
			currentEntry = currentEntry.next;
		}
		return currentEntry;
	}
	
	/** Checks if the slots are overfilled, if so expand table capacity by double **/
	private void checkComplexityOfTable() {
		if(size/table.length < OVERFILL*table.length) return;
		
		TableEntry<K, V>[] newArray = this.toArray();
		this.clear();
		table = Arrays.copyOf(table, table.length*2);
		
		for(TableEntry<K, V> entry : newArray) this.put(entry.key, entry.value);
	}

	/**
	 * Implements the {@link Iterator} interface, because it's going to specify in which
	 * way does the IteratorImpl iterate over every element of the {@link SimpleHashtable}.
	 * 
	 * @author lukasunara
	 * 
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		/** Points on the current element while iterating over the table **/
		private int index;
		
		/** The entry we are currently iterating over **/
		private SimpleHashtable.TableEntry<K, V> lastEntry;
		
		/** Is used to know if we already iterated over this element **/
		private boolean alreadyIterated;
		
		/** Is used to know if we already deleted an element **/
		private boolean alreadyDeleted;
		
		/** Used to see if collection was altered (altered when != modificationCount) **/
		private long savedModificationCount;
		
		/**
		 * Creates an instance of {@link IteratorImpl}.
		 * 
		 * @param arrayCollection is the collection we want to iterate over.
		 */
		public IteratorImpl() {
			super();
			this.index = 0;
			this.savedModificationCount = modificationCount;
			this.alreadyIterated = false;
			this.lastEntry = findFirst();
		}
		
		/** Finds the first entry != <code>null</code> **/
		private SimpleHashtable.TableEntry<K, V> findFirst() {
			while(index < table.length) {
				if(table[index] != null) return table[index];
				index++;
			}
			return null;
		}
		
		/**
		 * Checks if there exists another element in the {@link SimpleHashtable}.
		 * 
		 * @throws ConcurrentModificationException if collection has been altered
		 */
		@Override
		public boolean hasNext() {
			if(savedModificationCount != modificationCount)
				throw new ConcurrentModificationException("SimpleHashtable has been altered!");
			
			if(lastEntry == null || alreadyIterated) {
				if(lastEntry.next == null) {
					int newIndex = index+1;
					while(newIndex < table.length) {
						if(table[newIndex] != null) return true;
						newIndex++;
					}
					return false;
				}
			}
			return true;
		}
		
		/**
		 * Checks if there exists another element in the {@link SimpleHashtable}, returns
		 * the Object and moves past it (iterates over an element in the table).
		 * 
		 * @throws NoSuchElementException if hasNext() returns false
		 * @throws ConcurrentModificationException if collection has been altered
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if(savedModificationCount != modificationCount)
				throw new ConcurrentModificationException("SimpleHashtable has been altered!");
			if(!hasNext())
				throw new NoSuchElementException("SimpleHashtable is empty!");
			
			if(lastEntry == null || alreadyIterated) {
				if(lastEntry.next == null) {
					while(++index < table.length) {
						if(table[index] != null) {
							lastEntry = table[index];
							break;
						}
					}
				} else {
					lastEntry = lastEntry.next;
				}
				alreadyIterated = false;
			}
			alreadyIterated = true;
			alreadyDeleted = false;
			
			return lastEntry;
		}
		
		/**
		 * Removes from the Hashtable the last element returned by this iterator. This method can
		 * be called only once per call to next().
		 * 
		 * @throws ConcurrentModificationException if collection has been altered
		 * @throws IllegalStateException if the next method has not yet been called, or the remove
		 * method has already been called after the last call to the next method
		 */
		public void remove() {
			if(lastEntry == null || alreadyDeleted)
				throw new IllegalStateException("Method remove() has already been called for the same entry!");
			if(savedModificationCount != modificationCount)
				throw new ConcurrentModificationException("SimpleHashtable has been altered!");

			if(SimpleHashtable.this.containsKey(lastEntry.key)) {
				TableEntry<K, V> pastEntry = null;
				if(this.hasNext()) {
					pastEntry = lastEntry;
					lastEntry = this.next();
				}
				if(SimpleHashtable.this.remove(pastEntry.key) != null) {
					alreadyDeleted = true;
					savedModificationCount++;
					alreadyIterated = false; // enables normal iteration after remove
				}
			}
		}
	}
	
	/**
	 * Creates an {@link Iterator} object which is used to iterate over this
	 * {@link SimpleHashtable}.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
}
