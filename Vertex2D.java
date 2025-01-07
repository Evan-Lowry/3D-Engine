public class Vertex2D {
    public double x;
    public double y;

    public Vertex2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int)(this.x + 0.5);
    }

    public int getY() {
        return (int)(this.y + 0.5);
    }


    public int[] get() {
        int[] cordinates = {(int)(this.x + 0.5), (int)(this.y + 0.5)};
        return cordinates;
    }

    public void moveX( double deltaX) {
        this.x += deltaX;

    }

    public void moveY( double deltaY) {
        this.y += deltaY;
    }

    public void move(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    public void setX( double x) {
        this.x = x;
    }

    public void setY( double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}