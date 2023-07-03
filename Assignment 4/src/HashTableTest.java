import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

@RunWith(JUnit4.class)
public class HashTableTest {
    private HashTable<String> hashTable;

    @Before
    public void setUp() {
        hashTable = new HashTable<>();
        hashTable.put("key1", "value1");
        hashTable.put("key2", "value2");
        hashTable.put("key3", "value3");
    }

    @Test
    public void testPutAndGet() {
        assertEquals("value1", hashTable.get("key1"));
        assertEquals("value2", hashTable.get("key2"));
        assertEquals("value3", hashTable.get("key3"));

        hashTable.put("key1", "newValue1");
        assertEquals("newValue1", hashTable.get("key1"));
    }

    @Test
    public void testGetNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> hashTable.get("nonexistentKey"));
    }

    @Test
    public void testRemove() {
        assertEquals("value1", hashTable.remove("key1"));
        assertThrows(NoSuchElementException.class, () -> hashTable.get("key1"));

        assertEquals("value2", hashTable.remove("key2"));
        assertThrows(NoSuchElementException.class, () -> hashTable.get("key2"));
    }

    @Test
    public void testRemoveNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> hashTable.remove("nonexistentKey"));
    }

    @Test
    public void testSize() {
        assertEquals(3, hashTable.size());

        hashTable.remove("key1");
        assertEquals(2, hashTable.size());

        hashTable.put("key4", "value4");
        assertEquals(3, hashTable.size());
    }
    @Test
    public void testRehash(){
        for (int i = 0; i < 100; i++){
            hashTable.put("key" + i, "value" + i);
        }
        for (int i = 0; i < 100; i++){
            assertEquals("value" + i, hashTable.get("key" + i));
        }
    }
}
