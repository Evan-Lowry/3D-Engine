public class Vertex {
    private double x;
    private double y;
    private double z;
    private double u;
    private double v;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertex normalizeCoordinates() {
        Camera c = GamePanel.c;
        Vertex v = new Vertex(0, 0, 0);

        // calculates the vertex position relative to the camera
        v.setX(this.x - c.getX());
        v.setY(this.y - c.getY());
        v.setZ(this.z - c.getZ());

        double x = v.getX();
        double y = v.getY();
        double z = v.getZ();

        double newX;
        double newY;
        double newZ;

        // rotates the vertex around the camera z axis
        newX = x*Math.cos(c.getRot()) + y*Math.sin(c.getRot());
        newY = y*Math.cos(c.getRot()) - x*Math.sin(c.getRot());


        double tempX = newX;

        // rotates the vertex around the camera y axis
        newX = newX*Math.cos(c.getPitch()) + z*Math.sin(c.getPitch());
        newZ = z*Math.cos(c.getPitch()) - tempX*Math.sin(c.getPitch());

        return new Vertex(newX, newY, newZ);
    }

    public Vertex2D castToScreen() {
        Camera c = GamePanel.c;

        double intersectionToScreenX;
        double intersectionToScreenY;

        double screenToWindowRatio;

        double pixelOffsetX;
        double pixelOffsetY;
        double screenPosX;
        double screenPosY;

        int windowWidth = GamePanel.windowWidth;
        int windowHeight = GamePanel.windowHeight;

        double screenWidth;
        double screenHeight;

        int screenDistance = 10;

        screenWidth = Math.tan(c.getFOV()/2)*screenDistance;
        screenHeight = (screenWidth*windowHeight/windowWidth);

        intersectionToScreenX = (screenDistance*y/x);
        intersectionToScreenY = (screenDistance*(z)/x);

        pixelOffsetX = (windowWidth*intersectionToScreenX/(screenWidth*2));
        pixelOffsetY = (windowHeight*intersectionToScreenY/(screenHeight*2));

        // calculates the pixel positon on the x axis to draw the vertex
        screenPosX = (int)((GamePanel.windowWidth / 2) + pixelOffsetX + 0.5);
        // System.out.println(screenPosX);

        screenPosY  = (int)((GamePanel.windowHeight / 2) - pixelOffsetY + 0.5);
        // System.out.println(screenPosY);

        return new Vertex2D(screenPosX, screenPosY);
    }

    public int getIX() {
        return (int)(this.x + 0.5);
    }

    public int getIY() {
        return (int)(this.y + 0.5);
    }

    public int getIZ() {
        return (int)(this.z + 0.5);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
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

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}