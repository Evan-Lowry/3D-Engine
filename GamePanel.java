import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
// import java.awt.RenderingHints.Key;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    private Map map = new Map("Map");
    private Color[] textures = {Color.BLUE.darker(), Color.BLUE.darker().darker(), Color.RED.darker(), Color.RED.darker().darker(), Color.ORANGE.darker(), Color.ORANGE.darker().darker()};

    static int windowHeight = (int)(0.5*1080);
    static int windowWidth = (int)(0.5*1920);
    
    static int centerY = windowHeight/2;
    static int centerX = windowWidth/2;

    int FPS = 60;

    public static Camera c = new Camera(300,150,0,0,90);
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

        update();

        repaint();

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
        if (keyH.turnUpPressed == true) {
            c.movePitch(0.5 * camSpeed);
            // System.out.println("TURNLEFT");
        }
        if (keyH.turnDownPressed == true) {
            c.movePitch(-0.5 * camSpeed);
            // System.out.println("TURNRIGHT");
        }
        // System.out.println(c.getPitch());

        // c.moveRot(1);
        // System.out.println(c.getRot());
        // c.moveY(0);
        // c.moveX(1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Triangle[] triangles = map.getSector().getWalls();

        Arrays.sort(walls, (w1, w2) -> Double.compare(w2.getDepthFromCamera(), w1.getDepthFromCamera()));

        drawFloor(g2, new Floor());

        for (int i = 0; i < walls.length; i++) {
            Wall w = walls[i];
            w.update();
            drawWall(g2, w);
        }
        for (Wall w : walls) {
            wallDebug(g2, w);
        }
        // System.out.println();

        camDebug(g2, c);

        g2.dispose();
    }

    private void camDebug (Graphics2D g2, Camera c) {
        g2.setColor(Color.WHITE);
        // g2.drawOval(windowWidth/2 + c.getX()-25, windowHeight/2 + c.getY()-25, 50, 50);
        g2.drawOval(c.getX()-10, c.getY()-10, 20, 20);

        drawPointer(g2, c.getX(), c.getY(), 25, c.getRot());
        drawPointer(g2, c.getX(), c.getY(), 500, c.getRot()+(c.getFOV()/2));
        drawPointer(g2, c.getX(), c.getY(), 500, c.getRot()-(c.getFOV()/2));
        drawPointer(g2, c.getX(), c.getY(), 1000, c.getRot()+(Math.PI/2));
        drawPointer(g2, c.getX(), c.getY(), 1000, c.getRot()-(Math.PI/2));
    }

    private void wallDebug (Graphics g2, Wall w) {
        Vertex v1 = w.getV1();
        Vertex v2 = w.getV2();

        // System.out.println(v1.getX() + ", " + v1.getY());

        g2.setColor(Color.WHITE);
        g2.drawLine(v1.getX(),v1.getY(),v2.getX(),v2.getY());
        // g2.drawLine(windowWidth/2 + v1.getX(), windowHeight/2 + v1.getY(), windowWidth/2 + v2.getX(), windowHeight/2 + v2.getY());
    }

    private void drawFloor (Graphics2D g2, Floor f) {
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, centerY, windowWidth, centerY);
        g2.setColor(Color.DARK_GRAY.darker());
        g2.fillRect(0, 0, windowWidth, centerY);
    }

    private void drawTriangle (Graphics2D g2, Triangle t) {
        if (t.isValid()) {
            g2.setColor(textures[t.getColorIndex()-1]);
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
        g2.drawLine(x, y, x + (int)deltaX,y + (int)deltaY);
        // g2.drawLine(centerX + x, centerY + y, centerX + x + (int)deltaX, centerY + y + (int)deltaY);
    }

    public void drawRadius(Graphics2D g2, int x, int y, int magnitude) {
        g2.setColor(Color.RED);
        g2.drawOval(centerX + x - magnitude/2, centerY + y - magnitude/2, magnitude, magnitude);
    }
}