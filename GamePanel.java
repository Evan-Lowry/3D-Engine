import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
// import java.awt.RenderingHints.Key;
import java.awt.Polygon;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    static int windowHeight = (int)(0.5*1080);
    static int windowWidth = (int)(0.5*1920);
    
    static int centerY = windowHeight/2;
    static int centerX = windowWidth/2;

    int FPS = 60;

    public Camera c = new Camera(-300,0,0,0,20);
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
        Wall w1 = new Wall(c,new Vertex(-50, -50, 0),new Vertex(-50, 50, 0), Color.RED);
        Wall w2 = new Wall(c,new Vertex(-50, 50, 0),new Vertex(50, 50, 0), Color.BLUE);
        Wall w3 = new Wall(c,new Vertex(50, 50, 0),new Vertex(50, -50, 0), Color.YELLOW);
        Wall w4 = new Wall(c,new Vertex(50, -50, 0),new Vertex(-50, -50, 0), Color.GREEN);



        drawWall(g2, w1);
        drawWall(g2, w2);
        drawWall(g2, w3);
        drawWall(g2, w4);
        camDebug(g2, c);
        wallDebug(g2, w1);
        wallDebug(g2, w2);
        wallDebug(g2, w3);
        wallDebug(g2, w4);

        g2.dispose();
    }

    private void camDebug (Graphics2D g2, Camera c) {
        g2.setColor(Color.WHITE);
        g2.drawOval(windowWidth/2 + c.getX()-25, windowHeight/2 + c.getY()-25, 50, 50);
        drawPointer(g2, c.getX(), c.getY(), 25, c.getRot());
    }

    private void wallDebug (Graphics g2, Wall w) {
        Vertex v1 = w.getV1();
        Vertex v2 = w.getV2();

        g2.setColor(w.getColor());
        g2.drawLine(windowWidth/2 + v1.getX(), windowHeight/2 + v1.getY(), windowWidth/2 + v2.getX(), windowHeight/2 + v2.getY());
    }

    private void drawWall (Graphics2D g2, Wall w) {
        if (w.isValid()) {
            g2.setColor(w.getColor());
            int[] cordinatesX = {w.getP1().getX(), w.getP2().getX(), w.getP4().getX(), w.getP3().getX()};
            int[] cordinatesY = {w.getP1().getY(), w.getP2().getY(), w.getP4().getY(), w.getP3().getY()};
            g2.fillPolygon(cordinatesX, cordinatesY, 4);
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