package puzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class Dictionary {
    private Trie trie;
    private String fileName;

    // Constructor
    public Dictionary(String fileName) {
        trie = new Trie();
        this.fileName = fileName;
        loadVocabulary();
    }

    // Method to load vocabulary into the trie
    private void loadVocabulary() {
        try (InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            Stream<String> lines = reader.lines();
            lines.map(String::toLowerCase).forEach(trie::insert); // Convert each word to lowercase before inserting
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a word is in the trie
    public boolean checkWord(String word) {
        return trie.checkWord(word);
    }

    // Method to check if a prefix is in the trie
    public boolean checkPrefix(String prefix) {
        return trie.checkPrefix(prefix);
    }

}