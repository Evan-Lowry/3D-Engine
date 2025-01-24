public class Vertex3D {
    private float x;
    private float y;
    private float z;

    private float newX;
    private float newY;
    private float newZ;

    public Vertex3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void normalizeCoordinates() {
        Camera c = GamePanel.c;
        Vertex3D v = new Vertex3D(0, 0, 0);

        // calculates the vertex position relative to the camera
        v.setX(this.x - c.getX());
        v.setY(this.y - c.getY());
        v.setZ(this.z - c.getZ());

        float x = v.getX();
        float y = v.getY();
        float z = v.getZ();

        // rotates the vertex around the camera z axis
        newX = (float) (x*Math.cos(c.getRot()) + y*Math.sin(c.getRot()));
        newY = (float) (y*Math.cos(c.getRot()) - x*Math.sin(c.getRot()));


        float tempX = newX;

        // rotates the vertex around the camera y axis
        newX = (float) (newX*Math.cos(c.getPitch()) + z*Math.sin(c.getPitch()));
        newZ = (float) (z*Math.cos(c.getPitch()) - tempX*Math.sin(c.getPitch()));
    }

    public Vertex2D castToScreen() {
        Camera c = GamePanel.c;

        float intersectionToScreenX;
        float intersectionToScreenY;

        float pixelOffsetX;
        float pixelOffsetY;
        int screenPosX;
        int screenPosY;

        int windowWidth = GamePanel.windowWidth;
        int windowHeight = GamePanel.windowHeight;

        float screenWidth;
        float screenHeight;

        int screenDistance = 10;

        screenWidth = (float) Math.tan(c.getFOV()/2)*screenDistance;
        screenHeight = (screenWidth*windowHeight/windowWidth);

        intersectionToScreenX = (screenDistance*newY/newX);
        intersectionToScreenY = (screenDistance*(newZ)/newX);

        pixelOffsetX = (windowWidth*intersectionToScreenX/(screenWidth*2));
        pixelOffsetY = (windowHeight*intersectionToScreenY/(screenHeight*2));

        // calculates the pixel positon on the x axis to draw the vertex
        screenPosX = (int)((GamePanel.windowWidth / 2) + pixelOffsetX + 0.5);
        // System.out.println(screenPosX);

        screenPosY  = (int)((GamePanel.windowHeight / 2) - pixelOffsetY + 0.5);
        // System.out.println(screenPosY);

        return new Vertex2D(screenPosX, screenPosY);
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

    public float getNewZ() {
        return this.newZ;
    }

    public int[] get() {
        int[] cordinates = {(int)this.x, (int)this.y, (int)this.z};
        return cordinates;
    }

    public void moveX( double deltaX) {
        this.x += deltaX;

    }

    public void moveY( float deltaY) {
        this.y += deltaY;
    }

    public void moveZ( float deltaZ) {
        this.z += deltaZ;
    }

    public void move(float deltaX, float deltaY, float deltaZ) {
        this.x += deltaX;
        this.y += deltaY;
        this.z += deltaZ;
    }

    public void setX( float x) {
        this.x = x;
    }

    public void setY( float y) {
        this.y = y;
    }

    public void setZ( float z) {
        this.z = z;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }

    public void setNewZ(float newZ) {
        this.newZ = newZ;
    }

    public void setNewCords(float newX, float newY, float newZ) {
        this.newX = newX;
        this.newY = newY;
        this.newZ = newZ;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}