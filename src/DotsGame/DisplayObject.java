/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DotsGame;

import java.awt.Graphics2D;

/**
 *
 * @author bowen
 */
public interface DisplayObject {
    public void render(Graphics2D g2);
    public double x();
    public double y();
    public double r();
}
