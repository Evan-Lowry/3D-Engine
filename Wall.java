public class Wall {

    private Vertex p1 = new Vertex(0, 0, 0);
    private Vertex p2 = new Vertex(0, 0, 0);
    private Vertex p3 = new Vertex(0, 0, 0);
    private Vertex p4 = new Vertex(0, 0, 0);

    public Vertex info = new Vertex(0, 0, 0);

    public Wall(Camera c, Vertex v1, Vertex v2) {
        p1 = castToScreen(c, v1);
        p3.setX(p1.getX());
        p3.setY(GamePanel.windowHeight - p1.getY());

        p2 = castToScreen(c, v2);
        p4.setX(p2.getX());
        p4.setY(GamePanel.windowHeight - p2.getY());
    }

    private Vertex castToScreen(Camera c, Vertex vOriginal) {

        double xSqrd;
        double ySqrd;
        double rSqrd;
        double r;

        double theta;
        double thetaNet;

        double width;
        double length;

        double factor;
        double screenFactor;

        double pixelOffset;
        double screenPosX;
        double screenPosY;

        Vertex v;

        v = new Vertex(vOriginal.getX(), vOriginal.getY(), 0);

        v.moveX(-c.getX());
        v.moveY(-c.getY());


        // calculate distance to vertex 1
        // x^2 + y^2 = r^2
        // r gives distance to vertex 1
        xSqrd = Math.pow(v.getX(), 2);
        ySqrd = Math.pow(v.getY(), 2);
        rSqrd = xSqrd + ySqrd;
        r = Math.sqrt(rSqrd);

        info.setX(r);

        // System.out.println(r);

        // calculate the angle theta to vertex 1
        // tan-1(y / x) = theta
        theta = Math.atan((double)v.getY() / v.getX());
        if (v.getX() < 0) {
            theta += Math.toRadians(180);
        }
        // System.out.println((double)v.getY() / v.getX());
        // System.out.println(Math.toDegrees(theta));

        info.setY(theta);
        // System.out.println(p2.getZ());

        // get theta for triange between vertex and camera accounting for the camera rotation
        thetaNet = theta - c.getRot();
        // System.out.println(Math.toDegrees(thetaNet));
        // System.out.println(Math.toDegrees(thetaNet) + " = " + Math.toDegrees(theta) + " - " + Math.toDegrees(c.getRot()));

        // calculate the length of the base of the triangle
        width = r * Math.sin(thetaNet);
        // System.out.println(width);

        // calculate the length from the camera to the closest point on a plane intesecting
        // vertex and parallel to camera rotation
        length = r * Math.cos(thetaNet);
        // System.out.println(length);
        info.setZ(length);

        // the factor between the difference in scale between the world triange and screen triangle
        factor = c.FOV / length;
        // System.out.println(factor);

        // the difference in scale between screen space and world space
        screenFactor = factor * width;
        // System.out.println(screenFactor);

        // calculates the difference in vertex screen position from the center of the screen
        pixelOffset = (screenFactor * 40);
        // System.out.println(pixelOffset);
        
        // calculates the pixel positon on the x axis to draw the vertex
        if (length > 0) {
            screenPosX = (GamePanel.windowWidth / 2) + pixelOffset;
        } else {
            screenPosX = (GamePanel.windowWidth / 2) + (Math.sin((Math.PI)-(2*thetaNet)))*(pixelOffset/Math.sin(thetaNet));
        }
        // System.out.println(screenPosX);

        screenPosY  = (GamePanel.windowHeight / 2) - (c.FOV * 2000/Math.abs(length));
        // System.out.println(screenPosY);

        return new Vertex((int)screenPosX, (int)screenPosY, 0);
    }

    public Vertex getP1() {
        return this.p1;
    }

    public Vertex getP2() {
        return this.p2;
    }

    public Vertex getP3() {
        return this.p3;
    }

    public Vertex getP4() {
        return this.p4;
    }
}