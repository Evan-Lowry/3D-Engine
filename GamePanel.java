import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    private Map map = new Map("ExportedData/Map.obj");
    static Textures texturess = new Textures("Textures");
    static Color[] textures = {Color.RED.darker(), Color.GREEN.darker(), Color.BLUE.darker(), Color.YELLOW.darker(), Color.white.darker()};
    private Drawing drawing = new Drawing(textures);

    static float fullscreen = 1;

    static int windowHeight = (int)(fullscreen*1080);
    static int windowWidth = (int)(fullscreen*1920);
    
    static int centerY = windowHeight/2;
    static int centerX = windowWidth/2;

    int FPS = 60;

    public static Camera c = new Camera(1300,-300,10000,180,90);

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
        c.calculateNewCordinates();
        c.setFloorHeight(0);
        // Floor currentFloor  = this.map.getSector().getFloor(c);
        // c.checkCollisions(currentFloor);
        c.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        Triangle[] triangles = map.getSector().getTriangles();
        // Floor floor = map.getSector().getFloor(c);

        Arrays.sort(triangles, (t1, t2) -> Double.compare(t2.getDepthFromCamera(), t1.getDepthFromCamera()));

        for (int i = 0; i < triangles.length; i++) {
            Triangle t = triangles[i];
            t.update();
            drawing.drawTexturedTriangle(g2, t, texturess.getTexture(0));
        }
        for (Triangle t : triangles) {
            // drawing.triangleDebug(g2, t);
        }

        // drawing.camDebug(g2, c);

        g2.dispose();
    }
}