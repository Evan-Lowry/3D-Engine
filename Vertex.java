public class Vertex {
    public double x;
    public double y;
    public double z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double getDX() {
        return this.x;
    }

    public double getDY() {
        return this.y;
    }

    public double getDZ() {
        return this.z;
    }

    public int[] get() {
        int[] cordinates = {(int)this.x, (int)this.y, (int)this.z};
        return cordinates;
    }

    public void moveX( double deltaX) {
        this.x += deltaX;

    }

    public void moveY( double deltaY) {
        this.y += deltaY;
    }

    public void moveZ( double deltaZ) {
        this.z += deltaZ;
    }

    public void move(double deltaX, double deltaY, double deltaZ) {
        this.x += deltaX;
        this.y += deltaY;
        this.z += deltaZ;
    }

    public void setX( double x) {
        this.x = x;
    }

    public void setY( double y) {
        this.y = y;
    }

    public void setZ( double z) {
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}