/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DotsGame;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author bowen
 */
public class GoodFood implements Food {

    private double x;
    private double y;
    private final double r;
    private final Color color;
    
    public GoodFood(double x, double y) {
        this.x = x;
        this.y = y;
        this.r = 10;
        color = Color.green;
        x = 100;
        y = 100;
    }
    
    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(x-r), (int)(y-r), (int)(r*2), (int)(r*2));
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
    
    

    
}
