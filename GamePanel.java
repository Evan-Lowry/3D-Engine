import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
// import java.awt.RenderingHints.Key;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    static int windowHeight = 1080/2;
    static int windowWidth = 1920/2;

    int FPS = 60;

    public Camera c = new Camera(0,0,0,0,5);
    int camSpeed = 5;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void update(){

        if (keyH.upPressed == true) {
            c.moveForward(camSpeed);
            // System.out.println("UP");
        }
        if (keyH.downPressed == true) {
            c.moveForward(-camSpeed);
            // System.out.println("DOWN");
        }
        if (keyH.leftPressed == true) {
            c.moveSideways(camSpeed);
            // System.out.println("LEFT");
        }
        if (keyH.rightPressed == true) {
            c.moveSideways(-camSpeed);
            // System.out.println("RIGHT");
        }
        if (keyH.turnLeftPressed == true) {
            c.moveRot(camSpeed);
            // System.out.println("TURNLEFT");
        }
        if (keyH.turnRightPressed == true) {
            c.moveRot(-camSpeed);
            // System.out.println("TURNRIGHT");
        }

        // c.moveRot(1/2);
        // c.moveY(0);
        // c.moveX(1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Vertex v1 = new Vertex(300,50,0);
        Vertex v2 = new Vertex(300,-50,0);
        Wall w1 = new Wall(c,v1,v2);

        g2.setColor(Color.white);

        g2.drawOval(windowHeight/2 + v1.getX(), windowWidth/2 + v1.getY(), 3, 3);
        g2.drawOval(windowHeight/2 + v2.getX(), windowWidth/2 + v2.getY(), 3, 3);
        g2.drawOval(windowHeight/2 + c.getX(), windowWidth/2 + v1.getY(), 5, 5);

        // g2.drawLine(w1.getP1().getX(), w1.getP1().getY(), w1.getP3().getX(), w1.getP3().getY());
        // g2.drawLine(w1.getP1().getX(), w1.getP1().getY(), w1.getP2().getX(), w1.getP2().getY());
        // g2.drawLine(w1.getP4().getX(), w1.getP4().getY(), w1.getP2().getX(), w1.getP2().getY());
        // g2.drawLine(w1.getP4().getX(), w1.getP4().getY(), w1.getP3().getX(), w1.getP3().getY());
        // g2.drawLine(w1.getP4().getX(), w1.getP4().getY(), w1.getP1().getX(), w1.getP1().getY());

        // g2.drawLine(windowWidth/2 - v1.getX(), windowHeight/2 - v1.getY(), windowWidth/2 - v2.getX(), windowHeight/2 - v2.getY());
        // g2.drawLine(windowWidth/2, windowHeight/2, windowWidth/2 + (int)(20 * Math.cos(c.getRot())), windowHeight/2 + (int)(20 * Math.sin(c.getRot())));

        g2.dispose();

    }
}