/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

import customnn.ActivationFunctions.ActivationType;

/**
 *
 * @author bowen
 */
public class FFNetwork {
    protected FFInputLayer inputLayer;
    protected FFLayer[] hiddenLayers;
    protected FFOutputLayer outputLayer;
    
    public FFNetwork(int[] sizes) {
        hiddenLayers = new FFLayer[sizes.length-2];
        inputLayer = new FFInputLayer(sizes[0]);
        for (int i=1; i<sizes.length-1; i++) {
            hiddenLayers[i-1] = new FFLayer(sizes[i]);
        }
        outputLayer = new FFOutputLayer(sizes[sizes.length-1]);
        
        inputLayer.connect(hiddenLayers[0]);
        for (int i=0; i<hiddenLayers.length-1; i++) {
            hiddenLayers[i].connect(hiddenLayers[i+1]);
        }
        hiddenLayers[hiddenLayers.length-1].connect(outputLayer);
    }
    
    
    public void addBias() {
        for (FFLayer layer : hiddenLayers) {
            layer.addBias();
        }
        outputLayer.addBias();
    }
    public void setBiasNeuronWeights(double d) {
        for (FFLayer layer : hiddenLayers) {
            layer.setBiasNeuronWeights(d);
        }
        outputLayer.setBiasNeuronWeights(d);
    }
    public void randomiseBiasUniform(double min, double max) {
        for (FFLayer layer : hiddenLayers) {
            layer.randomiseBiasUniform(min, max);
        }
        outputLayer.randomiseBiasUniform(min, max);
    }
    public void randomiseWeightsUniform(double d) {
        inputLayer.randomiseWeightsUniform(-d, d);
        for (FFLayer layer : hiddenLayers) {
            layer.randomiseWeightsUniform(-d, d);
        }
    }
    public void randomiseWeightsUniform(double min, double max) {
        inputLayer.randomiseWeightsUniform(min, max);
        for (FFLayer layer : hiddenLayers) {
            layer.randomiseWeightsUniform(min, max);
        }
    }
    
    public double[] forward(double[] inputs, ActivationType type) {
        inputLayer.setInput(inputs);
        inputLayer.forward(type);
        for (FFLayer layer : hiddenLayers) {
            layer.forward(type);
        }
        outputLayer.forward(type);
        return outputLayer.getOutput();
    }
    public double backward(double[] expected, ActivationType type) {
        double totalError = outputLayer.setExpected(expected, type);
        outputLayer.backward(type);
        for (int i=hiddenLayers.length-1; i>=0; i--) {
            hiddenLayers[i].backward(type);
        }
        return totalError;
    }
    public void updateWeights(double learningRate, double decay, double gradientsClip) {
        outputLayer.updateWeights(learningRate, decay, gradientsClip);
        for (int i=hiddenLayers.length-1; i>=0; i--) {
            hiddenLayers[i].updateWeights(learningRate, decay, gradientsClip);
        }
    }
    public void clipWeights(double min, double max) {
        inputLayer.clipWeights(min, max);
        for (FFLayer layer : hiddenLayers) {
            layer.clipWeights(min, max);
        }
    }
}
