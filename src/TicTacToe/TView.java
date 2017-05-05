/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToe;

import DotsGame.Agent;
import DotsGame.GameView;
import DotsGame.GoodFood;
import World2D.SceneSwing;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author bowen
 */
public class TView extends SceneSwing {
    private int maxX, maxY, start, end, width, height, widtho3, heighto3;
    
    private int xWins, oWins, draws;
    
    
    private int[] board = new int[9];
    private final int[] boardXPos;
    private final int[] boardYPos;
    
    protected boolean canClick = true;
    private boolean enableRandom = false;
    
    private double nelipson = 0.7d;
    private double ngamma = 0.9d;
    
    TNNAgent agent = new TNNAgent();
    
    public TView(int desiredFPS, int xsize, int ysize) {
        super(desiredFPS, xsize, ysize);
        maxX = xsize;
        maxY = ysize;
        start = 100;
        end = 100;
        width = maxX - (start*2);
        height = maxY - (end*2);
        widtho3 = Math.floorDiv(width, 3);
        heighto3 = Math.floorDiv(height, 3);
        boardXPos = new int[] {start+widtho3/2, start+widtho3/2+widtho3, start+widtho3/2+widtho3*2, start+widtho3/2, start+widtho3/2+widtho3, start+widtho3/2+widtho3*2, start+widtho3/2, start+widtho3/2+widtho3, start+widtho3/2+widtho3*2};
        boardYPos = new int[] {start+heighto3/2, start+heighto3/2, start+heighto3/2, start+heighto3/2+heighto3, start+heighto3/2+heighto3, start+heighto3/2+heighto3, start+heighto3/2+heighto3*2, start+heighto3/2+heighto3*2, start+heighto3/2+heighto3*2};
        setBackground(Color.getHSBColor(0, 0, 1));
        //this.setDisplayObjects(worlds);
        //this.dObjects.add(agent);
        initialiseHandlers();
        for (int i=0; i<board.length; i++) {
            board[i] = 0;
        }
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (enableRandom && canClick) {
                    if (checkCanPlay()) {
                        randomStep(1, 0.9d);
                    }
                    if (checkCanPlay()) {
                        neuralNetworkStep(2, nelipson, ngamma);
                    }
                }
            }
        });
        timer.start();
    }
    public final void initialiseHandlers() {
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                repaint();
                //System.out.println(e.getKeyChar());
                 switch(e.getKeyCode()) {
                    case KeyEvent.VK_W :
                        
                        break;
                    case KeyEvent.VK_S :
                        
                        break;
                    case KeyEvent.VK_A :
                        nelipson -= 0.1;
                        break;
                    case KeyEvent.VK_D :
                        nelipson += 0.1;
                        break;
                    case KeyEvent.VK_SPACE:
                        enableRandom = !enableRandom;
                        break;
                    case KeyEvent.VK_F11:
                        ((SceneSwing)e.getComponent()).getViewport().toggleFullScreen();
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
                        
                        break;
                    case KeyEvent.VK_S :
                        
                        break;
                    case KeyEvent.VK_A :
                        
                        break;
                    case KeyEvent.VK_D :
                        
                        break;
                    default :
                        break;
                 }
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                TView thisGame = (TView)(me.getComponent());
                if (canClick) {
                    if (checkCanPlay()) {
                        //randomStep(1, 0.9d);
                        clickStep(me.getX(), me.getY(), 1, ngamma);
                    }
                    if (checkCanPlay()) {
                        neuralNetworkStep(2, nelipson, ngamma);
                    }
                }
                //thisGame.foods.add(new GoodFood(me.getX(), me.getY()));
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
        
    }
    
    private boolean checkCanPlay() {
        if (!canClick) {
            return false;
        }
        if (checkWin()) {
            return false;
        }
        
        boolean emptySlot = false;
        for (int i=0; i<9; i++) {
            if (board[i] == 0) {
                emptySlot = true;
                break;
            }
        }
        if (!emptySlot) {
            draw();
            return false;
        }
        return true;
    }
    private boolean checkBoardHasEmptySlot(int[] board) {
        
        boolean emptySlot = false;
        for (int i=0; i<9; i++) {
            if (board[i] == 0) {
                emptySlot = true;
                break;
            }
        }
        if (!emptySlot) {
            return false;
        }
        return true;
    }
    
    private static double[] flattenBoards(int[] board) {
        double[] flatBoard = new double[18];
        
        for (int i=0; i<9; i++) {
            if (board[i] == 1) {
                flatBoard[i] = 1;
            }
        }
        for (int i=0; i<9; i++) {
            if (board[i] == 2) {
                flatBoard[i+9] = 1;
            }
        }
        return flatBoard;
    }
    
    private static int findMaxIndex(double[] actions, int[] board) {
        double max = -1000;
        int index = -1;
        for (int i=0; i<actions.length; i++) {
            if (actions[i] > max && board[i] == 0) {
                max = actions[i];
                index = i;
            }
        }
        return index;
    }
    private static double findMax(double[] arr) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i=0; i<arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    private void neuralNetworkStep(int player, double epsilon, double gamma) {
        int[] state = board.clone();
        double[] q = agent.predict(flattenBoards(state));
        int action;
        if (Math.random() < epsilon) {
            action = randomChoice(player);
        } else {
            action = findMaxIndex(q, state);
        }
        board[action] = player;
        double reward = getReward(board, player);
        
        double[] newQ = agent.predict(flattenBoards(board));
        double maxQ = findMax(newQ);
        
        double update;
        if (reward == -1) {
            update = (reward + (gamma * maxQ));
        } else {
            update = reward;
        }
        //System.out.println(Arrays.toString(q));
        agent.lastQ = q;
        q[action] = update;
        //System.out.println(Arrays.toString(q));
        agent.lastState = flattenBoards(state);
        agent.lastQs = newQ;
        agent.lastAction = action;
        if (reward >= 0) {
            System.out.println(Arrays.toString(q));
            agent.train(agent.lastState, q, 1);
        }
        
        
        
    }
    private double getReward(int[] board, int player) {
        int px = 1;
        int po = 2;
        int winner = 0;
        
        if (!checkBoardHasEmptySlot(board)) {
            return 0.01;
        }
        
        
        for (int i=0; i<3; i++) {
            if (checkRow(px, i)) {
                winner = px;
            } else if (checkRow(po, i)) {
                winner = po;
            }
            if (checkColumn(px, i)) {
                winner = px;
            } else if (checkColumn(po, i)) {
                winner = po;
            }
        }
        if (board[0] == px && board[4] == px && board[8] == px) {
                winner = px;
        } else if (board[0] == po && board[4] == po && board[8] == po) {
                winner = po;
        } else if (board[2] == px && board[4] == px && board[6] == px) {
                winner = px;
        } else if (board[2] == po && board[4] == po && board[6] == po) {
                winner = po;
        }
        
        if (winner == 0) {
            //System.out.println("-1 Reward");
            return 0d;
        } else if (winner == player) {
            //System.out.println("10 Reward");
            return 1d;
        } else {
            //System.out.println("-10 Reward");
            return -1d;
        }
    }
    
    private void clearBoard() {
        canClick = false;
        repaint();
        int time = 1000;
        if (enableRandom) {
            time = 100;
        }
        Timer timer = new Timer(time, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                canClick = true;
                for (int i=0; i<9; i++) {
                    board[i] = 0;
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void xWin() {
        System.out.println("X Wins!");
        xWins++;
        clearBoard();
    }
    private void oWin() {
        System.out.println("O Wins!");
        oWins++;
        clearBoard();
    }
    private void draw() {
        System.out.println("Draw!");
        draws++;
        clearBoard();
    }
    
    private boolean checkRow(int p, int row) {
        row = row*3;
        if (board[row] == p && board[row+1] == p && board[row+2] == p) {
            return true;
        }
        return false;
        
    }
    private boolean checkColumn(int p, int col) {
        if (board[col] == p && board[col+3] == p && board[col+6] == p) {
            return true;
        }
        return false;
    }
    
    private boolean checkWin() {
        int px = 1;
        int po = 2;
        
        for (int i=0; i<3; i++) {
            if (checkRow(px, i)) {
                xWin();
                return true;
            } else if (checkRow(po, i)) {
                oWin();
                return true;
            }
            if (checkColumn(px, i)) {
                xWin();
                return true;
            } else if (checkColumn(po, i)) {
                oWin();
                return true;
            }
        }
        if (board[0] == px && board[4] == px && board[8] == px) {
            xWin();
            return true;
        } else if (board[0] == po && board[4] == po && board[8] == po) {
            oWin();
            return true;
        } else if (board[2] == px && board[4] == px && board[6] == px) {
            xWin();
            return true;
        } else if (board[2] == po && board[4] == po && board[6] == po) {
            oWin();
            return true;
        }
        
        return false;
    }
    
    private int randomChoice(int player) {
        
        while(true) {
            int choice = (int)(Math.random()*9);
            
            if (board[choice] == 0) {
                board[choice] = player;
                return choice;
            }
        }
    }
    private void clickStep(int x, int y, int player, double gamma) {
        
        click(x, y);
        if (agent.lastState != null) {
            double reward = getReward(board, ((player == 1) ? 2 : 1));
            double[] newQ = agent.lastQs;
            double maxQ = findMax(newQ);
            double[] q = agent.lastQ;
            int action = agent.lastAction;
            double[] state = agent.lastState;

            double update;
            if (reward == -1) {
                update = (reward + (gamma * maxQ));
            } else {
                update = reward;
            }
            q[action] = update;
            //System.out.println(Arrays.toString(q));
            agent.train(state, q, 1);
        }
        //repaint();
        //checkWin();
        return;
        
        
    }
    private void randomStep(int player, double gamma) {
        
        int choice;
        while(true) {
            choice = (int)(Math.random()*9);
            
            if (board[choice] == 0) {
                board[choice] = player;
                break;
            }
            
        }
        if (agent.lastState != null) {
            double reward = getReward(board, ((player == 1) ? 2 : 1));
            double[] newQ = agent.lastQs;
            double maxQ = findMax(newQ);
            double[] q = agent.lastQ;
            int action = agent.lastAction;
            double[] state = agent.lastState;

            double update;
            if (reward == -1) {
                update = (reward + (gamma * maxQ));
            } else {
                update = reward;
            }
            q[action] = update;
            //System.out.println(Arrays.toString(q));
            if (reward < 0.7) {
                agent.train(state, q, 1);
            }
        }
        //repaint();
        //checkWin();
        return;
        
        
    }
    
    protected boolean isWithin(int v, int min, int max) {
        if (v >= min && v < max) {
            return true;
        }
        return false;
    }
    
    protected void click(int x, int y) {
        if (isWithin(y, start, start+heighto3)) { //First column
            if (isWithin(x, start, start+widtho3)) {
                
                if (board[0] == 0) {
                    board[0] = 1;
                }
                
            } else if (isWithin(x, start+widtho3, start+widtho3*2)) {
                if (board[1] == 0) {
                    board[1] = 1;
                }
            } else if (isWithin(x, start+widtho3*2, start+widtho3*3)) {
                if (board[2] == 0) {
                    board[2] = 1;
                }
            }
        } else if (isWithin(y, start+heighto3, start+heighto3*2)) {
            if (isWithin(x, start, start+widtho3)) {
                if (board[3] == 0) {
                    board[3] = 1;
                }
            } else if (isWithin(x, start+widtho3, start+widtho3*2)) {
                if (board[4] == 0) {
                    board[4] = 1;
                }
            } else if (isWithin(x, start+widtho3*2, start+widtho3*3)) {
                if (board[5] == 0) {
                    board[5] = 1;
                }
            }
        } else if (isWithin(y, start+heighto3*2, start+heighto3*3)) {
            if (isWithin(x, start, start+widtho3)) {
                if (board[6] == 0) {
                    board[6] = 1;
                }
            } else if (isWithin(x, start+widtho3, start+widtho3*2)) {
                if (board[7] == 0) {
                    board[7] = 1;
                }
            } else if (isWithin(x, start+widtho3*2, start+widtho3*3)) {
                if (board[8] == 0) {
                    board[8] = 1;
                }
            }
        }  
        //repaint();
    }
    
    private void drawX(Graphics2D g2, int x, int y) {
        g2.drawLine(x-50, y-50, x+50, y+50);
        g2.drawLine(x+50, y-50, x-50, y+50);
    }
    
    private void drawO(Graphics2D g2, int x, int y) {
        g2.drawOval(x-50, y-50, 100, 100);
    }
    
    @Override
    protected void beforePaint() {
        
    }

    @Override
    protected void prePaint() {
        
    }

    @Override
    protected void onPaint(Graphics2D g2) {
        
        g2.drawString("X Wins: " + xWins, 20, 20);
        g2.drawString("O Wins: " + oWins, 20, 40);
        g2.drawString("Draws: " + draws, 20, 60);
        g2.drawString("Epsilon: " + nelipson, 20, 80);
        
        g2.drawRect(start, end, width, height);
        g2.drawLine(start+widtho3, start, start+widtho3, maxY-start);
        g2.drawLine(start+widtho3*2, start, start+widtho3*2, maxY-start);
        g2.drawLine(start, start+heighto3, maxX-start, start+heighto3);
        g2.drawLine(start, start+heighto3*2, maxX-start, start+heighto3*2);
        
        for (int i=0; i<9; i++) {
            if (board[i] == 1) {
                drawX(g2, boardXPos[i], boardYPos[i]);
            } else if (board[i] == 2) {
                drawO(g2, boardXPos[i], boardYPos[i]);
            }
            
        }
        
        
    }

    @Override
    protected void postPaint() {
        
    }

    @Override
    protected void afterPaint() {
        
    }
    
}
