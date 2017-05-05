/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn;

import DotsGame.DotsMain;
import TicTacToe.TTTMain;
import customnn.ActivationFunctions.ActivationType;
import customnn.FFNetwork.FFConnection;
import customnn.FFNetwork.FFInputLayer;
import customnn.FFNetwork.FFLayer;
import customnn.FFNetwork.FFNetwork;
import customnn.FFNetwork.FFNeuron;
import customnn.FFNetwork.FFOutputLayer;
import customnn.Graph.GraphNode;
import customnn.Graph.ListedNode;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.nnet.learning.PerceptronLearning;
import org.neuroph.util.NeuralNetworkType;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author bowen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //testRefNNxor();
        //testNNxor();
        //DotsMain.main();
        TTTMain.main();
        
    }
    public static void testRefNNxor() {
        List<Integer> layersi = Arrays.asList(2, 10, 10, 10, 1);
        
        NeuralNetwork network = new MultiLayerPerceptron(layersi, TransferFunctionType.TANH);
        System.out.println(Arrays.deepToString(network.getLayers().toArray()));
        List<Layer> layers = network.getLayers();
        
        for (Layer layer : layers) {
            System.out.println(layer.getNeuronsCount());
        }
        
        MomentumBackpropagation bp = new MomentumBackpropagation();
        bp.setLearningRate(0.001d);
        bp.setMinErrorChange(0.0001d);
        bp.setMomentum(0.1);
        //bp.setMaxError(10000d);
        bp.setMaxIterations(100000);
        bp.setNeuralNetwork(network);
        
        network.randomizeWeights(-0.5d, 0.5d);
        DataSet data = new DataSet(2, 1);
        data.add(new DataSetRow(new double[]{1,1}, new double[]{0}));
        data.add(new DataSetRow(new double[]{1,0}, new double[]{1}));
        data.add(new DataSetRow(new double[]{0,1}, new double[]{1}));
        data.add(new DataSetRow(new double[]{0,0}, new double[]{0}));
        
        //network.learn(data);
        bp.learn(data);
        
        network.setInput(new double[]{1,1});
        network.calculate();
        System.out.println(Arrays.toString(network.getOutput()));
        network.setInput(new double[]{1,0});
        network.calculate();
        System.out.println(Arrays.toString(network.getOutput()));
        network.setInput(new double[]{0,1});
        network.calculate();
        System.out.println(Arrays.toString(network.getOutput()));
        network.setInput(new double[]{0,0});
        network.calculate();
        System.out.println(Arrays.toString(network.getOutput()));
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
            network.updateWeights(learningRate, 0.97d, 10d);
            
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
