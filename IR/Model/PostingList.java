/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ThangHuynh
 */
public class PostingList {
    private String PositionOfDocument;
    private double Frequency;
    private double Weight;
    
    public PostingList(String positionOfDocument, double frequency, double weight){
        this.PositionOfDocument = positionOfDocument;
        this.Frequency = frequency;
        this.Weight = weight;
    }

    public String getPositionOfDocument() {
        return PositionOfDocument;
    }

    public void setPositionOfDocument(String d) {
        this.PositionOfDocument = d;
    }

    public double getFrequency() {
        return Frequency;
    }

    public void setFrequency(double f) {
        this.Frequency = f;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double w) {
        this.Weight = w;
    }
}

