package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
    public void testPutAndGet() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        dictionary.put("Third", 3);
        dictionary.put("Fourth", 4);

        assertEquals(1, dictionary.get("First"));
        assertNull(dictionary.get(3412));
        
        dictionary.put("First", 44);
        assertEquals(44, dictionary.get("First"));
    }

    @Test
    public void testRemove() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        dictionary.put("Third", 3);
        dictionary.put("Fourth", 4);

        assertEquals(1, dictionary.remove("First"));
        assertNull(dictionary.get("First"));
        assertNull(dictionary.remove("First"));
    }

    @Test
    public void testSize(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        dictionary.put("Third", 3);
        dictionary.put("Fourth", 4);

        assertEquals(4, dictionary.size());
    }

    @Test
    public void testEmpty(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testClear(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        dictionary.put("Third", 3);
        dictionary.put("Fourth", 4);

        dictionary.clear();

        assertTrue(dictionary.isEmpty());
    }

}
