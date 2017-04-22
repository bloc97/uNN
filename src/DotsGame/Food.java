/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DotsGame;

/**
 *
 * @author bowen
 */
public interface Food extends DisplayObject {
    public static Food getRandomFood(int maxX, int maxY) {
        int choice = (int)(Math.random()*2);
        switch(choice) {
            case 0:
                return new BadFood(Math.random()*maxX, Math.random()*maxY);
            case 1:
                return new GoodFood(Math.random()*maxX, Math.random()*maxY);
            default:
                throw new IllegalStateException("Bad random number");
        }
    }
}
