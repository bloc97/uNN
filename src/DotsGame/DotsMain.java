/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DotsGame;

import World2D.Scene;
import World2D.Viewport;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author bowen
 */
public class DotsMain {
    public static void Main() {
        
        int defaultW = 1200;
        int defaultH = 1000;
        //System.setProperty("sun.java2d.opengl","True");
        Scene scene = new GameView(60, defaultW, defaultH);
        //Scene scene = newGameViewSlick();
        Viewport viewport = new Viewport(defaultW, defaultH);
        viewport.setScene(scene);
        
        scene.launch();
    }
}
