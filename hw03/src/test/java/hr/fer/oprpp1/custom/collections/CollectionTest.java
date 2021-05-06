package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CollectionTest {

	@Test
    public void testAddAllSatisfying() {
        Collection<String> col1 = new LinkedListIndexedCollection<>();
        Collection<String> col2 = new ArrayIndexedCollection<>();
        
        col1.add("Iva");
        col1.add("Anna");
        col1.add("Ante");
        col1.add("Filip");
        col1.add("Borna");
        
        col2.add("Luka");
        col2.add("Ivan");
        
        col2.addAllSatisfying(col1, value -> value.length() == 4);

        assertEquals(4, col2.size());
    }
	
	@Test
    public void testElementsGetter() {
        Collection<String> col = new ArrayIndexedCollection<>();
        
        col.add("Frane");
        col.add("Anna");
        col.add("Luka");
        
        ElementsGetter<String> getter = col.createElementsGetter();
        
        assertEquals("Frane", getter.getNextElement());
        
        StringBuilder sb = new StringBuilder();
        getter.processRemaining(sb::append);
        assertEquals("AnnaLuka", sb.toString());
    }
	
}
