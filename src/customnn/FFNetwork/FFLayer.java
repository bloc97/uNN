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
    public void foward() {
        for (FFNeuron neuron : neurons) {
            neuron.foward();
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
    public void randomiseWeightsUniform(double d) {
        for (FFNeuron neuron : neurons) {
            neuron.randomiseWeightsUniform(d);
        }
    }
    public void setBiasNeuronWeights(double d) {
        for (FFConnection connection : biasNeuron.getConnections()) {
            connection.setWeight(d);
        }
    }
}
