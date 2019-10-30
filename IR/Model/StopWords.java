package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StopWords {
    private Set<String> stopwords;
    
    public StopWords(){
        stopwords = new HashSet<>();
    }
    
    private void readFileStopWords(){
        String file = "./stopword.txt";
        
        BufferedReader fileStopWord;
        try {
            fileStopWord = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = fileStopWord.readLine()) != null){
                line = line.trim();
                stopwords.add(line);
            }

            fileStopWord.close();
        } catch (IOException ex) {
            Logger.getLogger(StopWords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Set<String> getStopWords(){
        readFileStopWords();
        return stopwords;
    }
    
}