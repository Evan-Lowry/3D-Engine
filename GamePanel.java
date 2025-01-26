import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.PrintWriter;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    // creates textures to load in and store pngs as textures
    static Textures texturess = new Textures("Textures");
    // creates a map to load in and store obj files
    static Map map = new Map("ExportedData/Map.obj");
    static Color[] textures = {Color.RED.darker(), Color.GREEN.darker(), Color.BLUE.darker(), Color.YELLOW.darker(), Color.white.darker()};
    // creates a drawing object to handle all drawing of objects to screen
    static Drawing drawing = new Drawing();
    // used to store the resolution of the window measured as a percentage of 1920 x 1080
    static float fullscreen = (float) 0.5;
    // sets the resolution variables in accordance with the fullscreen variables
    static int windowHeight = (int)(fullscreen*1080);
    static int windowWidth = (int)(fullscreen*1920);
    // calculates the coordinates of center of the window
    static int centerY = windowHeight/2;
    static int centerX = windowWidth/2;
    // sets the target fram rate
    static int FPS = 40;
    // creates a new camera with inputed starting location rotation and FOV
    public static Camera c = new Camera(100,-100,0,180,90);
    // creates a KeyHandler to read and store key inputs
    KeyHandler keyH = new KeyHandler();
    // creates a MouseAiming object to read and store mouse position
    MouseAiming mouseA = new MouseAiming();
    // creates a thread to run game logic through
    Thread gameThread;

    public GamePanel(){
        // sets size of window
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        // sets background color to black
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // makes sure the key and mouse listeners can detect input
        this.addKeyListener(keyH);
        this.addMouseMotionListener(mouseA);
        this.setFocusable(true);
        this.setVisible(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    // starts the game thread to run logic through
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    // runs through the game loop
    @Override
    public void run() {

        // calculates the draw interval
        // the number of nanoseconds allowed for each frame
        double drawInterval = 1000000000/FPS;
        // takes the system time and adds the draw interval
        // calculate the time to start rendering the next frame
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
            // update method updates all variables
            update();
            // draws everything to the screen
            repaint();

            try {
                // calculates the remaining time to pause before drawing the next frame
                double remainingTime = nextDrawTime - System.nanoTime();
                // converts from nanoseconds to microseconds
                remainingTime = remainingTime / 1000000;

                // makes sure remaining time isn't negative
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                // puts the thread to sleep
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // updates player position
    public void update(){

        // checks for key input and calls appropriate camera method
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
            // makes sure you can't sprint while not moving
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                c.sprint();
            }
        }
        // takes the mouse movment since last update and applies it to the camera
        if (mouseA.mouseMoved) {
            c.moveRot(mouseA.rot);
            c.movePitch(mouseA.pitch);
            mouseA.mouseMoved = false;
        }
        mouseA.recenterMouse();

        // uses all input data to calculate new location
        c.calculateNewCordinates();
        // checks the floor height of the current floor
        // c.setFloorHeight(0);

        c.checkCollisions(map.getSector().getFloor());
        // updates the camera variables using the calculations
        c.update();

        // updates the location of all vertexes in respect to the camera
        map.updateVertexs();
    }

    // the actual drawing method
    public void paintComponent(Graphics g) {
        // adds in a graphics object to draw with
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // calls drawing object to draw everything to screen
        drawing.draw(g2);

        g2.dispose();
    }


    public static void exit() {
        // saves the camera position and location data
        c.saveData();
        // ends program
        System.exit(0);
    }
}