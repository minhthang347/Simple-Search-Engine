package Model;

import Process.FileProcessing;
import Process.TermProcessing;
import Model.Term;
import Model.PostingList;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RetrievelResults {
    private TreeMap<String, Double> queries;
    private Hashtable<String, Double> results;
    private TermProcessing process;
    private FileProcessing processFile;
    
    public RetrievelResults(){
        queries = new TreeMap<>();
        results = new Hashtable<>();
        process = new TermProcessing();
        processFile = new FileProcessing();
    }
    
    public void ProcessFile(String forder_name){
        File[] files = processFile.readFolder(forder_name);
        for (File f : files){
            String document = processFile.readFile(forder_name + "/" + f.getName());
            TreeMap<String, Double> words = processFile.getFrequencyInDocument(document);
            
            Set<String> keys = words.keySet();
            keys.forEach((str)->{
                process.AddWord(str, f.getName(), words.get(str));
            });
        }
        
        process.setWeight();
    }
    
    public void ResultOfQuery(String sentence){
        queries = processFile.getFrequencyInDocument(sentence);
        Set<String> keys = queries.keySet();
        
        keys.forEach((key)->{
            if (process.getTermList().containsKey(key)){
                Term d = process.getTermList().get(key);            
                ArrayList<PostingList> posts = d.getPosting();

                posts.forEach((p) -> {
                    String doc = p.getPositionOfDocument();
                    double w = (double) queries.get(key);
                    if (results.containsKey(doc)){
                        double value = results.get(doc) + w * p.getWeight();
                        results.put(doc, value);
                    } else {
                        double value = w * p.getWeight();
                        results.put(doc, value);
                    }
                });
            }
        });
    }
    
    public ArrayList<Map.Entry<String, Double>> getResults(){
        return getSortedValues();
    }
    
    public ArrayList<Map.Entry<String, Double>> getSortedValues(){
        ArrayList<Map.Entry<String, Double>> l = new ArrayList(results.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        
        return l;
    }
} 