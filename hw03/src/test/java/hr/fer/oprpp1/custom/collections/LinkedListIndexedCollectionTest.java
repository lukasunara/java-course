package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testDefaultConstructor() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
	
		assertEquals(0, list.size());
		assertNull(list.get(0));
		assertNull(list.get(list.size()-1));
	}
	
	@Test
	public void testCollectionConstructor() {
		LinkedListIndexedCollection<Integer> other1 = new LinkedListIndexedCollection<>();
		other1.add(1);
		other1.add(2);
		other1.add(3);
		other1.add(4);
		LinkedListIndexedCollection<Integer> list1 = new LinkedListIndexedCollection<>(other1);
		
		assertEquals(other1.size(), list1.size());
		assertEquals(other1.get(0), list1.get(0));
		assertEquals(other1.get(other1.size()-1), list1.get(list1.size()-1));
			
		ArrayIndexedCollection<Integer> other2 = new ArrayIndexedCollection<>();
		other2.add(1);
		other2.add(2);
		other2.add(3);
		other2.add(4);
		LinkedListIndexedCollection<Integer> list2 = new LinkedListIndexedCollection<>(other2);
		
		assertEquals(other2.size(), list2.size());
		assertEquals(other2.get(0), list2.get(0));
		assertEquals(other2.get(other2.size()-1), list2.get(list2.size()-1));
	}
		
	@Test
	public void testCollectionConstructorShouldThrow() {
		assertThrows(NullPointerException.class,
				() -> new LinkedListIndexedCollection<Object>(null));
	}
	
	@Test
	public void testAdd() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		
		assertEquals(5, list.size());
		
		for(int i = 0; i < list.size(); i++) {
			assertEquals(i+1, list.get(i));
		}
	}
		
	@Test
	public void testAddShouldThrow() {
		assertThrows(NullPointerException.class,
				() -> new LinkedListIndexedCollection<>().add(null));
	}
		
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
			
		assertEquals(2, list.indexOf(3));
		assertEquals(0, list.indexOf(1));
		assertEquals(4, list.indexOf(5));
		assertEquals(-1, list.indexOf(7));
	}
		
	@Test
	public void testClear() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
			
		list.clear();
		
		assertArrayEquals(new Object[0], list.toArray());
		assertEquals(0, list.size());
	}
		
	@Test
	public void testInsert() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(5);
			
		list.insert(4, 3);
			
		assertEquals(5, list.size());
		for(int i = 0; i < list.size(); i++) {
			assertEquals(i+1, list.toArray()[i]);
		}
	}
		
	@Test
	public void testInsertShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new LinkedListIndexedCollection<String>().insert("Ivan", 7));
			
		assertThrows(IndexOutOfBoundsException.class,
				() -> new LinkedListIndexedCollection<>().insert("Ivan", -9));
		
		assertThrows(NullPointerException.class,
				() -> new LinkedListIndexedCollection<>().insert(null, 3));
	}
		
	@Test
	public void testRemove() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		list.add(1);
		list.add(0);
		list.add(2);
		list.add(3);
		list.add(4);
		
		list.remove(1);
		
		assertEquals(4, list.size());
		for(int i = 0; i < list.size(); i++) {
			assertEquals(i+1, list.toArray()[i]);
		}
	}
		
		@Test
		public void testRemoveShouldThrow() {
			LinkedListIndexedCollection<Integer> other1 = new LinkedListIndexedCollection<>();
			other1.add(1);
			other1.add(2);
			other1.add(3);
			other1.add(4);
			
			assertThrows(IndexOutOfBoundsException.class,
					() -> other1.remove(5));
			
			assertThrows(IndexOutOfBoundsException.class,
					() -> other1.remove(-1));
		}
		
		@Test
		public void testGet() {
			LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
			list.add("Anna");
			list.add("Petra");
			
			assertEquals("Petra", list.get(1));
		}
		
		@Test
		public void testGetShouldThrow() {
			LinkedListIndexedCollection<Integer> other1 = new LinkedListIndexedCollection<>();
			other1.add(1);
			other1.add(2);
			other1.add(3);
			other1.add(4);
			
			assertThrows(IndexOutOfBoundsException.class,
					() -> other1.get(5));
			
			assertThrows(IndexOutOfBoundsException.class,
					() -> other1.get(-1));
		}
		
		@Test
		public void testToArray() {
			LinkedListIndexedCollection<String> col = new LinkedListIndexedCollection<>();
			col.add("Sutra");
			col.add("je");
			col.add("lijep");
			col.add("dan");
			
			Object[] array = {"Sutra", "je", "lijep", "dan"};
			
			assertArrayEquals(array, col.toArray());
		}

}
