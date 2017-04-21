/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

import customnn.Graph.GraphLink;
import customnn.Graph.ListedNode;
import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class FFNeuron extends ListedNode {
    
    public double totalValue;
    
    public FFNeuron() {
        super();
    }
    public void randomiseWeightsUniform(double range) {
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (connection.isOutput(this)) {
                connection.setWeight(Math.random() * range);
            }
        }
    }
    public FFConnection[] getConnections() {
        return links.toArray(new FFConnection[0]);
    }
    public void connect(FFNeuron neuron) {
        GraphLink link = new FFConnection(this, neuron);
        this.addLink(link);
        neuron.addLink(link);
    }
    public static double nonLinear(double d) {
        return d;
    }
    public static double nonLinearDerivative(double d) {
        return 1;
    }
    public void foward() {
        
        totalValue = 0;
        
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (!connection.isOutput(this)) {
                totalValue += connection.getValue() * connection.getWeight();
            }
            
        }
        
        totalValue = nonLinear(totalValue);
        
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (connection.isOutput(this)) {
                connection.setValue(totalValue);
            }
            
        }
    }
    
    public void backward() {
        double totalError = 0;
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (connection.isOutput(this)) {
                totalError += connection.getError() * connection.getWeight();
            }
            
        }
        totalError = totalError * nonLinearDerivative(totalValue);
        
        
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (!connection.isOutput(this)) {
                connection.setError(totalError);
            }
            
        }
        
    }
}
