import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Camera {
    // stores location, rotation and FOV data
    private float x, y, z, rot, pitch, FOV;
    private int height;
    // used for camera physics
    private int camSpeed = 2;
    // stores velocity on x, y plane
    private float velocity = 0;
    // stores z velocity
    private float velocityUp = 0;
    // stores the current number of jumps since touching ground
    private float numJumps = 0;
    // rotation applied to x, y velocity
    private float movementRot = 0;
    // floor height
    private float floorHeight = 0;
    // used to store the next coordinates for the camera
    private float newX, newY;

    
    public Camera(int x, int y, int z, float rot, int FOV) {
        // sets all variables to inputed stating coordinates
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = 50;
        this.rot = (float) Math.toRadians(rot);
        this.FOV = (float) Math.toRadians(FOV);
        this.pitch = 0;

        // if possible tries to read camera save data
        try {
            readSave();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // reads in save data to apply to camera loc and rot
    private void readSave() {
        try {
            // accesses file location
            File saveFile = new File("SaveData/Camera.csv");
            Scanner input = new Scanner(saveFile);
            // clears first line
            input.nextLine();
            // takes data and splits it
            String[] data = input.nextLine().split(",");
            // reads through data and sets variables
            this.x = Float.parseFloat(data[0]);
            this.y = Float.parseFloat(data[1]);
            this.z = Float.parseFloat(data[2]);
            this.rot = Float.parseFloat(data[3]);
            this.pitch = Float.parseFloat(data[4]);
            // closes scanner
            input.close();
        } catch (FileNotFoundException e) {
            // if read in fails camera will use previously set data
            e.printStackTrace();
        }
    }

    // used before program closes to save data
    public void saveData() {
        // create a PrintWriter from the file
        PrintWriter output = null;
        try{
            output =  new PrintWriter("SaveData/Camera.csv");
        }catch(Exception e){
            e.printStackTrace();
        }
        // adds the header line
        output.println("x,y,z,rot,pitch");
        // writes out camera location data
        output.println(this.x + "," + this.y + "," + this.z  + "," + this.rot  + "," + this.pitch);
        // closes the PrintWriter
        output.close();
    }

    // used to adjust velocities and rotations that move the player

    public void moveForward() {
        // sets the camera speed to the camSpeed
        if (this.velocity < camSpeed) {
            this.velocity = camSpeed;
        }
        // points the movement vector forward
        this.movementRot = 0;
    }

    public void moveBackward() {
        // sets the camera speed to the camSpeed
        if (this.velocity < camSpeed) {
            this.velocity = camSpeed;
        }
        // points movement vectore backward
        this.movementRot = (float) Math.PI;
    }

    public void moveLeft() {
        // sets the camera speed to the camSpeed
        if (this.velocity < camSpeed) {
            this.velocity = camSpeed;
        }
        // if moving forward
        if (this.movementRot == 0) {
            // turn vector 45 degrees left
            this.movementRot = (float) (-Math.PI/4);
            // if moving backward
        } else if (this.movementRot == Math.PI) {
            // turn vector 45 degrees left
            this.movementRot = (float) (-3*Math.PI/4);
            // if not moving forward or backward
        } else {
            // set vector left
            this.movementRot = (float) (-Math.PI/2);
        }
    }

    public void moveRight() {
        // sets the camera speed to the camSpeed
        if (this.velocity < camSpeed) {
            this.velocity = camSpeed;
        }
        // if moving forward
        if (this.movementRot == 0) {
            // turn vector 45 degrees right
            this.movementRot = (float) (Math.PI/4);
            // if moving backward
        } else if (this.movementRot == Math.PI) {
            // turn vector 45 degrees right
            this.movementRot = (float) (3*Math.PI/4);
            // if not moving forward or backward
        } else {
            // set vector left
            this.movementRot = (float) (Math.PI/2);
        }
    }

    public void sprint() {
        // doubles velocity
        this.velocity = this.camSpeed*2;
    }

    // moves camera view

    public void moveRot(double deltaRot) {
        // converts to radians
        this.rot += Math.toRadians(deltaRot);
        // keeps rotation between 0 and 2PI
        if (this.rot > 2*Math.PI) {
            this.rot -= 2*Math.PI;
        } else if (this.rot < 0) {
            this.rot += 2*Math.PI;
        }
    }

    public void movePitch(double deltaPitch) {
        // converts to radians
        this.pitch += Math.toRadians(deltaPitch);
        // makes sure you can only look up/down 90 degrees
        // keeps the rotation between PI/2 to -PI/2
        if (this.pitch > (Math.PI/2)) {
            this.pitch = (float) (Math.PI/2);
        } else if (this.pitch < -(Math.PI/2)) {
            this.pitch = (float) -(Math.PI/2);
        }
    }

    // makes the player jump
    public void jump() {
        // if player is on the ground
        if (this.numJumps < 2) {
            // adds to the velocity upward
            this.velocityUp += 4;
            // increases the number of jumps
            this.numJumps++;
        }
    }

    // resets the player position
    public void resetLocation () {
        this.x = 100;
        this.y = -250;
        this.z = 250;
        this.rot = 0;
        this.newX = 100;
        this.newY = -250;
        this.numJumps = 0;
        this.velocity = 0;
    }

    // uses current velocities to generate the new coordinates
    // prior to collision detection
    public void calculateNewCordinates() {
        this.newX = (float) (this.x + Math.cos(this.movementRot+this.rot) * camSpeed * velocity);
        this.newY = (float) (this.y + Math.sin(this.movementRot+this.rot) * camSpeed * velocity);
    }

    // updates all location variables using velocities
    public void update() {
        // updates x, y, z
        this.x = this.newX;
        this.y = this.newY;
        this.z += this.velocityUp;

        if (this.z < -250 + this.height) {
            resetLocation();
        }

        // if the velocity is positive
        if (this.velocity > 0) {
            // decelerate
            this.velocity -= 0.1;
            // if negative
        } else {
            // set to 0
            this.velocity = 0;
        }

        // if player is in the air
        if (this.z > this.floorHeight+this.height) {
            // accelerate downward
            this.velocityUp -= 0.30;
            // if player is below ground
        } else {
            // set player to ground height
            this.z = this.floorHeight+this.height;
            // stop movement upward/downward
            this.velocityUp = 0;
            // sets number of jumps in air to 0
            this.numJumps = 0;
        }
    }

    // given a floor it keeps the camera withing its bounding box
    public void checkCollisions(Floor floor) {

        if (floor == null) {
            return;
        }

        ArrayList <Wall> walls = floor.getWalls();

        for (Wall w : walls) {
            if (this.z - this.height + 20 > w.getMinZ() && this.z - this.height + 20 < w.getMaxZ()) {
                checkLineCollisions(w.getPoint1(), w.getPoint2(), w.getNormal());
            }
        }
    }

    // checks axis aligned collisions with floors
    private void checkAACollisions() {

    }

    private void checkLineCollisions(Vertex2D v1, Vertex2D v2, Normal n) {

        double x1 = v1.getX();
        double y1 = v1.getY();
        double x2 = v2.getX();
        double y2 = v2.getY();
        float length = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));


        double theta;
        if (x1 == x2) {
            if (y1 <= y2) {
                theta = Math.PI/2;
            } else {
                theta = -Math.PI/2;
            }
        } else {
            theta = Math.atan2((y2-y1),(x2-x1));
        }
        if (theta == -Math.PI/2 || theta == Math.PI/2 || theta == 0 || theta == Math.PI || theta == -Math.PI) {
            double x = this.newX - x1;
            double y = this.newY - y1;

            double normalY;
    
            this.newX = (float) (x*Math.cos(theta) + y*Math.sin(theta));
            this.newY = (float) (y*Math.cos(theta) - x*Math.sin(theta));

            normalY = (float) (n.getY()*Math.cos(theta) - n.getX()*Math.sin(theta));
    
            x = this.newX;
            y = this.newY;

            if (normalY > 0) {
                if (this.newX > -20 && this.newX < length + 20) {
                    if (y < 25 && y > 0) {
                        y = 25; 
                    }
                }
            } else if (normalY < 0) {
                if (this.newX > -20 && this.newX < length + 20) {
                    if (y > -25 && y < 0) {
                        y = - 25;
                    }
                }
            }
    
            this.newX = (float) (x*Math.cos(-theta) + y*Math.sin(-theta));
            this.newY = (float) (y*Math.cos(-theta) - x*Math.sin(-theta));
    
            this.newX += x1;
            this.newY += y1;
            
        }
    }

    // stops the player
    public void stop() {
        this.velocity = 0;
    }

    // setters and getters

    public void setFloorHeight(float floorHeight) {
        this.floorHeight = floorHeight;
    }

    public void setFOV( int FOV) {
        this.FOV = FOV;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getNewX() {
        return this.newX;
    }

    public float getNewY() {
        return this.newY;
    }

    public float getRot() {
        return this.rot;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getFOV() {
        return this.FOV;
    }

    // converts camera to 3D Vertex
    public Vertex3D toVertex() {
        return new Vertex3D(this.x, this.y, this.z);
    }
}
