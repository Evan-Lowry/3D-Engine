import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    private Map map = new Map("Map.tsv");
    private Color[] textures = {Color.BLUE.darker(), Color.BLUE.darker().darker(), Color.RED.darker(), Color.RED.darker().darker(), Color.ORANGE.darker(), Color.ORANGE.darker().darker()};
    private Drawing drawing = new Drawing(textures);

    static double fullscreen = 1;

    static int windowHeight = (int)(fullscreen*1080);
    static int windowWidth = (int)(fullscreen*1920);
    
    static int centerY = windowHeight/2;
    static int centerX = windowWidth/2;

    int FPS = 60;

    public static Camera c = new Camera(100,-100,50,0,90);

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
                remainingTime = remainingTime / 1000000;

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

        if (keyH.upPressed) {
            c.moveForward();
        }
        if (keyH.downPressed) {
            c.moveBackward();
        }
        if (keyH.leftPressed) {
            c.moveLeft();
        }
        if (keyH.rightPressed) {
            c.moveRight();
        }
        if (keyH.turnLeftPressed) {
            c.moveRot(-0.5 * 3);
        }
        if (keyH.turnRightPressed) {
            c.moveRot(0.5 * 3);
        }
        if (keyH.turnUpPressed) {
            c.movePitch(0.5 * 3);
        }
        if (keyH.turnDownPressed) {
            c.movePitch(-0.5 * 3);
        }

        if (keyH.spacePressed) {
            c.jump();
            keyH.spacePressed = false;
        }

        if (keyH.shiftPressed) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                c.sprint();
            }
        }

        if (mouseA.mouseMoved) {
            c.moveRot(mouseA.rot);
            c.movePitch(mouseA.pitch);
            mouseA.mouseMoved = false;
        }

        mouseA.recenterMouse();
        c.setFloorHeight(this.map.getSector().getFloor(c).getFloorHeight());
        c.calculateNewCordinates();
        Floor currentFloor  = this.map.getSector().getFloor(c);
        c.checkCollisions(currentFloor);
        c.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Triangle[] triangles = map.getSector().getTriangles();
        Floor floor = map.getSector().getFloor(c);

        Arrays.sort(triangles, (t1, t2) -> Double.compare(t2.getDepthFromCamera(), t1.getDepthFromCamera()));

        for (int i = 0; i < triangles.length; i++) {
            Triangle t = triangles[i];
            t.update();
            drawing.drawTriangle(g2, t);
        }
        for (Triangle t : triangles) {
            // drawing.triangleDebug(g2, t);
        }

        // drawing.camDebug(g2, c);

        g2.dispose();
    }
}