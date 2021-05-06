package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	@Test
	public void testDefaultConstructor() {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>();
	
		assertEquals(0, col.size());
	}

	@Test
	public void testInitialCapacityConstructor() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(5);
		
		assertEquals(0, col.size());
	}
	
	@Test
	public void testInitialCapacityConstructorShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ArrayIndexedCollection<>(-2));
	}
	
	@Test
	public void testCollectionConstructor() {
		ArrayIndexedCollection<Double> other = new ArrayIndexedCollection<>();
		
		other.add(1.25);
		other.add(2.52);
		other.add(3.44);
		other.add(4.87);
		
		ArrayIndexedCollection<Double> col = new ArrayIndexedCollection<>(other);
		
		assertEquals(other.size(), col.size());
		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
	}
	
	@Test
	public void testCollectionConstructorBiggerSize() {
		ArrayIndexedCollection<Integer> other = new ArrayIndexedCollection<>();
		
		for(int i = 0; i < 20; i++) {
			other.add(i);
		}
		
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(other);
		
		assertEquals(other.size(), col.size());

		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
		
	}
	
	@Test
	public void testCollectionConstructorShouldThrow() {
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection<Object>(null));
	}
	
	@Test
	public void testCollectionAndCapacityConstructor() {
		ArrayIndexedCollection<Integer> other = new ArrayIndexedCollection<>();
		
		other.add(1);
		other.add(2);
		other.add(3);
		other.add(4);
		
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(other, 5);
		
		assertEquals(other.size(), col.size());
		
		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
		
	}
	
	@Test
	public void testCollectionAndCapacityConstructorBiggerSize() {
		ArrayIndexedCollection<Integer> other = new ArrayIndexedCollection<>();
		
		other.add(1);
		other.add(2);
		other.add(3);
		other.add(4);
		
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(other, 2);
		
		assertEquals(other.size(), col.size());
		
		for(int i = 0; i < col.size(); i++) {
			assertEquals(other.get(i), col.get(i));
		}
	}
	
	@Test
	public void testCollectionAndCapacityConstructorShouldThrow() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<>(null, 3));
		
		assertThrows(IllegalArgumentException.class,
				() -> new ArrayIndexedCollection<Object>(new ArrayIndexedCollection<>(), -2));
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		
		col.add(1);
		col.add(2);
		col.add(3);
		col.add(4);
		col.add(5);
		
		assertEquals(5, col.size());
		
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}
	}
	
	@Test
	public void testAddShouldThrow() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<>().add(null));
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		
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
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(5);
		
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
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(5);

		col.add(1);
		col.add(2);
		col.add(3);
		col.add(5);
		
		col.insert(4, 3);
		
		assertEquals(5, col.size());
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}

		ArrayIndexedCollection<Integer> other = new ArrayIndexedCollection<>(2);
		other.add(1);
		other.add(2);
		other.add(4);
		
		other.insert(3, 2);
		
		assertEquals(4, other.size());
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}
	}
	
	@Test
	public void testInsertShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection<>(5).insert("Ivan", 7));
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection<>().insert("Ivan", -9));
	
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection<>().insert(null, 3));
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>(5);

		col.add(1);
		col.add(0);
		col.add(2);
		col.add(3);
		col.add(4);
		
		col.remove(1);
		
		assertEquals(4, col.size());
		for(int i = 0; i < col.size(); i++) {
			assertEquals(i+1, col.get(i));
		}
	}
	
	@Test
	public void testRemoveShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection<>().remove(5));
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection<>().remove(-1));
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>(2);
		col.add("Filip");
		col.add("Franjo");
		
		assertEquals("Franjo", col.get(1));
	}
	
	@Test
	public void testToArray() {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>(2);
		col.add("Sutra");
		col.add("je");
		col.add("lijep");
		col.add("dan");
		
		Object[] array = {"Sutra", "je", "lijep", "dan"};
		
		assertArrayEquals(array, col.toArray());
	}
	
	@Test
	public void testGetShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection<String>(2).get(5));
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection<>().get(-1));
	}
}
