public class Vertex {
    private double x;
    private double y;
    private double z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertex normalizeCoordinates() {
        Camera c = GamePanel.c;

        double xSqrd;
        double ySqrd;
        double zSqrd;
        double rSqrd;
        double r;

        double thetaRot;
        double thetaPitch;


        double thetaRotNormalized;
        double thetaPitchNormalized;

        double width;
        double length;
        double height;

        Vertex v = new Vertex(0, 0, 0);

        v.setX(this.x - c.getX());
        v.setY(this.y - c.getY());
        v.setZ(this.z - c.getZ());


        // calculate distance to vertex point
        // x^2 + y^2 + z^2 = r^2
        // r gives distance to vertex 1
        xSqrd = Math.pow(v.getX(), 2);
        ySqrd = Math.pow(v.getY(), 2);
        rSqrd = xSqrd + ySqrd;
        r = Math.sqrt(rSqrd);

        // System.out.println(r);

        // calculate the angle theta to vertex
        // tan-1(y / x) = theta
        thetaRot = Math.atan2(this.y, this.x);
        // System.out.println((double)v.getY() / v.getX());
        // System.out.println(Math.toDegrees(theta));

        // get theta for triange between vertex and camera accounting for the camera rotation
        thetaRotNormalized = thetaRot - c.getRot();
        // System.out.println(Math.toDegrees(thetaNet));
        // System.out.println(Math.toDegrees(thetaNet) + " = " + Math.toDegrees(theta) + " - " + Math.toDegrees(c.getRot()));

        // calculate the length of the base of the triangle
        width = r * Math.sin(thetaRotNormalized);
        // System.out.println(width);

        // calculate the length from the camera to the closest point on a plane intesecting
        // vertex and parallel to camera rotation
        length = r * Math.cos(thetaRotNormalized);
        // System.out.println(length);

        v.setX(width);
        v.setY(length);

        // calculate distance to vertex point
        // x^2 + y^2 + z^2 = r^2
        // r gives distance to vertex 1
        zSqrd = Math.pow(v.getZ(), 2);
        ySqrd = Math.pow(v.getY(), 2);
        rSqrd = zSqrd + ySqrd;
        r = Math.sqrt(rSqrd);

        // System.out.println(r);

        // calculate the angle theta to vertex
        // tan-1(y / x) = theta
        thetaPitch = Math.atan2(this.z, this.y);
        // System.out.println((double)v.getY() / v.getX());
        // System.out.println(Math.toDegrees(theta));

        // get theta for triange between vertex and camera accounting for the camera rotation
        thetaPitchNormalized = thetaPitch - c.getPitch();
        // System.out.println(Math.toDegrees(thetaNet));
        // System.out.println(Math.toDegrees(thetaNet) + " = " + Math.toDegrees(theta) + " - " + Math.toDegrees(c.getRot()));

        // calculate the length of the base of the triangle
        height = r * Math.sin(thetaPitchNormalized);
        // System.out.println(width);

        // calculate the length from the camera to the closest point on a plane intesecting
        // vertex and parallel to camera rotation
        length = r * Math.cos(thetaRotNormalized);
        // System.out.println(length);

        return new Vertex(width, length, height);
    }

    public castToScreen() {
        Camera c = GamePanel.c;

    }

    public int getIX() {
        return (int)this.x;
    }

    public int getIY() {
        return (int)this.y;
    }

    public int getIZ() {
        return (int)this.z;
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
}