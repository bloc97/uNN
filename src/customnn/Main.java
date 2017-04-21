/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn;

import customnn.FFNetwork.FFConnection;
import customnn.FFNetwork.FFInputLayer;
import customnn.FFNetwork.FFLayer;
import customnn.FFNetwork.FFNetwork;
import customnn.FFNetwork.FFNeuron;
import customnn.FFNetwork.FFOutputLayer;
import customnn.Graph.GraphNode;
import customnn.Graph.ListedNode;
import java.util.Arrays;

/**
 *
 * @author bowen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        FFInputLayer inputLayer = new FFInputLayer(2);
        FFLayer l1 = new FFLayer(8);
        FFOutputLayer outputLayer = new FFOutputLayer(1);
        
        inputLayer.connect(l1);
        l1.connect(outputLayer);
        
        inputLayer.randomiseWeightsUniform(0.1d);
        l1.randomiseWeightsUniform(0.1d);
        
        inputLayer.setInput(new double[] {2d,7d});
        
        
        inputLayer.foward();
        l1.foward();
        outputLayer.foward();
        System.out.println(Arrays.toString(outputLayer.getOutput()));
        */
        
        FFNetwork network = new FFNetwork(new int[]{2,8,3,1});
        network.randomiseWeightsUniform(0.1d);
        
        network.addBias();
        network.setBiasNeuronWeights(0);
        
        network.setInput(new double[] {2,8});
        network.foward();
        
        System.out.println(Arrays.toString(network.getOutput()));
        
    }
    
}
