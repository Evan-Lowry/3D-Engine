package Game.main;
public class Vertex {
    private int x;
    private int y;
    private int z;

    public Vertex(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public int[] get() {
        int[] cordinates = {this.x, this.y, this.z};
        return cordinates;
    }

    public void moveX( int deltaX) {
        this.x += deltaX;

    }

    public void moveY( int deltaY) {
        this.y += deltaY;
    }

    public void moveZ( int deltaZ) {
        this.z += deltaZ;
    }

    public void move(int deltaX, int deltaY, int deltaZ) {
        this.x += deltaX;
        this.y += deltaY;
        this.z += deltaZ;
    }

    public void setX( int x) {
        this.x = x;
    }

    public void setY( int y) {
        this.y = y;
    }

    public void setZ( int z) {
        this.z = z;
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = x;
        this.z = x;
    }
}