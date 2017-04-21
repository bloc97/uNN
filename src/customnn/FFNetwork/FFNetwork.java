/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.FFNetwork;

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
    public void randomiseWeightsUniform(double d) {
        inputLayer.randomiseWeightsUniform(d);
        for (FFLayer layer : hiddenLayers) {
            layer.randomiseWeightsUniform(d);
        }
    }
    
    public void foward() {
        inputLayer.foward();
        for (FFLayer layer : hiddenLayers) {
            layer.foward();
        }
        outputLayer.foward();
    }
    public void setInput(double[] inputs) {
        inputLayer.setInput(inputs);
    }
    public double[] getOutput() {
        return outputLayer.getOutput();
    }
}
