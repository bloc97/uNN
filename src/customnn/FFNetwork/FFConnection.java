/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

import customnn.Graph.UndirectedLink;

/**
 *
 * @author bowen
 */
public class FFConnection extends UndirectedLink {
    
    
    protected double value;
    protected double weight;
    protected double error;
    
    public FFConnection(FFNeuron thisNeuron, FFNeuron otherNeuron) {
        super(thisNeuron, otherNeuron);
    }

    public boolean isOutput(FFNeuron thisNeuron) { //Is this a connection to set or get values from
        return n1 == thisNeuron;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double d) {
        value = d;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double d) {
        weight = d;
    }
    public double getError() {
        return error;
    }
    public void setError(double d) {
        error = d;
    }
}
