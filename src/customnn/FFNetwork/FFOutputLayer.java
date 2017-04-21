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
public class FFOutputLayer extends FFLayer {
    
    public FFConnection[] outputNodes;
    
    public FFOutputLayer(int size) {
        super();
        neurons = new LinkedList<>();
        outputNodes = new FFConnection[size];
        for (int i=0; i<size; i++) {
            FFNeuron neuron = new FFNeuron();
            neurons.add(neuron);
            outputNodes[i] = new FFConnection(neuron, null);
            outputNodes[i].setWeight(1);
            neuron.addLink(outputNodes[i]);
        }
    }
    
    public double[] getOutput() {
        double[] output = new double[outputNodes.length];
        for (int i=0; i<output.length; i++) {
            output[i] = outputNodes[i].getValue();
        }
        return output;
    }
    @Override
    public void randomiseWeightsUniform(double d) {
        throw new IllegalStateException("Do not change the output layer weights!");
    }
}
