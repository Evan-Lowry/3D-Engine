import java.awt.Color;

public class Wall {

    private Vertex originalV1;
    private Vertex originalV2;
    private Vertex v1;
    private Vertex v2;
    private Vertex p1 = new Vertex(0, 0, 0);
    private Vertex p2 = new Vertex(0, 0, 0);
    private Vertex p3 = new Vertex(0, 0, 0);
    private Vertex p4 = new Vertex(0, 0, 0);
    private double depthFromCamera;
    private Color col;
    private int colorIndex;

    public Vertex info = new Vertex(0, 0, 0);
    private boolean isValid = true;


    public Wall( Vertex v1, Vertex v2, int colorIndex) {
        this.originalV1 = v1;
        this.originalV2 = v2;
        this.colorIndex = colorIndex;
    }

    public void update() {
        this.col = Color.RED;
        Camera c = GamePanel.c;

        //used to determine if to draw wall
        isValid = true;
        if (colorIndex == 0) {
            this.isValid = false;
        }

        // adjusts the cordinates so the camera has 0 rotation and is at (0,0)
        v1 = normalizeCoordinates(c, this.originalV1);
        v2 = normalizeCoordinates(c, this.originalV2);

        // figures out which point is farther from camera, then sets depthFromCamera to furthest back
        if (v1.getDY() >= v2.getDY()) {
            depthFromCamera = v1.getDY();
        } else {
            depthFromCamera = v2.getDY();
        }

        if (v1.getY() <= 0 && v2.getY() <= 0) {
            isValid = false;
            // System.out.println(isValid);
        } else {
            if (v1.getY() <= 0) {
                v1 = ghostVertex(c, v1, v2);
            } else if (v2.getY() <= 0) {
                v2 = ghostVertex(c, v2, v1);
            }
            // int colFactor = (int)((v1.getDY()+v2.getDY())/50);
            // this.col = new Color (this.col.getRed() - colFactor, this.col.getBlue() - colFactor, this.col.getGreen() - colFactor);


            // System.out.println(v1.getDX() + ", " + v1.getDY());
            Vertex[] vertexs;
            vertexs = castToScreen(c, v1);
            p1 = vertexs[0];
            p3 = vertexs[1];

            vertexs = castToScreen(c, v2);
            p2 = vertexs[0];
            p4 = vertexs[1];

            if (p1.getX() >= p2.getX()) {
                isValid = false;
            }
        }
    }

    private Vertex normalizeCoordinates(Camera c, Vertex vOriginal) {

        double xSqrd;
        double ySqrd;
        double rSqrd;
        double r;

        double theta;
        double thetaNet;

        double width;
        double length;

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

        info.setX(width);

        // calculate the length from the camera to the closest point on a plane intesecting
        // vertex and parallel to camera rotation
        length = r * Math.cos(thetaNet);
        // System.out.println(length);

        info.setZ(length);

        v.setX(width);
        v.setY(length);

        return v;
    }

    private Vertex ghostVertex(Camera c, Vertex v1, Vertex v2) {

        double slopeVertex;
        double slopeCam;
        double b;
        double newX;
        double newY;
        
        double x1 = v1.getDX();
        double y1 = v1.getDY();
        double x2 = v2.getDX();
        double y2 = v2.getDY();

        slopeCam = Math.tan((Math.PI-c.getFOV())/2);

        slopeVertex = (y2-y1)/(x2-x1);

        b = y1-(slopeVertex*x1);

        if (x1 < 0) {
            if (b < 0) {
                slopeCam = -slopeCam;   
            }
        }

        if ((int)(x1) == (int)(x2)) {
            newX = x2;
            newY = slopeCam*newX;
            System.out.println(newX);
            return new Vertex(newX, newY, 0);
        }

        newX = -b / (slopeVertex - slopeCam);

        newY = slopeVertex*newX + b;

        // System.out.println(x1 + ", " + y1 + " " + x2 + ", " + y2);

        System.out.println(newX + ", " + newY);

        // System.out.println(slopeVertex-slopeCam);
        return new Vertex(newX, newY, 0);
    }

    private Vertex[] castToScreen(Camera c, Vertex v) {

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

        double length = v.getDY();
        double width = v.getDX();

        int wallHeight = 100;

        screenWidth = Math.tan(c.getFOV()/2)*screenDistance;
        screenHeight = (screenWidth*windowHeight/windowWidth);

        intersectionToScreenX = (screenDistance*width/length);
        intersectionToScreenY = (screenDistance*(wallHeight/2)/length);

        pixelOffsetX = (windowWidth*intersectionToScreenX/(screenWidth*2));
        pixelOffsetY = (windowHeight*intersectionToScreenY/(screenHeight*2));


        // calculates the pixel positon on the x axis to draw the vertex
        screenPosX = (int)((GamePanel.windowWidth / 2) + pixelOffsetX + 0.5);
        // System.out.println(screenPosX);

        screenPosY  = (int)((GamePanel.windowHeight / 2) - pixelOffsetY + 0.5);
        // System.out.println(screenPosY);

        Vertex[] vertexs = {new Vertex(screenPosX, screenPosY, 0), new Vertex(screenPosX, (int) (GamePanel.centerY + pixelOffsetY + 0.5),0)};
        return vertexs;
    }

    public Vertex getV1() {
        return this.originalV1;
    }

    public Vertex getV2() {
        return this.originalV2;
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

    public double getDepthFromCamera() {
        return this.depthFromCamera;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public boolean isValid() {
        return this.isValid;
    }

    private void darkenColor() {
        // System.out.println(this.col);
        int colFactor = (int)((v1.getDY()+v2.getDY())/20);

        int r = (int) (col.getRed() -colFactor);
        int g = (int) (col.getGreen() -colFactor);
        int b = (int) (col.getBlue() -colFactor);
        
        // Ensure RGB values stay within the valid range (0-255)
        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));
        
        this.col = new Color(r, g, b);
        // System.out.println(this.col);
    }
}