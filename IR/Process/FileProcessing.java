package Process;

import Model.StopWords;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.stemmer.PorterStemmer;

public class FileProcessing {
    private final StopWords stopwords;
    private final PorterStemmer stemmer;
    
    public FileProcessing(){
        stopwords = new StopWords();
        stemmer = new PorterStemmer();
    }
    
    public File[] readFolder(String folderName){
        File folder = new File(folderName);
        File[] fileOfFolder = folder.listFiles();
        
        Arrays.sort(fileOfFolder, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2){
                int file1 = extractNumber(f1.getName());
                int file2 = extractNumber(f2.getName());

                return file1 - file2;
            }
            
            private int extractNumber(String name){
                int i = 0;
                
                int e = name.lastIndexOf(".");
                String news = name.substring(0, e);
                return Integer.parseInt(news);
            }
        });
        
        return fileOfFolder;
    }
    
    private String cleanSentence(String sentence){
        String results = sentence.replaceAll("[^a-zA-Z]", " ").toLowerCase();
        results = results.replaceAll("\\s+", " ");
        
        return results;
    }
    
    public String readFile(String fileName){
        String results = "";
        String line = null;
        BufferedReader file;
        
        try {
            file = new BufferedReader(new FileReader(fileName));
            while ((line = file.readLine()) != null){
                results += line;
            }
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(FileProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
    public TreeMap<String, Double> getFrequencyInDocument(String document){
        String results = cleanSentence(document);
        
        TreeMap<String, Double> tables = new TreeMap<>();
        String[] words = results.split(" ");
        Set<String> listStopWord = stopwords.getStopWords();
        
        for (String w : words){
            String s = stemmer.stem(w);
            if (!listStopWord.contains(s)){
                if (tables.containsKey(s)){
                    double value = tables.get(s) + 1.0;
                    tables.put(s, value);
                } else {
                    tables.put(s, 1.0);
                }
            }
        }
        
        return tables;
    }
}
