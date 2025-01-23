import java.util.ArrayList;

public class Camera {
    private float x;
    private float y;
    private float z;
    private float rot;
    private float pitch;
    private float FOV;
    private int height;
    private int camSpeed = 2;
    private float velocity = 0;
    private float velocityUp = 0;
    private float movementRot = 0;
    private float floorHeight = 0;
    private float newX;
    private float newY;

    
    public Camera(int x, int y, int z, float rot, int FOV) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = 50;
        this.rot = (float) Math.toRadians(rot);
        this.FOV = (float) Math.toRadians(FOV);
        this.pitch = 0;
    }

    public void moveForward() {
        this.velocity = camSpeed;
        this.movementRot = 0;
    }

    public void moveBackward() {
        this.velocity = camSpeed;
        this.movementRot = (float) Math.PI;
    }

    public void moveLeft() {
        this.velocity = camSpeed;
        if (this.movementRot == 0) {
            this.movementRot = (float) (-Math.PI/4);
        } else if (this.movementRot == Math.PI) {
            this.movementRot = (float) (-3*Math.PI/4);
        } else {
            this.movementRot = (float) (-Math.PI/2);
        }
    }

    public void moveRight() {
        this.velocity = camSpeed;
        if (this.movementRot == 0) {
            this.movementRot = (float) (Math.PI/4);
        } else if (this.movementRot == Math.PI) {
            this.movementRot = (float) (3*Math.PI/4);
        } else {
            this.movementRot = (float) (Math.PI/2);
        }
    }

    public void sprint() {
        this.velocity = this.velocity*2;
    }

    public void moveRot(double deltaRot) {
        this.rot += Math.toRadians(deltaRot);
        if (this.rot > 2*Math.PI) {
            this.rot -= 2*Math.PI;
        } else if (this.rot < 0) {
            this.rot += 2*Math.PI;
        }
    }

    public void movePitch(double deltaPitch) {
        this.pitch += Math.toRadians(deltaPitch);
        if (this.pitch > (Math.PI/2)) {
            this.pitch = (float) (Math.PI/2);
        } else if (this.pitch < -(Math.PI/2)) {
            this.pitch = (float) -(Math.PI/2);
        }
    }

    public void setFloorHeight(double floorHeight) {
        this.floorHeight = (float) floorHeight;
    }

    public void setFOV( int FOV) {
        this.FOV = FOV;
    }

    public void jump() {
        // if (this.z == this.floorHeight+this.height) {
            this.velocityUp += 4;
        // }
    }

    public void calculateNewCordinates() {
        this.newX = (float) (this.x + Math.cos(this.movementRot+this.rot) * camSpeed * velocity);
        this.newY = (float) (this.y + Math.sin(this.movementRot+this.rot) * camSpeed * velocity);
    }

    public void update() {
        this.x = this.newX;
        this.y = this.newY;
        this.z += this.velocityUp;

        if (this.velocity > 0) {
            this.velocity -= 0.05;
        } else {
            this.velocity = 0;
        }

        if (this.z > this.floorHeight+this.height) {
            this.velocityUp -= 0.30;
        } else {
            this.z = this.floorHeight+this.height;
            this.velocityUp = 0;
        }
    }

    public void checkCollisions(Floor floor) {

        for (int i = 0; i < 3; i++) {
            Vertex3D v1 = floor.getVertex(i);
            Vertex3D v2 = floor.getVertex((i+1)%3);
            // checkLineCollisions(v1, v2);
        }
    }

    private void checkLineCollisions(Vertex3D v1, Vertex3D v2) {

        double x1 = v1.getX();
        double y1 = v1.getY();
        double x2 = v2.getX();
        double y2 = v2.getY();

        double theta;

        if (x1 == x2) {
            theta = -Math.PI/2;
        } else {
            theta = Math.atan2((y2-y1),(x2-x1));
        }

        if (theta == -Math.PI/2 || theta == 0) {
            double x = this.newX - x1;
            double y = this.newY - y1;
    
            this.newX = (float) (x*Math.cos(theta) + y*Math.sin(theta));
            this.newY = (float) (y*Math.cos(theta) - x*Math.sin(theta));
    
            x = this.newX;
            y = this.newY;
            if (y < 15) {
                y = 15;   
            }
    
            this.newX = (float) (x*Math.cos(-theta) + y*Math.sin(-theta));
            this.newY = (float) (y*Math.cos(-theta) - x*Math.sin(-theta));
    
            this.newX += x1;
            this.newY += y1;
            
        }
    }

    public void stop() {
        this.velocity = 0;
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

    public Vertex3D toVertex() {
        return new Vertex3D(this.x, this.y, this.z);
    }
}
