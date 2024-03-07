package datasource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GetWordsFromFile implements WordLoader {
    private String filePath;
    private Set<String> words;
    public GetWordsFromFile() {
        words = new HashSet<>();
        // filePath = "datasource/EnglishDictionary.csv";
        filePath = "src/main/java/datasource/EnglishDictionary.csv";
        loadSet();
    }

    public void loadSet() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] word = line.split(",");
                words.add(word[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getWords() {
        return this.words;
    }
}
