package hr.fer.oprpp1.custom.collections;

/**
 * Instances of this class are a type of collection used for storing pairs of objects (keys
 * and values). Those pairs are instances of {@link DictionaryEntry}. In Dictionary duplicate
 * elements with the same key are not allowed.
 * 
 * @author lukasunara
 *
 * @param <K> type of Object which can represent a key
 * @param <V> type of Object which can represent a value
 */
public class Dictionary<K, V> {
	
	/** Instance of ArrayIndexedCollection used for actual element storage **/
	private ArrayIndexedCollection<DictionaryEntry<K, V>> entrySet;
	
	/**
	 * Class DictionaryEntry reprresents one pair of key and value (one Entry)
	 * of a {@link Dictionary}.
	 * 
	 * @author lukasunara
	 * 
	 * @param <K> type of Object which can represent a key
	 * @param <V> type of Object which can represent a value
	 */
	private static class DictionaryEntry<K, V> {
		
		/** Key of this Entry **/
		private K key;
		
		/** Value of this Entry **/
		private V value;
		
		/**
		 * Creates a new DictionaryEntry whith the given key and value.
		 * 
		 * @param key the Object which represents a key of an Entry
		 * @param value the Object which represents a value of an Entry
		 * @throws NullPointerException when the given key is <code>null</code>
		 */
		public DictionaryEntry(K key, V value) {
			super();
			if(key == null)
				throw new NullPointerException("Key mustn't be a null reference!");
			
			this.key = key;
			this.value = value;
		}
		
	}
	
	/** Default constructor creates an empty Dictionary **/
	public Dictionary() {
		super();
		this.entrySet = new ArrayIndexedCollection<DictionaryEntry<K, V>>();
	}
	
	/**
	 * Method returns true if this Dictionary contains no Entries and false otherwise.
	 * 
	 * @return boolean value which shows if this Dictionary is empty
	 */
	public boolean isEmpty() {
		return entrySet.isEmpty();
	}
	
	/**
	 * Counts the number of currently stored Entries in this Dictionary.
	 * 
	 * @return int value which shows the exact number of currently stored Entries
	 */
	public int size() {
		return entrySet.size();
	}
	
	/** Removes all Entries from this Dictionary. **/
	public void clear() {
		entrySet.clear();
	}
	
	/**
	 * Creates an Entry from the given key and value and puts it into this Dictionary.
	 * If an Entry with the same key already exists, it just sets the value to the given value.
	 * 
	 * @param key the Object which represents a key of an Entry
	 * @param value the Object which represents a value of an Entry
	 * @return <code>null</code> or value of the Entry if there is no Entry with the same key
	 * @throws NullPointerException when the given key is <code>null</code>
	 */
	public V put(K key, V value) {
		if(key == null)
			throw new NullPointerException("Key mustn't be a null reference!");
		
		ElementsGetter<DictionaryEntry<K, V>> eg = entrySet.createElementsGetter();
		DictionaryEntry<K, V> currentEntry = null;
		V oldValue = null;
		while(eg.hasNextElement()) {
			currentEntry = eg.getNextElement();
			if(currentEntry.key.equals(key)) {
				oldValue = currentEntry.value;
				currentEntry.value = value;
				return oldValue;
			}
		}
		DictionaryEntry<K, V> newEntry = new DictionaryEntry<>(key, value);
		entrySet.add(newEntry);

		return null;
	}
	
	/**
	 * Returns the value of Entry that is stored in Dictionary with the same key.
	 * 
	 * @param key the key value we are looking for
	 * @return V value of the Entry or <code>null</code> if there is no Entry with the same key
	 */
	public V get(Object key) {
		ElementsGetter<DictionaryEntry<K, V>> eg = entrySet.createElementsGetter();
		
		DictionaryEntry<K, V> currentEntry = null;
		while(eg.hasNextElement()) {
			currentEntry = eg.getNextElement();
			if(currentEntry.key.equals(key)) {
				return currentEntry.value;
			}
		}
		return null;
	}
	
	/**
	 * Removes the Entry with the same given key from this Dictionary and returns the value
	 * from that Entry.
	 * 
	 * @param key the key value we are looking for
	 * @return value of the Entry or <code>null</code> if there is no Entry with the same key
	 */
	public V remove(K key) {
		ElementsGetter<DictionaryEntry<K, V>> eg = entrySet.createElementsGetter();
		DictionaryEntry<K, V> currentEntry = null;
		V oldValue = null;
		while(eg.hasNextElement()) {
			currentEntry = eg.getNextElement();
			if(currentEntry.key.equals(key)) {
				oldValue = currentEntry.value;
				entrySet.remove(currentEntry);
				return oldValue;
			}
		}
		return null;
	}
	
}
