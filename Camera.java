public class Camera {
    int x;
    int y;
    int z;
    double rot;
    int FOV;

    public Camera(int x, int y, int z, double rot, int FOV) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rot = Math.toRadians(rot);
        this.FOV = FOV;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public double getRot() {
        return this.rot;
    }

    public int getFOV() {
        return this.FOV;
    }

    public int[] getPos() {
        int[] cordinates = {this.x, this.y, this.z};
        return cordinates;
    }

    public void moveX(int deltaX) {
        this.x += deltaX;
    }

    public void moveY(int deltaY) {
        this.y += deltaY;
    }

    public void moveZ(int deltaZ) {
        this.z += deltaZ;
    }

    public void moveForward(int delta) {
        this.x += delta * Math.cos(this.rot);
        this.y += delta * Math.sin(this.rot);
    }

    public void moveSideways(int delta) {
        this.x += delta * Math.sin(this.rot);
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

    public void setFOV( int FOV) {
        this.FOV = FOV;
    }

    public void move(int deltaX, int deltaY, int deltaZ) {
        this.x += deltaX;
        this.y += deltaY;
        this.z += deltaZ;
    }
}
