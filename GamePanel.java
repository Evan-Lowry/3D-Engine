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

    private Map map = new Map("Map copy");
    private Color[] textures = {Color.BLUE.darker(), Color.BLUE.darker().darker(), Color.RED.darker(), Color.RED.darker().darker(), Color.ORANGE.darker(), Color.ORANGE.darker().darker()};

    static double fullscreen = 1;

    static int windowHeight = (int)(fullscreen*1080);
    static int windowWidth = (int)(fullscreen*1920);
    
    static int centerY = windowHeight/2;
    static int centerX = windowWidth/2;

    int FPS = 60;

    public static Camera c = new Camera(300,150,50,180,90);
    int camSpeed = 3;

    KeyHandler keyH = new KeyHandler();
    MouseAiming mouseA = new MouseAiming();
    Thread gameThread;

    

    public GamePanel(){
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseMotionListener(mouseA);
        this.setFocusable(true);
        this.setVisible(true);
        this.setFocusTraversalKeysEnabled(false);
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
        if (keyH.spacePressed == true) {
            c.jump();
            // System.out.println("JUMP");
        }

        if (mouseA.mouseMoved == true) {
            c.moveRot(mouseA.rot);
            c.movePitch(mouseA.pitch);
            mouseA.mouseMoved = false;
        }
        mouseA.recenterMouse();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Triangle[] triangles = map.getSector().getTriangles();

        Arrays.sort(triangles, (t1, t2) -> Double.compare(t2.getDepthFromCamera(), t1.getDepthFromCamera()));

        for (int i = 0; i < triangles.length; i++) {
            Triangle t = triangles[i];
            t.update();
            drawTriangle(g2, t);
        }
        for (Triangle t : triangles) {
            // triangleDebug(g2, t);
        }

        // camDebug(g2, c);

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

    private void triangleDebug (Graphics g2, Triangle t) {
        Vertex v1 = t.getV1();
        Vertex v2 = t.getV2();
        Vertex v3 = t.getV3();
 
        g2.setColor(Color.WHITE);
        g2.drawLine(v3.getIX(),v3.getIY(),v1.getIX(),v1.getIY());
    }

    private void drawTriangle (Graphics2D g2, Triangle t) {
        if (t.isValid() == false) {
            return;
        }
        g2.setColor(textures[t.getColorIndex()]);
        int[] cordinatesX = {t.getP1().getX(), t.getP2().getX(), t.getP3().getX(), t.getP4().getX()};
        int[] cordinatesY = {t.getP1().getY(), t.getP2().getY(), t.getP3().getY(), t.getP4().getY()};
        g2.fillPolygon(cordinatesX, cordinatesY, t.getNumberOfVertices());
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
        g2.drawOval(x - magnitude/2,y - magnitude/2, magnitude, magnitude);
    }
}