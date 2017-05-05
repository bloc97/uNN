/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToe;

import customnn.ActivationFunctions;
import customnn.FFNetwork.FFNetwork;
import java.util.Arrays;

/**
 *
 * @author bowen
 */
public class TNNAgent {
    
    double learningRate = 0.01d;
    ActivationFunctions.ActivationType type = ActivationFunctions.ActivationType.RELU;
    
    private FFNetwork network = new FFNetwork(new int[] {18, 128, 64, 64, 32, 9});
    
    public double[] lastState;
    public double[] lastQ;
    public double[] lastQs;
    public int lastAction;
    
    public TNNAgent() {
        network.randomiseWeightsUniform(-1d, 1d);
        network.addBias();
        network.randomiseBiasUniform(-0.1d, 0.1d);
    }
    public double[] predict(double[] input) {
        return network.forward(input, type);
    }
    public double train(double[] input, double[] expected, int n) {
        double totalError = 0;
        for (int i=0; i<n; i++) {
            System.out.println(Arrays.toString(input));
            double[] output = network.forward(input, type);
            System.out.println(Arrays.toString(output));
            System.out.println(Arrays.toString(expected));
            totalError += network.backward(expected, type);
            network.updateWeights(learningRate, 1d, 0.01d);
        }
        System.out.println(totalError/n);
        return totalError / ((n>0) ? n : 1);
    }
    
}
