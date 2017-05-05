/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DotsGame;

import World2D.Camera;
import customnn.ActivationFunctions;
import customnn.FFNetwork.FFNetwork;
import static customnn.Main.getRandomSample;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class Agent implements DisplayObject {

    private double x;
    private double y;
    private final double r;
    public final double rv;
    public final double rsqr;
    private Color color;
    
    double learningRate = 0.01d;
    ActivationFunctions.ActivationType type = ActivationFunctions.ActivationType.ARCTAN;
    
    private FFNetwork network = new FFNetwork(new int[] {8, 128, 64, 64, 32, 2});
    
    
    public Agent(double r) {
        this.r = r;
        rv = 6*r;
        rsqr = Math.sqrt((rv*rv)/2);
        color = Color.gray;
        x = 100;
        y = 100;
        
        network.randomiseWeightsUniform(-1d, 1d);
        network.addBias();
        network.randomiseBiasUniform(-0.1d, 0.1d);
    }
    
    public void trainBad(double[] eyes, double lastXSign, double lastYSign) {
        //System.out.println("Training Bad");
        train(eyes, -lastXSign, -lastYSign, 5);
        //System.out.println(Arrays.toString(network.forward(eyes, type)));
    }
    public void trainGood(double[] eyes, double lastXSign, double lastYSign) {
        //System.out.println("Training");
        train(eyes, lastXSign, lastYSign, 50);
    }
    public void train(double[] eyes, double lastXSign, double lastYSign, int n) {
        double[] input = eyes;
        double[] expected = new double[] {lastXSign, lastYSign};
        
        for (int i=0; i<n; i++) {
            System.out.println(Arrays.toString(network.forward(input, type)));
            network.backward(expected, type);
            network.updateWeights(learningRate, 0.97d, 10d);
        }
        
    }
    public double[] getMovement(double[] eyes) {
        return network.forward(eyes, type);
    }
    
    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(x-r), (int)(y-r), (int)(r*2), (int)(r*2));
        
        g2.setColor(Color.BLUE);
        
        g2.drawLine((int)x, (int)y, (int)(x+rv), (int)(y));
        g2.drawLine((int)x, (int)y, (int)(x-rv), (int)(y));
        g2.drawLine((int)x, (int)y, (int)(x), (int)(y+rv));
        g2.drawLine((int)x, (int)y, (int)(x), (int)(y-rv));
        
        g2.drawLine((int)x, (int)y, (int)(x+rsqr), (int)(y+rsqr));
        g2.drawLine((int)x, (int)y, (int)(x+rsqr), (int)(y-rsqr));
        g2.drawLine((int)x, (int)y, (int)(x-rsqr), (int)(y+rsqr));
        g2.drawLine((int)x, (int)y, (int)(x-rsqr), (int)(y-rsqr));
    }
    
    @Override
    public double x() {
        return x;
    }
    @Override
    public double y() {
        return y;
    }
    @Override
    public double r() {
        return r;
    }
    
    public void setX(double d) {
        x = d;
    }
    public void setY(double d) {
        y = d;
    }
    public void addX(double d) {
        x = x+d;
    }
    public void addY(double d) {
        y = y+d;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    

    
}
