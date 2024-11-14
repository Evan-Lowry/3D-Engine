import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
// import java.awt.RenderingHints.Key;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    static int windowHeight = (int)(0.5*1080);
    static int windowWidth = (int)(0.5*1920);
    
    static int centerY = windowHeight/2;
    static int centerX = windowWidth/2;

    int FPS = 60;

    public Camera c = new Camera(0,0,0,0,10);
    int camSpeed = 3;

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
                // double gh = drawInterval - remainingTime;
                // System.out.println(1000000000/gh);
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void update(){

        // System.out.println(keyH.upPressed);

        if (keyH.upPressed == true) {
            c.moveForward(camSpeed);
            // System.out.println("UP");
        }
        if (keyH.downPressed == true) {
            c.moveForward(-camSpeed);
            // System.out.println("DOWN");
        }
        if (keyH.leftPressed == true) {
            c.moveSideways(-camSpeed);
            // System.out.println("LEFT");
        }
        if (keyH.rightPressed == true) {
            c.moveSideways(camSpeed);
            // System.out.println("RIGHT");
        }
        if (keyH.turnLeftPressed == true) {
            c.moveRot(-0.5 * camSpeed);
            // System.out.println("TURNLEFT");
        }
        if (keyH.turnRightPressed == true) {
            c.moveRot(0.5 * camSpeed);
            // System.out.println("TURNRIGHT");
        }

        // c.moveRot(1);
        // System.out.println(c.getRot());
        // c.moveY(0);
        // c.moveX(1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Vertex v1 = new Vertex(300,50,0);
        Vertex v2 = new Vertex(300,-50,0);
        Wall w1 = new Wall(c,v1,v2);

        // drawWall(g2, w1);
        drawDebug(g2, c, w1, v1, v2);

        g2.dispose();

    }

    private void drawDebug (Graphics2D g2, Camera c, Wall w, Vertex v1, Vertex v2) {
        g2.setColor(Color.BLUE);
        // System.out.println(w1.info.x);
        drawPointer(g2, c.getX(), c.getY(), w.info.getZ(), c.getRot());
        drawPointer(g2, c.getX(), c.getY(), w.info.getX(), c.getRot() + Math.toRadians(90));
        g2.drawOval(windowWidth/2 + v1.getX()-2, windowHeight/2 + v1.getY()-2, 4, 4);
        g2.drawOval(windowWidth/2 + v2.getX()-2, windowHeight/2 + v2.getY()-2, 4, 4);
        g2.drawOval(windowWidth/2 + c.getX()-3, windowHeight/2 + c.getY()-3, 6, 6);

        g2.setColor(Color.WHITE);
        g2.drawOval(windowWidth/2 + c.getX()-50, windowHeight/2 + c.getY()-50, 100, 100);
        // drawRadius(g2, c.getX(), c.getY(), 2*w.info.getZ());
        drawPointer(g2, c.getX(), c.getY(), 50, c.getRot());
    }

    private void drawWall (Graphics2D g2, Wall w) {
        if (w.isValid()) {
            g2.setColor(Color.RED);
            g2.drawLine(w.getP1().getX(), w.getP1().getY(), w.getP3().getX(), w.getP3().getY());
            g2.drawLine(w.getP1().getX(), w.getP1().getY(), w.getP2().getX(), w.getP2().getY());
            g2.drawLine(w.getP4().getX(), w.getP4().getY(), w.getP2().getX(), w.getP2().getY());
            g2.drawLine(w.getP4().getX(), w.getP4().getY(), w.getP3().getX(), w.getP3().getY());
            g2.drawLine(w.getP4().getX(), w.getP4().getY(), w.getP1().getX(), w.getP1().getY());
        }
    }

    public void drawPointer(Graphics2D g2, int x, int y, int magnitude, double angle) {
        double deltaX;
        double deltaY;

        // System.out.println(angle);

        deltaX = Math.cos(angle) * magnitude;
        deltaY = Math.sin(angle) * magnitude;

        // g2.setColor(Color.RED);
        g2.drawLine(centerX + x, centerY + y, centerX + x + (int)deltaX, centerY + y + (int)deltaY);
    }

    public void drawRadius(Graphics2D g2, int x, int y, int magnitude) {
        g2.setColor(Color.RED);
        g2.drawOval(centerX + x - magnitude/2, centerY + y - magnitude/2, magnitude, magnitude);
    }
}