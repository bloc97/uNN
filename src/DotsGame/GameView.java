/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DotsGame;

import Physics2D.Vector2;
import Physics2D.Vectors2;
import SpaceGame.Objects.SpaceNatural;
import World2D.Objects.Interpolable;
import World2D.SceneSwing;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class GameView extends SceneSwing {
    
    private boolean keyW = false;
    private boolean keyS = false;
    private boolean keyA = false;
    private boolean keyD = false;
    
    private boolean isPaused = false;
    
    private Agent agent;
    
    private LinkedList<Food> foods = new LinkedList<>();
    
    private long msCounter = 1;
    private int fpsCounter = 0;
    
    private int objPaintCounter = 0;
    
    private double lastX = 0;
    private double lastY = 0;
    
    private int maxX;
    private int maxY;
    
    private int moveCounter = 0;
    private int moveTop = 30;
    
    private int spawnCounter = 0;
    private int spawnTop = 60;
    
    private double eProb = 0.5d;
    
    public GameView() {
        this(60, 0, 0);
    }
    
    public GameView(int desiredFPS, int xsize, int ysize) {
        super(desiredFPS, xsize, ysize);
        maxX = xsize;
        maxY = ysize;
        setBackground(Color.getHSBColor(0, 0, 1));
        //this.setDisplayObjects(worlds);
        agent = new Agent(20);
        agent.setX(500);
        agent.setY(500);
        randomGenDots(100);
        //this.dObjects.add(agent);
        initialiseHandlers();
    }
    
    public void randomGenDots(int n) {
        while (foods.size() < 400 && n > 0) {
            foods.add(Food.getRandomFood(maxX, maxY));
            n--;
        }
    }
    
    protected void toggleLearning() {
        if (moveTop == 30) {
            moveTop = 1;
            eProb = 0.1d;
        } else {
            moveTop = 30;
            eProb = 0.5d;
        }
    }
    
    public final void initialiseHandlers() {
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.getKeyChar());
                 switch(e.getKeyCode()) {
                    case KeyEvent.VK_W :
                        keyW = true;
                        break;
                    case KeyEvent.VK_S :
                        keyS = true;
                        break;
                    case KeyEvent.VK_A :
                        keyA = true;
                        break;
                    case KeyEvent.VK_D :
                        keyD = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        toggleLearning();
                        break;
                    case KeyEvent.VK_F11:
                        ((GameView)e.getComponent()).viewport.toggleFullScreen();
                        System.out.println("FullScreen");
                        break;
                    default :
                        break;
                 }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                 switch(e.getKeyCode()) {
                    case KeyEvent.VK_W :
                        keyW = false;
                        break;
                    case KeyEvent.VK_S :
                        keyS = false;
                        break;
                    case KeyEvent.VK_A :
                        keyA = false;
                        break;
                    case KeyEvent.VK_D :
                        keyD = false;
                        break;
                    default :
                        break;
                 }
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                GameView thisGame = (GameView)(me.getComponent());
                thisGame.foods.add(new GoodFood(me.getX(), me.getY()));
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent me) {
                
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                
            }
        });
        
    }
    
    public void togglePause() {
        isPaused = !isPaused;
    }
    
    
    public void checkKeys() {
        if (keyW) {
            agent.addY(-10);
        }
        if (keyS) {
            agent.addY(10);
        }
        if (keyA) {
            agent.addX(-10);
        }
        if (keyD) {
            agent.addX(10);
        }
    }
    public double sign(double d) {
        if (d > 0) {
            return 1;
        } else if (d == 0) {
            return 0;
        } else {
            return -1;
        }
    }
    public double[] randomDir() {
        int choice = (int)(Math.random() * 8);
        switch (choice) {
            case 0:
                return new double[]{0,1};
            case 1:
                return new double[]{1,0};
            case 2:
                return new double[]{1,1};
            case 3:
                return new double[]{0,-1};
            case 4:
                return new double[]{-1,0};
            case 5:
                return new double[]{-1,-1};
            case 6:
                return new double[]{-1,1};
            case 7:
                return new double[]{1,-1};
            default:
                return new double[]{0,0};
        }
    }
    
    protected void moveAgent(double[] eyes, double eProbability) {
        
        if (moveCounter > moveTop) {
            moveCounter = 0;
            
            if (Math.random() < eProbability) {
                double[] rD = randomDir();

                lastX = rD[0]*10;
                lastY = rD[1]*10;
            } else {
                double[] nD = agent.getMovement(eyes);
                lastX = sign(nD[0])*10;
                lastY = sign(nD[1])*10;
            }
        }
        moveCounter++;
        
        agent.addX(lastX);
        agent.addY(lastY);
    }
    protected void spawnFood() {
        if (spawnCounter > spawnTop) {
            spawnCounter = 0;
            randomGenDots(1);
        }
        spawnCounter++;
    }
    
    protected double[] checkNearFood() {
        Vector2[] foodVectors = new Vector2[foods.size()];
        boolean[] isFoodBad = new boolean[foodVectors.length];
        Vector2 agentVector = new Vector2(new double[] {agent.x(), agent.y()});
        
        for (int i=0; i<foods.size(); i++) {
            Food food = foods.get(i);
            Vector2 foodVector = new Vector2(new double[] {food.x(), food.y()});
            foodVectors[i] = foodVector.sub(agentVector);
            if (food instanceof BadFood) {
                isFoodBad[i] = true;
            } else {
                isFoodBad[i] = false;
            }
        }
        
        
        Vector2[] eyesVector = new Vector2[8];
        double x = agent.x();
        double y = agent.y();
        double r = 10;
        double rv = agent.rv;
        double rsqr = agent.rsqr;
        eyesVector[0] = new Vector2(new double[] {0, -rv}); //top eye    
        eyesVector[1] = new Vector2(new double[] {rsqr, -rsqr}); //topright eye      
        eyesVector[2] = new Vector2(new double[] {rv, 0}); //right eye      
        eyesVector[3] = new Vector2(new double[] {rsqr, rsqr}); //bottomright eye      
        eyesVector[4] = new Vector2(new double[] {0, rv}); //bottom eye      
        eyesVector[5] = new Vector2(new double[] {-rsqr, rsqr}); //bottomleft eye   
        eyesVector[6] = new Vector2(new double[] {-rv, 0}); //left eye   
        eyesVector[7] = new Vector2(new double[] {-rsqr, -rsqr}); //topleft eye    
        
        double[] eyes = new double[8];
        
        double mxmin = -x;
        double mymin = -y;
        double mxmax = -x + maxX;
        double mymax = -y + maxY;
        if (mymin > -rv) {
            eyes[0] = -1;
        }
        if (mymin > -rsqr) {
            eyes[1] = -1;
            eyes[7] = -1;
        }
        if (mymax < rv) {
            eyes[4] = -1;
        }
        if (mymax < rsqr) {
            eyes[3] = -1;
            eyes[5] = -1;
        }
        if (mxmin > -rv) {
            eyes[6] = -1;
        }
        if (mxmin > -rsqr) {
            eyes[5] = -1;
            eyes[7] = -1;
        }
        if (mxmax < rv) {
            eyes[2] = -1;
        }
        if (mxmax < rsqr) {
            eyes[1] = -1;
            eyes[3] = -1;
        }
        
        for (int n=0; n<eyesVector.length; n++) {
            double eyeNorm = eyesVector[n].norm();
            for (int i=0; i<foodVectors.length; i++) {
                Vector2 rej = Vectors2.rej(foodVectors[i], eyesVector[n]);
                Vector2 proj = Vectors2.proj(foodVectors[i], eyesVector[n]);
                double projNorm = proj.norm();
                Vector2 subProj = Vectors2.sub(eyesVector[n], proj);
                double subProjNorm = subProj.norm();
                if (rej.norm() <= r && projNorm <= eyeNorm && subProjNorm <= projNorm && subProjNorm >= 0) {
                    if (isFoodBad[i]) {
                        eyes[n] = -1;
                    } else {
                        eyes[n] = 1;
                    }
                }
            }
        }
        return eyes;
    }
    
    @Override
    protected void beforePaint() {
        checkKeys();
    }
    @Override
    protected void prePaint() {
        msCounter = System.currentTimeMillis();
    }
    @Override
    protected void onPaint(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawString("FPS: " + fpsCounter, 10, 20);
        
        spawnFood();
        double[] eyes = checkNearFood();
        System.out.println(Arrays.toString(eyes));
        moveAgent(eyes, eProb);
        
        
        if (agent.x() > maxX) {
            agent.setX(maxX);
            agent.trainBad(eyes, sign(lastX), sign(lastY));
        } else if (agent.x() < 0) {
            agent.setX(0);
            agent.trainBad(eyes, sign(lastX), sign(lastY));
        }
        if (agent.y() > maxY) {
            agent.setY(maxY);
            agent.trainBad(eyes, sign(lastX), sign(lastY));
        } else if (agent.y() < 0) {
            agent.setY(0);
            agent.trainBad(eyes, sign(lastX), sign(lastY));
        }
        
        synchronized(foods) {
            for (Food food : foods) {
                double xDist = (food.x() - agent.x());
                double yDist = (food.y() - agent.y());
                double distance = Math.sqrt((xDist * xDist) + (yDist * yDist));
                if (distance < (food.r() + agent.r())) {
                    foods.remove(food);
                    if (food instanceof GoodFood) {
                        agent.trainGood(eyes, sign(lastX), sign(lastY));
                    } else if (food instanceof BadFood) {
                        agent.trainBad(eyes, sign(lastX), sign(lastY));
                    }
                } else {
                    food.render(g2);
                }
            }
        }
        
        agent.render(g2);
        g2.drawRect(0, 0, maxX, maxY);
    }
    @Override
    protected void postPaint() {
        int msDiff = (int)(System.currentTimeMillis() - msCounter);
        if (msDiff == 0) {
            msDiff = 1;
        }
        int newFps = Math.floorDiv(1000, msDiff);
        fpsCounter = (newFps <= 60) ? newFps : 60;
        
    }
    @Override
    protected void afterPaint() {
        
    }
    


}
