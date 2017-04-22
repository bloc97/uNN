/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

import customnn.ActivationFunctions.ActivationType;
import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class FFLayer {
    
    protected LinkedList<FFNeuron> neurons;
    protected FFNeuron biasNeuron;
    protected FFLayer() {
        
    }
    public FFLayer(int size) {
        neurons = new LinkedList<>();
        for (int i=0; i<size; i++) {
            neurons.add(new FFNeuron());
        }
    }
    public void forward(ActivationType type) {
        for (FFNeuron neuron : neurons) {
            neuron.forward(type);
        }
    }
    public void backward(ActivationType type) {
        for (FFNeuron neuron : neurons) {
            neuron.backward(type);
        }
    }
    public void updateWeights(double learningRate, double decay) {
        for (FFNeuron neuron : neurons) {
            neuron.updateWeights(learningRate, decay);
        }
    }
    public void addBias() {
        biasNeuron = new FFNeuron();
        for (FFNeuron neuron : neurons) {
            biasNeuron.connect(neuron);
        }
        for (FFConnection connection : biasNeuron.getConnections()) {
            connection.setValue(1);
        }
        setBiasNeuronWeights(0);
    }
    public void connect(FFLayer layer) {
        for (FFNeuron thisNeuron : neurons) {
            for (FFNeuron otherNeuron : layer.neurons) {
                thisNeuron.connect(otherNeuron);
            }
        }
    }
    public void randomiseWeightsUniform(double min, double max) {
        for (FFNeuron neuron : neurons) {
            neuron.randomiseWeightsUniform(min, max);
        }
    }
    public void setBiasNeuronWeights(double d) {
        for (FFConnection connection : biasNeuron.getConnections()) {
            connection.setWeight(d);
        }
    }
    public void randomiseBiasUniform(double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        double range = Math.abs(max - min);
        
        for (FFConnection connection : biasNeuron.getConnections()) {
            connection.setWeight(Math.random() * range + min);
        }
    }
    public void clipWeights(double min, double max) {
        for (FFNeuron neuron : neurons) {
            neuron.clipWeights(min, max);
        }
        biasNeuron.clipWeights(min, max);
    }
}
