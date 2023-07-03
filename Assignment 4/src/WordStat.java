import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class WordStat {
    private ArrayList<String> words;
    private HashTable<Integer> wordCounts;
    private ArrayList<String> uniqueWords;
    private HashTable<Integer> wordRanks;
    private HashTable<Integer> wordPositions;
    public WordStat(String file) throws IOException {
        uniqueWords = new ArrayList<>();
        // Tokenize the file
        Tokenizer tokenizer = new Tokenizer(file);
        words = tokenizer.wordList();
        computeWordCounts();
        computeWordRanks();
        computeWordPositions();
    }
    public WordStat(String[] text){
        uniqueWords = new ArrayList<>();
        // Tokenize the text
        Tokenizer tokenizer = new Tokenizer(text);
        words = tokenizer.wordList();
        computeWordCounts();
        computeWordRanks();
        computeWordPositions();
    }
    private void computeWordCounts(){
        wordCounts = new HashTable<Integer>();
        for (String word: words){
            int count = 0;
            try{
                count = wordCounts.get(word);
            } catch (NoSuchElementException e){
                // Build a list of unique words
                uniqueWords.add(word);
            }
            wordCounts.put(word, count + 1);
        }
    }
    public int wordCount (String word){
        int count = 0;
        try{
            count = wordCounts.get(word);
        } catch (NoSuchElementException e){
            // Do nothing
        }
        return count;
    }

    private void computeWordRanks(){
        Collections.sort(uniqueWords, (w1, w2) -> {
            int count1 = wordCounts.get(w1);
            int count2 = wordCounts.get(w2);
            return count2 - count1;
        });
        wordRanks = new HashTable<Integer>();
        int rank = 1;
        String previousWord = uniqueWords.get(0);
        wordRanks.put(previousWord, rank);
        for (int i = 1; i < uniqueWords.size(); i++){
            String word = uniqueWords.get(i);
            int count = wordCounts.get(word);
            int prevCount = wordCounts.get(previousWord);
            // Increment rank if the word count is less than the previous word
            if(count < prevCount){
                rank = i + 1;
            }
            previousWord = word;
            wordRanks.put(word, rank);
        }
    }
    public int wordRank(String word){
        int rank = 0;
        // If the word is not in the list, return 0
        try{
            rank = wordRanks.get(word);
        } catch (NoSuchElementException e){
            // Do nothing
        }
        return rank;
    }
    public String[] mostCommonWords(int k) throws IllegalArgumentException {
        if (k < 0){
            throw new IllegalArgumentException("k must not be negative");
        }
        // If k is greater than the number of unique words, set it to be the number of unique words
        if (k > uniqueWords.size()){
            k = uniqueWords.size();
        }
        String[] mostCommon = new String[k];
        for (int i = 0; i < k; i++){
            mostCommon[i] = uniqueWords.get(i);
        }
        return mostCommon;
    }
    public String[] leastCommonWords(int k) throws IllegalArgumentException {
        if (k < 0){
            throw new IllegalArgumentException("k must not be negative");
        }
        // If k is greater than the number of unique words, set it to be the number of unique words
        if (k > uniqueWords.size()){
            k = uniqueWords.size();
        }
        String[] leastCommon = new String[k];
        for (int i = 0; i < k; i++){
            leastCommon[i] = uniqueWords.get(uniqueWords.size() - i - 1);
        }
        return leastCommon;
    }
    private void computeWordPositions(){
        wordPositions = new HashTable<Integer>();
        for (int i = 0; i < words.size(); i++){
            String word = words.get(i);
            // Store only first occurrence of each word.
            try{
                wordPositions.get(word);
            } catch (NoSuchElementException e){
                wordPositions.put(word, i);
            }
        }
    }
    public String[] mostCommonCollocations(int k, String baseWord, boolean precede) throws IllegalArgumentException {
        if (k < 0){
            throw new IllegalArgumentException("k must not be negative");
        }
        // If k is greater than the number of unique words, set it to be the number of unique words
        if (k > uniqueWords.size()){
            k = uniqueWords.size();
        }
        int baseWordPosition = 0;
        ArrayList<String> allUniqueWordsBeforeOrAfter = new ArrayList<String>();
        try{
            baseWordPosition = wordPositions.get(baseWord);
        }
        catch (NoSuchElementException e){
            throw new IllegalArgumentException("baseWord must be a word in the text");
        }
        // If precede is true, get all words before the base word
        if (precede){
            for (int i = 0; i < baseWordPosition; i++){
                String word = words.get(i);
                if (!allUniqueWordsBeforeOrAfter.contains(word)){
                    allUniqueWordsBeforeOrAfter.add(word);
                }
            }
        }
        // If precede is false, get all words after the base word
        else {
            for (int i = baseWordPosition + 1; i < words.size(); i++){
                String word = words.get(i);
                if (!allUniqueWordsBeforeOrAfter.contains(word)){
                    allUniqueWordsBeforeOrAfter.add(word);
                }
            }
        }
        Collections.sort(allUniqueWordsBeforeOrAfter, (w1, w2) -> {
            int count1 = wordCounts.get(w1);
            int count2 = wordCounts.get(w2);
            return count2 - count1;
        });
        // If k is greater than the number of unique words before or after the base word, set it to be the number of unique words before or after the base word
        if (k > allUniqueWordsBeforeOrAfter.size()){
            k = allUniqueWordsBeforeOrAfter.size();
        }
        String[] mostCommon = new String[k];
        for (int i = 0; i < k; i++){
            mostCommon[i] = allUniqueWordsBeforeOrAfter.get(i);
        }
        return mostCommon;
    }
}
