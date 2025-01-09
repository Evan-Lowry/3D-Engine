public class Camera {
    double x;
    double y;
    double z = 0;
    double rot;
    double pitch;
    double FOV;
    int height;
    double velocityUp = 0;
    double floorHeight = 0;
    double velocityForward;
    double velocitySideways;
    
    public Camera(int x, int y, int z, double rot, int FOV) {
        this.x = x;
        this.y = y;
        this.height = z;
        this.rot = Math.toRadians(rot);
        this.FOV = Math.toRadians(FOV);
        this.pitch = 0;
    }

    public int getX() {
        return (int)this.x;
    }

    public int getY() {
        return (int)this.y;
    }

    public int getZ() {
        return (int)this.z;
    }

    public double getRot() {
        return this.rot;
    }

    public double getPitch() {
        return this.pitch;
    }

    public double getFOV() {
        return this.FOV;
    }

    public int[] getPos() {
        int[] cordinates = {(int)this.x, (int)this.y, (int)this.z};
        return cordinates;
    }

    public void moveX(double deltaX) {
        this.x += deltaX;
    }

    public void moveY(double deltaY) {
        this.y += deltaY;
    }

    public void moveZ(double deltaZ) {
        this.z += deltaZ;
    }

    public void moveForward(double delta) {
        this.x += delta * Math.cos(this.rot);
        this.y += delta * Math.sin(this.rot);
    }

    public void moveSideways(double delta) {
        this.x -= delta * Math.sin(this.rot);
        this.y += delta * Math.cos(this.rot);
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
            this.pitch = (Math.PI/2);
        } else if (this.pitch < -(Math.PI/2)) {
            this.pitch = -(Math.PI/2);
        }
    }

    public void setFloorHeight(double floorHeight) {
        this.floorHeight = floorHeight;
    }

    public void setFOV( int FOV) {
        this.FOV = FOV;
    }

    public void move(int deltaX, int deltaY, int deltaZ) {
        this.x += deltaX;
        this.y += deltaY;
        this.z += deltaZ;
    }

    public void jump() {
        if (this.z == this.floorHeight+this.height) {
            this.velocityUp = 5;
        }
    }

    public void update() {

        this.x += this.velocityForward;
        this.y += this.velocitySideways;
        this.z += this.velocityUp;

        if (this.z > this.floorHeight+this.height) {
            this.velocityUp -= 0.30;
        } else {
            this.z = this.floorHeight+this.height;
            this.velocityUp = 0;
        }
    }
}
