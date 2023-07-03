import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tokenizer {

    private ArrayList<String> allWords;
    public Tokenizer(String file) throws IOException {
        allWords = new ArrayList<>();
        FileReader wordFileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(wordFileReader);
        String line;
        while ((line = br.readLine()) != null){
            tokenizeLine(line);
        }
    }
    public Tokenizer(String[] text){
        allWords = new ArrayList<>();
        for (String line: text){
            tokenizeLine(line);
        }
    }
    String normalize(String token){
        String normalized = token.replaceAll("[^A-Za-z0-9]", "");
        return normalized.toLowerCase();
    }
    private void tokenizeLine(String line){
        String[] tokens = line.split("\\s+");
        // Tokenize and store all the words in an individual line
        for (String token: tokens) {
            String norm = normalize(token);
            if (norm.length() > 0){
                allWords.add(norm);
            }
        }
    }

    public ArrayList<String> wordList(){
        return allWords;
    }
}
