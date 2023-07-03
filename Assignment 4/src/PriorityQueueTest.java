import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PriorityQueueTest {
    private PriorityQueue<Integer, String> priorityQueue;

    @Before
    public void setUp() {
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void testAdd() {
        priorityQueue.add(3, "three");
        priorityQueue.add(1, "one");
        priorityQueue.add(2, "two");

        assertEquals(3, priorityQueue.size());
        assertEquals("three", priorityQueue.peek());
    }
    @Test
    public void testUpdate(){
        priorityQueue.add(3, "three");
        priorityQueue.add(1, "one");
        priorityQueue.add(2, "two");

        priorityQueue.update(4, "one");
        assertEquals(Integer.valueOf(4), priorityQueue.poll("one"));
    }

    @Test
    public void testPeek() {
        priorityQueue.add(3, "three");
        priorityQueue.add(1, "one");
        priorityQueue.add(2, "two");

        assertEquals("three", priorityQueue.peek());
    }

    @Test
    public void testPeekK() {
        priorityQueue.add(3, "three");
        priorityQueue.add(1, "one");
        priorityQueue.add(2, "two");

        String[] expectedValues = {"three", "two"};
        String[] actualValues = priorityQueue.peek(2);

        assertArrayEquals(expectedValues, actualValues);
        assertThrows(IllegalArgumentException.class, () -> priorityQueue.peek(5));
    }

    @Test
    public void testPoll() {
        priorityQueue.add(3, "three");
        priorityQueue.add(1, "one");
        priorityQueue.add(2, "two");

        assertEquals("three", priorityQueue.poll());
        assertEquals("two", priorityQueue.poll());
        assertEquals("one", priorityQueue.poll());
        assertThrows(NoSuchElementException.class, priorityQueue::poll);
    }

    @Test
    public void testPollV() {
        priorityQueue.add(3, "three");
        priorityQueue.add(1, "one");
        priorityQueue.add(2, "two");

        assertEquals(Integer.valueOf(1), priorityQueue.poll("one"));
        assertEquals(Integer.valueOf(2), priorityQueue.poll("two"));
        assertEquals(Integer.valueOf(3), priorityQueue.poll("three"));
        assertThrows(NoSuchElementException.class, () -> priorityQueue.poll("four"));
    }

    @Test
    public void testSize() {
        priorityQueue.add(3, "three");
        priorityQueue.add(1, "one");
        priorityQueue.add(2, "two");

        assertEquals(3, priorityQueue.size());
        priorityQueue.poll();
        assertEquals(2, priorityQueue.size());
    }
}
