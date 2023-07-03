import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TokenizerTest {

    private Tokenizer tokenizer;

    @Before
    public void setUp() {
        String[] input = {
                "This is a sample text.",
                "It contains: numbers 123, symbols @#& and punctuations."
        };
        tokenizer = new Tokenizer(input);
    }

    @Test
    public void testTokenizerConstructor() throws IOException {
        String testFilePath = "test_file.txt";
        //Write test file
        FileWriter writer = new FileWriter(testFilePath);
        writer.write("This is a test file.");
        writer.close();
        Tokenizer tokenizerFromFile = new Tokenizer(testFilePath);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("this");
        expected.add("is");
        expected.add("a");
        expected.add("test");
        expected.add("file");
        assertEquals(expected, tokenizerFromFile.wordList());
    }

    @Test
    public void testNormalize() {
        String token = "@#&AbC-123dEF_*!.";
        String expected = "abc123def";
        assertEquals(expected, tokenizer.normalize(token));
        token = "I'm ";
        expected = "im";
        assertEquals(expected, tokenizer.normalize(token));
        token = "@#&";
        expected = "";
        assertEquals(expected, tokenizer.normalize(token));
    }

    @Test
    public void testWordList() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("this");
        expected.add("is");
        expected.add("a");
        expected.add("sample");
        expected.add("text");
        expected.add("it");
        expected.add("contains");
        expected.add("numbers");
        expected.add("123");
        expected.add("symbols");
        expected.add("and");
        expected.add("punctuations");
        assertEquals(expected, tokenizer.wordList());
    }
}
