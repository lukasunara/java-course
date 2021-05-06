package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	final int GOOD_INITIAL_CAPACITY = 5;
	final int NEGATIVE_INITIAL_CAPACITY = -4;
	final int SMALL_INITIAL_CAPACITY = 2;
	
	@Test
	public void testDefaultConstructor() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
	
		assertEquals(0, col.size());
	}

	@Test
	public void testInitialCapacityConstructor() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY);
		
		assertEquals(0, col.size());
	}
	
	@Test
	public void testInitialCapacityConstructorShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ArrayIndexedCollection(NEGATIVE_INITIAL_CAPACITY));
	}
	
	@Test
	public void testCollectionConstructor() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
		other.add(1);
		other.add(2);
		other.add(3);
		other.add(4);
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(other);
		
		assertEquals(other.size(), col.size());
		
		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
	}
	
	@Test
	public void testCollectionConstructorBiggerSize() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
		for(int i = 0; i < 20; i++) {
			other.add(i);
		}
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(other);
		
		assertEquals(other.size(), col.size());

		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
		
	}
	
	@Test
	public void testCollectionConstructorShouldThrow() {
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testCollectionAndCapacityConstructor() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
		other.add(1);
		other.add(2);
		other.add(3);
		other.add(4);
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(other, GOOD_INITIAL_CAPACITY);
		
		assertEquals(other.size(), col.size());
		
		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
		
	}
	
	@Test
	public void testCollectionAndCapacityConstructorBiggerSize() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
		other.add(1);
		other.add(2);
		other.add(3);
		other.add(4);
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(other, SMALL_INITIAL_CAPACITY);
		
		assertEquals(other.size(), col.size());
		
		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
	}
	
	@Test
	public void testCollectionAndCapacityConstructorShouldThrow() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
		
		assertThrows(IllegalArgumentException.class,
				() -> new ArrayIndexedCollection(NEGATIVE_INITIAL_CAPACITY));
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		assertEquals(GOOD_INITIAL_CAPACITY, col.size());
		
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}
	}
	
	@Test
	public void testAddShouldThrow() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection().add(null));
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		assertEquals(2, col.indexOf(3));
		assertEquals(0, col.indexOf(1));
		assertEquals(4, col.indexOf(5));
		assertEquals(-1, col.indexOf(7));
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY);
		
		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		col.clear();
		
		assertEquals(0, col.size());
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(5);
		
		col.insert(4, 3);
		
		assertEquals(GOOD_INITIAL_CAPACITY, col.size());
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}

		ArrayIndexedCollection other = new ArrayIndexedCollection(SMALL_INITIAL_CAPACITY + 1);
		other.add(1);
		other.add(2);
		other.add(4);
		
		other.insert(3, 2);
		
		assertEquals(GOOD_INITIAL_CAPACITY-1, other.size());
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}
	}
	
	@Test
	public void testInsertShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY).insert("Ivan", 7));
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY).insert("Ivan", -9));
	
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY).insert(null, 3));
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY);

		col.add(1);
		col.add(0);
		col.add(2);
		col.add(3);
		col.add(4);
		
		col.remove(1);
		
		assertEquals(GOOD_INITIAL_CAPACITY-1, col.size());
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}
	}
	
	@Test
	public void testRemoveShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY).remove(5));
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY).remove(-1));
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(SMALL_INITIAL_CAPACITY);
		col.add(1);
		col.add(2);
		
		assertEquals(2, col.get(1));
	}
	
	@Test
	public void testGetShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY).get(5));
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(GOOD_INITIAL_CAPACITY).get(-1));
	}
}
