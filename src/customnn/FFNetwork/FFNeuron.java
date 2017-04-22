/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

import customnn.ActivationFunctions;
import customnn.ActivationFunctions.ActivationType;
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
    public void randomiseWeightsUniform(double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        double range = Math.abs(max - min);
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (connection.isOutput(this)) {
                //System.out.println(Math.random() * range + min);
                connection.setWeight(Math.random() * range + min);
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
    
    public void forward(ActivationType type) {
        
        totalValue = 0;
        
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (!connection.isOutput(this)) {
                totalValue += connection.getValue() * connection.getWeight();
            }
            
        }
        
        totalValue = ActivationFunctions.nonLinear(totalValue, type);
        
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (connection.isOutput(this)) {
                connection.setValue(totalValue);
            }
            
        }
    }
    
    public void backward(ActivationType type) {
        double totalError = 0;
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (connection.isOutput(this)) {
                double weight = connection.getWeight();
                if (Double.isInfinite(weight)) {
                    throw new IllegalStateException("Infinite Weight!");
                }
                totalError += connection.getError() * connection.getWeight();
            }
            
        }
        
        totalError = totalError * ActivationFunctions.nonLinearDerivative(totalValue, type);
        
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (!connection.isOutput(this)) {
                connection.setError(totalError);
            }
            
        }
        
    }
    public void updateWeights(double learningRate, double decay) { //SGD
        
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (!connection.isOutput(this)) {
                double weight = connection.getWeight();
                if (weight > 1) {
                    connection.setWeight(decay * connection.getWeight() - (learningRate * connection.getError() * connection.getValue())); //Weight + lr * E * in
                } else {
                    connection.setWeight(connection.getWeight() - (learningRate * connection.getError() * connection.getValue())); //Weight + lr * E * in
                }
            }
            
        }
    }
    public void clipWeights(double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        for (GraphLink link : links) {
            FFConnection connection = (FFConnection)link;
            
            if (connection.isOutput(this)) {
                double weight = connection.getWeight();
                if (weight < min) {
                    weight = min;
                } else if (weight > max) {
                    weight = max;
                }
                
                connection.setWeight(weight);
            }
            
        }
    }
}
