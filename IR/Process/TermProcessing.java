package Process;

import Model.Term;
import Model.PostingList;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class TermProcessing{
    private TreeMap<String, Term> TermList;
    private TreeMap<String, Double> Norm;
    
    public TermProcessing(){
        TermList = new TreeMap<>();
        Norm = new TreeMap<>();
    }
    
    private void AddNewTerm(String word, int numDoc, double frequency, double idf){
        Term term = new Term(word, numDoc, frequency, idf);
        TermList.put(word, term);
    }
    
    private void AddPosting(String word, String document, double frequency, double weight){
        Term term = TermList.get(word);
        if (term == null){
            return;
        }
        term.AddPosting(document, frequency, weight);
    }
    
    public void AddWord(String word, String document, double frequency){
        if (!TermList.containsKey(word)){
            AddNewTerm(word, 1, frequency, 1);
            AddPosting(word, document, frequency, 0);
        }
        else {
            Term oldTerm = TermList.get(word);
            int _oldTermNumDoc = oldTerm.getNumDoc() + 1;
            double _oldTermFrequency = oldTerm.getFrequency() + frequency;
            double _oldTermIdf = Math.log((double)1400 / _oldTermNumDoc);
            oldTerm.setNumDoc(_oldTermNumDoc);
            oldTerm.setFrequency(_oldTermFrequency);
            oldTerm.setIdf(_oldTermIdf);
            
            AddPosting(word, document, frequency, 0);
        }
    }
    
    private void setIfIdf(){
        Set<String> KeyOfTermList = TermList.keySet();
        
        KeyOfTermList.forEach((KeyOfTermItem) -> {
            Term term = TermList.get(KeyOfTermItem);
            ArrayList<PostingList> postingList = term.getPosting();
            
            postingList.forEach((postingItem) -> {
                double weight = term.getIdf() * postingItem.getFrequency();
                double value;
                
                String document = postingItem.getPositionOfDocument();
                if (Norm.containsKey(document)){
                    value = Norm.get(document) + weight * weight;
                } else {
                    value = weight * weight;
                }
                
                Norm.put(document, value);
                postingItem.setWeight(weight);
            });
        });
    }
    
    public void setWeight(){
        setIfIdf();
        Set<String> KeyOfTermList = TermList.keySet();
        
        KeyOfTermList.forEach((KeyOfTermItem) -> {
            Term term = TermList.get(KeyOfTermItem);
            ArrayList<PostingList> postingList = term.getPosting();
            
            postingList.forEach((postingItem) -> {
                String key = postingItem.getPositionOfDocument();
                double Norm = Math.sqrt(this.Norm.get(key));
                
                double weight = postingItem.getWeight() / Norm;
                postingItem.setWeight(weight);
            });
        });
    }
    
    public TreeMap<String, Term> getTermList(){
        return TermList;
    }
    
    public TreeMap<String, Double> getNorm(){
        return Norm;
    }
    
}
