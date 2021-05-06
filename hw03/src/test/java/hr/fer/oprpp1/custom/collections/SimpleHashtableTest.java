package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	@Test
    public void testPut() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 5);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Filip", 1);

        assertEquals(2, hashTable.get("Ante"));
        assertEquals(5, hashTable.get("Ivana"));
        assertEquals(5, hashTable.size());
    }

    @Test
    public void testToString() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana

        assertEquals("[Ante=2, Ivana=5, Jasna=2]", hashTable.toString());
    }

    @Test
    public void testContainsKey() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Josip", 10);

        assertTrue(hashTable.containsKey("Kristina"));
    }

    @Test
    public void testContainsValue() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Josip", 10);

        assertTrue(hashTable.containsValue(10));
    }

    @Test
    public void testRemove() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Josip", 10);

        hashTable.remove("Ivana");

        assertFalse(hashTable.containsKey("Ivana"));
        assertTrue(hashTable.containsKey("Jasna"));
        
        hashTable.remove("Ante");
        
        assertFalse(hashTable.containsKey("Ante"));
    }

    @Test
    public void testIteratorImplWithForEach() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Josip", 100);

        StringBuilder result = new StringBuilder();

        for(var element : hashTable) {
            result.append(element.getKey()).append(element.getValue());
        }

        assertEquals("Josip100Ante2Ivana5Jasna2Kristina2", result.toString());
    }

    @Test
    public void testIteratorImplRemove() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Josip", 10);

        var it = hashTable.iterator();

        while(it.hasNext()) {
        	if(it.next().getKey().equals("Ivana")) it.remove();
        }
        assertFalse(hashTable.containsKey("Ivana"));
    }

    @Test
    public void testIteratorImplNextThrowsNoSuchElement() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);

        var it = hashTable.iterator();

        it.next();
        it.next();

        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    public void testIteratorImplNextThrowsConcurrentModification() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);

        var it = hashTable.iterator();

        it.next();
        hashTable.put("Lucija", 2);

        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    public void testIteratorImplRemoveThrowsIllegalState() {
        SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Josip", 10);

        var it = hashTable.iterator();
        
        assertThrows(IllegalStateException.class, () -> {
            while(it.hasNext()) {
                if(it.next().getKey().equals("Ivana")) {
                    it.remove();
                    it.remove();
                }
            }
        });
    }

    @Test
    public void testTwoIterators() {
    	SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);

        hashTable.put("Ivana", 2);
        hashTable.put("Ante", 2);
        hashTable.put("Jasna", 2);
        hashTable.put("Kristina", 2);
        hashTable.put("Ivana", 5); // overwrites old grade for Ivana
        hashTable.put("Josip", 10);

        var it1 = hashTable.iterator();
        var it2 = hashTable.iterator();

        assertThrows(ConcurrentModificationException.class, () -> {
        	while(it1.hasNext()) {
        		while(it2.hasNext()) {
        			if(it2.next().getKey().equals("Ivana")) it2.remove();
        		}
        	}        	
        });
    }
    
}
