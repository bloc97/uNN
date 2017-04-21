/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class FFInputLayer extends FFLayer {
    
    public FFConnection[] inputNodes;
    
    public FFInputLayer(int size) {
        super();
        neurons = new LinkedList<>();
        inputNodes = new FFConnection[size];
        for (int i=0; i<size; i++) {
            FFNeuron neuron = new FFNeuron();
            neurons.add(neuron);
            inputNodes[i] = new FFConnection(null, neuron);
            inputNodes[i].setWeight(1);
            neuron.addLink(inputNodes[i]);
        }
    }
    
    public void setInput(double[] inputs) {
        if (inputs.length == inputNodes.length) {
            for (int i=0; i<inputs.length; i++) {
                inputNodes[i].setValue(inputs[i]);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Input size mismatch.");
        }
    }
}
