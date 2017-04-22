/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn;

import DotsGame.DotsMain;
import customnn.ActivationFunctions.ActivationType;
import customnn.FFNetwork.FFConnection;
import customnn.FFNetwork.FFInputLayer;
import customnn.FFNetwork.FFLayer;
import customnn.FFNetwork.FFNetwork;
import customnn.FFNetwork.FFNeuron;
import customnn.FFNetwork.FFOutputLayer;
import customnn.Graph.GraphNode;
import customnn.Graph.ListedNode;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DotsMain.Main();
        
    }
    public static void testNNxor() {
        
        double learningRate = 0.01d;
        ActivationType type = ActivationType.ELU;
        
        
        FFNetwork network = new FFNetwork(new int[]{2,10,10,10,1});
        network.randomiseWeightsUniform(-1d, 1d);
        
        network.addBias();
        network.randomiseBiasUniform(-0.1d, 0.1d);
        //network.setBiasNeuronWeights(0.1);
        double[] input = new double[] {1d,1d};
        double[] output = network.forward(input, type);
        double[] expected;
        double error;
        
        
        System.out.println(Arrays.toString(output));
        
        LinkedList<Double> pastError = new LinkedList<>();
        
        for (int i=0; i<100000; i++) {
            double[][] rand = getRandomSample();
            input = rand[0];
            expected = rand[1];
            output = network.forward(input, type);
            error = network.backward(expected, type);
            network.updateWeights(learningRate, 0.97d);
            
            pastError.addLast(error);
            
            if (pastError.size() > 10) {
                pastError.removeFirst();
            }
            
            double avgError = 0;
            for (double thisErr : pastError) {
                avgError += thisErr;
            }
            avgError /= pastError.size();
            
            
            System.out.println(avgError);
            if (avgError == 0 && pastError.size() > 2) {
                break;
            }
        }
        
        System.out.println(Arrays.toString(network.forward(new double[] {1,1}, type)));
        System.out.println(Arrays.toString(network.forward(new double[] {1,0}, type)));
        System.out.println(Arrays.toString(network.forward(new double[] {0,1}, type)));
        System.out.println(Arrays.toString(network.forward(new double[] {0,0}, type)));
    }
    public static double[][] getRandomSample() {
        int choice = (int)(Math.random() * 4);
        switch (choice) {
            case 0:
                return new double[][]{{1,1},{0}};
            case 1:
                return new double[][]{{1,0},{1}};
            case 2:
                return new double[][]{{0,1},{1}};
            case 3:
                return new double[][]{{0,0},{0}};
            default:
                throw new IllegalStateException("Error in random integer");
        }
        
    }
    
}
