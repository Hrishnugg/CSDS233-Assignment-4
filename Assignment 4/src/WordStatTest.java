import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(JUnit4.class)
public class WordStatTest {
    private WordStat wordStat;
    private String[] text = {"this", "is", "a", "sample", "text", "this", "is", "a", "test"};

    @Before
    public void setUp() {
        wordStat = new WordStat(text);
    }

    @Test
    public void testWordCount() {
        assertEquals(2, wordStat.wordCount("this"));
        assertEquals(2, wordStat.wordCount("is"));
        assertEquals(1, wordStat.wordCount("sample"));
        assertEquals(0, wordStat.wordCount("nonexistent"));
    }

    @Test
    public void testWordRank() {
        assertEquals(1, wordStat.wordRank("this"));
        assertEquals(1, wordStat.wordRank("is"));
        assertEquals(1, wordStat.wordRank("a"));
        assertEquals(4, wordStat.wordRank("sample"));
        assertEquals(0, wordStat.wordRank("nonexistent"));
    }

    @Test
    public void testMostCommonWords() {
        String[] mostCommon = {"this", "is", "a"};
        assertArrayEquals(mostCommon, wordStat.mostCommonWords(3));

        assertThrows(IllegalArgumentException.class, () -> wordStat.mostCommonWords(-1));
    }

    @Test
    public void testLeastCommonWords() {
        String[] leastCommon = {"test", "text", "sample"};
        assertArrayEquals(leastCommon, wordStat.leastCommonWords(3));

        assertThrows(IllegalArgumentException.class, () -> wordStat.leastCommonWords(-1));
    }

    @Test
    public void testMostCommonCollocations() {
        String[] collocationsPrecede = {"is"};
        assertArrayEquals(collocationsPrecede, wordStat.mostCommonCollocations(1, "this", false));

        String[] collocationsFollow = {"is", "a"};
        assertArrayEquals(collocationsFollow, wordStat.mostCommonCollocations(2, "this", false));

        assertThrows(IllegalArgumentException.class, () -> wordStat.mostCommonCollocations(-1, "this", true));
        assertThrows(IllegalArgumentException.class, () -> wordStat.mostCommonCollocations(1, "nonexistent", true));
    }
    @Test
    public void testLargeText(){
        // Test handling of large text files/sentences
        String[] text = new String[] {"This sentence is being used to make sure that our code works properly.",
        "This is another sentence that ensures that our code is sufficient.", "This was a very interesting assignment."};
        WordStat wordStat = new WordStat(text);
        String[] mostCommon = {"this", "is", "that"};
        assertArrayEquals(mostCommon, wordStat.mostCommonWords(3));
        String[] leastCommon = {"assignment", "interesting", "very"};
        assertArrayEquals(leastCommon, wordStat.leastCommonWords(3));
        String[] collocationsPrecede = {"this"};
        assertArrayEquals(collocationsPrecede, wordStat.mostCommonCollocations(1, "being", true));
        String[] collocationsFollow = {"is", "that"};
        assertArrayEquals(collocationsFollow, wordStat.mostCommonCollocations(2, "this", false));
    }
}
