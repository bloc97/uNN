/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

import customnn.ActivationFunctions;
import customnn.ActivationFunctions.ActivationType;
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
    
    public double setExpected(double[] expected, ActivationType type) { //Using MSE
        double totalError = 0;
        if (expected.length == outputNodes.length) {
            for (int i=0; i<expected.length; i++) {
                double diff = expected[i] - outputNodes[i].getValue();
                totalError += diff * diff;
                outputNodes[i].setError(-(expected[i] - outputNodes[i].getValue()) * ActivationFunctions.nonLinearDerivative(expected[i], type));
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Input size mismatch.");
        }
        return totalError * 0.5d;
    }
    
    @Override
    public void randomiseWeightsUniform(double min, double max) {
        throw new IllegalStateException("Do not change the output layer weights!");
    }
    @Override
    public void clipWeights(double min, double max) {
        throw new IllegalStateException("Do not change the output layer weights!");
    }
}
